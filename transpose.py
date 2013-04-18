#!/usr/bin/env python3

import argparse
import calendar
from collections import defaultdict
import copy

import yaml

def args():
    parser = argparse.ArgumentParser(description='Transpose yaml table')
    parser.add_argument('source', metavar='FILE', type=argparse.FileType(),
                       help='source file')
    parser.add_argument('output', metavar='FILE', type=argparse.FileType('w+'),
                       help='output file')
    parser.add_argument('--lang', choices=['it', 'es', 'en'], default='it',
                        help='language')

    args = parser.parse_args()

    return args

def get_localized_months(lang):
    lc = {
          'it': ('it_IT', 'UTF-8'),
          'en': ('en_US', 'UTF-8'),
          'es': ('es_ES', 'UTF-8'),
          }
    with calendar.different_locale(lc[lang]):
        return [calendar.month_name[i].title() for i in range(1, 13)]

def transpose(adict):
    tr = defaultdict(list)
    idict = dict(adict)
    for k in adict:
        idict[k]['name'] = k
        for v in adict[k]['months']:
            tr[v].append(copy.deepcopy(idict[k]))
    return dict(tr)

def run(args):
    source = yaml.load(args.source)
    tr = transpose(source)
    res = []
    for month in get_localized_months(args.lang):
        res += [{"month": month, "produce": sorted(tr[month], key=lambda d: d['name'])}]
    yaml.dump(res, args.output)

if __name__ == "__main__":
    run(args())
