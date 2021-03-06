#!/usr/bin/env python
#-*- coding: utf-8 -*-

"""Launch script for distagione app.

Connect your phone and type "python launch.py".
Note: create a "launch.cfg" file first (see below) before using this script.

Steps executed by the script:
1. environment setup
2. devices availability check
3. apk build
4. apk install
5. application launch
6. logcat

Step 1 uses a launch configuration file called launch.cfg.
The configuration file must be placed in the directory of this script.
For now the configuration file contains the ANDROID_HOME location.
Example of file content:

    import os

    cfg = dict()

    # path to the android sdk
    cfg['ANDROID_HOME'] = '/opt/android-sdk-linux'

Step 3 (apk build) can be skipped with the "-r" command-line option.
Step 6 is enabled with the "-l" command-line option. If the logcat-color
command is found, uses it to display app-specific logs only.
Use the "-v" option to enable verbose mode.
"""

import sys
import os
import optparse
import subprocess
import signal
import logging

# script directory
my_dir = os.path.dirname(os.path.realpath(__file__))

# command-line options
opts = None

# base logger
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger("launch")

def which(name, flags=os.X_OK):
    """Search PATH for executable with the given name."""
    result = []
    exts = [p for p in os.environ.get('PATHEXT', '').split(os.pathsep) if p]

    path = os.environ.get('PATH', None)
    if path is None:
        return []
    for p in os.environ.get('PATH', '').split(os.pathsep):
        p = os.path.join(p, name)
        if os.access(p, flags):
            result.append(p)
        for e in exts:
            pext = p + e
            if os.access(pext, flags):
                result.append(pext)
    return result

def createCmdlineParser():
    parser = optparse.OptionParser('usage: python %prog [options]')
    parser.add_option(
        '-v', '--verbose',
        action='store_true',
        default=False,
        help='enable verbose mode')
    parser.add_option(
        '-b', '--build-only',
        action='store_true',
        default=False,
        help="build the apk without running"
        )
    parser.add_option(
        '-r', '--run',
        action='store_true',
        default=False,
        help='skip the build step and run the app directly'
        )
    parser.add_option(
        '-l', '--log',
        dest='logcat',
        default=None,
        help='show app log with the specified debug level'
        )
    parser.add_option(
        '-c', '--clean',
        action='store_true',
        default=False,
        help='clean environment'
        )
    return parser

def loadConfigurationFile():
    """Load the application launch configuration file (launch.cfg)."""
    cfgfile = os.path.join(my_dir, 'launch.cfg')
    # check that configuration file exist
    if not os.path.isfile(cfgfile):
        if opts.verbose:
            logger.debug('launch configuration file (launch.cfg) not found')
        return {}
    # if so, read it
    d = {}
    exec(open(cfgfile).read(), d)
    if 'cfg' in d:
        return d['cfg']
    else:
        logger.warn('"cfg" dict not found in launch.cfg file')
        return {}

def main():
    # parse command line
    global opts
    parser = createCmdlineParser()
    (opts, args) = parser.parse_args()

    if opts.verbose:
        logger.setLevel(logging.DEBUG)

    logger.info('loading launch configuration..')
    launchCfg = loadConfigurationFile()

    # check that sbt is in path
    logger.info('preparing environment..')
    if len(which('sbt')) <= 0:
        logger.error('sbt command not found')
        return 1

    # check android environment
    envcopy = os.environ.copy()
    androidHome = envcopy.get('ANDROID_HOME')
    if androidHome == None:

        # search ANDROID_HOME in the launch configuration file
        androidHome = launchCfg.get('ANDROID_HOME')
        if androidHome == None:
            # if ANDROID_HOME is not found in the configuration file we give up
            logger.info('ANDROID_HOME not found: you can set it in the launch.cfg file')
            return 1

        # update environment
        toolsDir = os.path.join(androidHome, 'tools')
        platformToolsDir = os.path.join(androidHome, 'platform-tools')
        envcopy['ANDROID_HOME'] = androidHome
        envcopy['PATH'] = ':'.join([
            toolsDir,
            platformToolsDir,
            envcopy['PATH']])
    else:
        logger.debug("if ANDROID_HOME is set we assume that android environment is correctly set up")
        pass

    if not opts.build_only:
        logger.info('checking device availability..')
        p = subprocess.Popen(
                ['adb', 'devices'],
                env=envcopy,
                stdout=subprocess.PIPE
                )
        devicesOutput = p.communicate()[0].decode("utf8")
        devicesLines = devicesOutput.split('\n')
        devicePresent = False
        for line in devicesLines[1:]:
            if len(line.strip()) > 0:
                devicePresent = True
                break
        if not devicePresent:
            logger.warn('no device found - hurry up and connect your phone!')
            return 1

    # prepares the command options
    cmdopts = {'env': envcopy}
    if not opts.verbose:
        cmdopts.update({'stdout': subprocess.PIPE, 'stderr':subprocess.PIPE})

    if opts.clean:
        logger.info('cleaning scala environment..')
        # the standard "sbt clean" does not remove all sbt artifacts
        # so we perform a deeper clean with find command
        os.system('find . -name target -type d -exec rm -rf {} \; -prune')
        os.system('find . -name bin -type d -exec rm -rf {} \; -prune')
        os.system('make clean')

    # generate xml data resources
    logger.info('generating xml data..')
    os.system('make clean')
    if os.system('make') != 0:
        logger.info('make command failed - aborting launch')
        return 1

    if not opts.run:
        logger.info('building apk..')
        cmd = ['sbt', 'android:package']
        if subprocess.call(cmd, **cmdopts) != 0:
            # remove pending adb process
            subprocess.call(['killall', 'adb'])
            logger.error('errors found while building the apk')
            return 1

        if opts.build_only:
            logger.info('OK')
            return 0

        logger.info('installing apk..')
        apkfile = os.path.join('bin', 'di-stagione-debug.apk')
        cmd = ['adb', 'install', '-r', apkfile]
        if subprocess.call(cmd, **cmdopts) != 0:
            logger.error('error while installing apk..')
            return 1

    logger.info('launching app..')
    packageName = '.'.join(['it', 'slug', 'distagione'])
    activityName = '.'.join([packageName, 'ListaDiStagione'])
    activityCompleteName = '/'.join([packageName, activityName])
    cmd = ['adb', 'shell', 'am', 'start', '-n', activityCompleteName]
    if subprocess.call(cmd, **cmdopts) != 0:
        logger.error('error while launching app..')
        return 1

    if opts.logcat != None:
        subprocess.call(['adb', 'logcat', '-c'], env=envcopy)
        def sighndl(s, f):
            print('')
            return
        signal.signal(signal.SIGINT, sighndl)
        cmd = ['adb', 'logcat']
        if len(which('logcat-color')) > 0:
            cmd = ['logcat-color', '*:' + opts.logcat]
        subprocess.call(cmd, env=envcopy)

    logger.info('Done!')
    return 0

if __name__ == '__main__':
    sys.exit(main())
