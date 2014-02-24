#!/usr/bin/env python3
# -*- encoding: utf-8 -*-
# Android res generation scripts for Di Stagione
# Copyright 2014 Matteo Bertini

import os
import re
import unicodedata
import argparse
import logging

import yaml
from jinja2 import Environment, FileSystemLoader


logging.basicConfig()
logger = logging.getLogger('res_generator')
logger.setLevel(logging.INFO)

def generate_months(produce_by_month):
    env = Environment(loader=FileSystemLoader('./templates'))
    template = env.get_template('produces.xml')

    with open('./res/values/produces.xml'.format(**locals()), 'w+') as res_month:
        res_month.write(template.render(**locals()))
    logger.info('{res_month.name} generated!'.format(**locals()))

def args():
    parser = argparse.ArgumentParser(description='Generate android res files from yaml data end jinja2 templates')
    parser.add_argument('target', choices=['months'], help='start res generator')

    args = parser.parse_args()
    return args

if __name__ == "__main__":
    produce_by_month = yaml.load(open('data/it/produce-by-month.yaml'))
    target = args().target
    globals()['generate_'+target](produce_by_month)
