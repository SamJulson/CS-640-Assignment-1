JFLAGS = -g -d .
JC = javac

SOURCE_DIRECTORIES = \
	./lib/commons-cli/src/main/java/org/apache/commons/cli \
	./src

SOURCES = $(foreach dir, $(SOURCE_DIRECTORIES), $(wildcard $(dir)/*.java))

default: classes

classes:
	$(JC) $(JFLAGS) $(SOURCES)

clean:
	rm -f *.class
	rm -rf ./org
