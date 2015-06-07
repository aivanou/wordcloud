
all: install test deploy

install:
	mvn clean install

test: install
	mvn test

deploy:
	sudo docker build -t wordcloud/engine .
	sudo docker run -it -p 8090:8090 wordcloud/engine

