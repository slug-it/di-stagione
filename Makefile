all: data/it/produce-by-name.yaml data/it/produce-by-month.yaml

data/it/produce-by-name.yaml: data/it/fruits.yaml data/it/vegetables.yaml
	./merge.py $? Frutta Verdura $@

data/it/produce-by-month.yaml: data/it/produce-by-name.yaml
	./transpose.py $? $@

clean:
	rm data/it/produce-by-month.yaml data/it/produce-by-name.yaml
