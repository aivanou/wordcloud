FROM sctrcdr/ubuntu-base
MAINTAINER Aliaksandr Ivanou <aliaksandr.ivanou@yahoo.com>

# Add a repo where OpenJDK can be found.
RUN apt-get install -y software-properties-common
RUN add-apt-repository -y ppa:webupd8team/java
RUN apt-get update

# Auto-accept the Oracle JDK license
RUN echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections

RUN apt-get install -y oracle-java7-installer

WORKDIR /wordcloud/engine

ADD target/engine-1.0.jar /wordcloud/engine/engine.jar
ADD config /wordcloud/engine/config

EXPOSE 8090
EXPOSE 8091

CMD ["/usr/lib/jvm/java-7-oracle/bin/java","-jar","engine.jar", "server", "config/wordcloud.yml"]
