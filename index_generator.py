#! /usr/bin/evn python3
# -*- encoding: utf-8 -*-
# Html generation scripts for Di Stagione
# Copyright 2013 Matteo Bertini

import re
import unicodedata
import argparse
import logging

import yaml
from jinja2 import Environment, FileSystemLoader


logging.basicConfig()
logger = logging.getLogger('html_generator')
logger.setLevel(logging.INFO)

def slugify(string): # FIXME: not working in py3
    """
    A generic slugifier utility (currently only for Latin-based scripts).

    Example:

        >>> slugify(u"Héllø Wörld")
        u"hello-world"
    """
    return re.sub(r'[-\s]+', '-',
                  re.sub(r'[^\w\s-]', '',
                      unicodedata.normalize('NFKD', string)
                      .encode('ascii', 'ignore'))
                  .strip()
                  .lower())

def generate_index(produce_by_month):
    env = Environment(loader=FileSystemLoader('./templates'))
    template = env.get_template('index.html')
    with open('./www/index.html', 'w+') as html_index:
        html_index.write(template.render(produce=produce_by_month))
    logger.info('./www/index.html generated!')

def generate_months(produce_by_month):
    env = Environment(loader=FileSystemLoader('./templates'))
    template = env.get_template('month.html')

    months = [produce['month'] for produce in produce_by_month]
    slugs = [m.lower() for m in months]

    for c, month in enumerate(months):
        month_slug = slugs[c]
        next_month_slug = slugs[c%len(slugs)]
        prev_month_slug = slugs[c-1]
        produce = [p for p in produce_by_month if p['month'] == month]
        with open('./www/{month_slug}.html'.format(**locals()), 'w+') as html_month:
            html_month.write(template.render(**locals()))
        logger.info('./www/{month_slug}.html generated!'.format(**locals()))

def args():
    parser = argparse.ArgumentParser(description='Generate htmls files from yaml data end jinja2 templates')
    parser.add_argument('target', choices=['index', 'months'], help='start html generator')

    args = parser.parse_args()
    return args

if __name__ == "__main__":
    produce_by_month = yaml.load(open('data/it/produce-by-month.yaml'))
    globals()['generate_'+args().target](produce_by_month)
