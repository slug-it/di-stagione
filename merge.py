#!/usr/bin/env python3

import argparse
import calendar
from collections import defaultdict

import yaml

def args():
    parser = argparse.ArgumentParser(description='Merge 2 yaml tables setting names as attributes')
    parser.add_argument('source1', metavar='INPUT1', type=argparse.FileType(),
                       help='first source file')
    parser.add_argument('source2', metavar='INPUT1', type=argparse.FileType(),
                       help='second source file')
    parser.add_argument('name1', metavar='NAME1', type=str,
                       help='first source name')
    parser.add_argument('name2', metavar='NAME1', type=str,
                       help='second source name')
    parser.add_argument('output', metavar='OUTPUT', type=argparse.FileType('w+'),
                       help='output file')

    args = parser.parse_args()

    return args

def merge(adict1, adict2, name1, name2):
    merged = {}
    for k in adict1:
        merged[k] = adict1[k]
        merged[k]['kind'] = name1
    for k in adict2:
        merged[k] = adict2[k]
        merged[k]['kind'] = name2
    return merged

def run(args):
    source1 = yaml.load(args.source1)
    source2 = yaml.load(args.source2)
    res = merge(source1, source2, args.name1, args.name2)
    yaml.dump(res, args.output)

if __name__ == "__main__":
    run(args())
