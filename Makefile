all: res/values/produces.xml

data/it/produce-by-name.yaml: data/it/fruits.yaml data/it/vegetables.yaml
	./merge.py $? Frutta Verdura $@

data/it/produce-by-month.yaml: data/it/produce-by-name.yaml
	./transpose.py $? $@

res/values/produces.xml: data/it/produce-by-month.yaml
	./res_generator.py months

clean:
	rm data/it/produce-by-month.yaml data/it/produce-by-name.yaml res/values/produces.xml
