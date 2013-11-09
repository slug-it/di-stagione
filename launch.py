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


# script directory
my_dir = os.path.dirname(os.path.realpath(__file__))

# command-line options
opts = None

def which(name, flags=os.X_OK):
    """Search PATH for executable with the given name."""
    result = []
    exts = filter(None, os.environ.get('PATHEXT', '').split(os.pathsep))
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
        dest='verbose', 
        action='store_true',
        default=False,
        help='enable verbose mode')
    parser.add_option(
        '-r', '--run',
        dest='run',
        action='store_true',
        default=False,
        help='skip the build step and run the app directly'
        )
    parser.add_option(
        '-l', '--logcat',
        dest='logcat',
        action='store_true',
        default=False,
        help='show real-time log after the launch step'
        )
    return parser

def loadConfigurationFile():
    """Load the application launch configuration file (launch.cfg)."""
    cfgfile = os.path.join(my_dir, 'launch.cfg')
    # check that configuration file exist
    if not os.path.isfile(cfgfile):
        if opts.verbose:
            print 'launch configuration file (launch.cfg) not found'
        return {}
    # if so, read it
    d = {}
    execfile(cfgfile, d)
    if 'cfg' in d:
        return d['cfg']
    else:
        print '(warning) "cfg" dict not found in launch.cfg file'
        return {}

def main():
    # parse command line
    global opts
    parser = createCmdlineParser()
    (opts, args) = parser.parse_args()
    
    print 'loading launch configuration..'
    launchCfg = loadConfigurationFile()

    # check that sbt is in path
    print 'preparing environment..'
    if len(which('sbt')) <= 0:
        print 'sbt command not found'
        return 1

    # check android environment
    envcopy = os.environ.copy()
    androidHome = envcopy.get('ANDROID_HOME')
    if androidHome == None:
        
        # search ANDROID_HOME in the launch configuration file
        androidHome = launchCfg.get('ANDROID_HOME')
        if androidHome == None:
            # if ANDROID_HOME is not found in the configuration file we give up
            print 'ANDROID_HOME not found'
            print 'you can set it in the launch.cfg file'
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
        # if ANDROID_HOME is set we assume that android environment is
        # correctly set up
        pass

    print 'checking device availability..'
    p = subprocess.Popen(
            ['adb', 'devices'], 
            env=envcopy, 
            stdout=subprocess.PIPE
            )
    devicesOutput = p.communicate()[0]
    devicesLines = devicesOutput.split('\n')
    devicePresent = False
    for line in devicesLines[1:]:
        if len(line) > 0:
            devicePresent = True
            break
    if not devicePresent:
        print 'no device found - hurry up and connect your phone!'
        return 1

    # prepares the command options
    cmdopts = {'env': envcopy}
    if not opts.verbose:
        cmdopts.update({'stdout': subprocess.PIPE, 'stderr':subprocess.PIPE})

    if not opts.run:
        print 'building apk..'
        cmd = ['sbt', 'android:package']
        if subprocess.call(cmd, **cmdopts) != 0:
            print 'errors found while building the apk'
            return 1

        print 'installing apk..'
        apkfile = os.path.join('bin', 'di-stagione-debug.apk')
        cmd = ['adb', 'install', '-r', apkfile]
        if subprocess.call(cmd, **cmdopts) != 0:
            print 'error while installing apk..'
            return 1

    print 'launching app..'
    packageName = '.'.join(['it', 'slug', 'distagione'])
    activityName = '.'.join([packageName, 'ListaDiStagione'])
    activityCompleteName = '/'.join([packageName, activityName])
    cmd = ['adb', 'shell', 'am', 'start', '-n', activityCompleteName]
    if subprocess.call(cmd, **cmdopts) != 0:
        print 'error while launching app..'
        return 1

    if opts.logcat:
        def sighndl(s, f):
            print ''
            return
        signal.signal(signal.SIGINT, sighndl)
        cmd = ['adb', 'logcat', 'it.slug.distagione:V', '*:S']
        if len(which('logcat-color')) > 0:
            cmd = ['logcat-color', 'it.slug.distagione:V', '*:S']
        subprocess.call(cmd, env=envcopy)

    print 'Done!'
    return 0

if __name__ == '__main__':
    sys.exit(main())
