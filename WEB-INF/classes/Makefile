TOMCAT=../../../apache-tomcat-9.0.30/lib
CP=-cp .:$(TOMCAT)/servlet-api.jar:$(TOMCAT)/jackson-core.jar:$(TOMCAT)/jackson-databind.jar
SRC:=$(wildcard *.java)
all:
	javac $(CP) $(SRC)
