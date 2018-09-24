JFLAGS = -g -d ./bin
JC = javac

DIRECTORIES = \
	./lib/commons-cli/src/main/java/org/apache/commons/cli \
	./src

SOURCES = $(foreach dir, $(DIRECTORIES), $(wildcard $(dir)/*.java))

default: classes

classes:
	$(JC) $(JFLAGS) $(SOURCES)

clean:
	rm -rf ./bin/*
