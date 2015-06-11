port = 8090

all: install deploy

install:
	mvn clean test install

deploy:
	sudo docker build -t wordcloud/engine .
	sudo docker run -it -p $(port):$(port) wordcloud/engine

