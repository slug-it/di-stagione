all: www/index.html www/gennaio.html

data/it/produce-by-name.yaml: data/it/fruits.yaml data/it/vegetables.yaml
	./merge.py $? Frutta Verdura $@

data/it/produce-by-month.yaml: data/it/produce-by-name.yaml
	./transpose.py $? $@

www/index.html: data/it/produce-by-month.yaml templates/index.html
	./html_generator.py index

www/gennaio.html: data/it/produce-by-month.yaml templates/month.html
	./html_generator.py months

clean:
	rm data/it/produce-by-month.yaml data/it/produce-by-name.yaml www/index.html www/gennaio.html
