#!/usr/bin/evn python3

import yaml
from jinja2 import Environment, FileSystemLoader


def generate_index(produce_by_month):
    env = Environment(loader=FileSystemLoader('./templates'))
    template = env.get_template('index.html')
    with open('./www/index.html', 'w+') as html_index:
        html_index.write(template.render(produce=produce_by_month))

if __name__ == "__main__":
    produce_by_month = yaml.load(open('data/it/produce-by-month.yaml'))
    print(len(produce_by_month), len(produce_by_month[0]))
    generate_index(produce_by_month)
