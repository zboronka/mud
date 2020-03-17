TOMCAT=../../../tomcat/lib
SERVLET=$(TOMCAT)/servlet-api.jar
JACKSONC=$(TOMCAT)/jackson-core.jar
JACKSOND=$(TOMCAT)/jackson-databind.jar
MYSQL=$(TOMCAT)/mysql-connector-java.jar
CP=-cp .:$(SERVLET):$(JACKSONC):$(JACKSOND):$(TOMCAT)
SRC:=$(wildcard *.java)
CLASS=$(SRC:%.java=%.class)
%.class: %.java
	javac $(CP) $^ 
all: $(CLASS)
