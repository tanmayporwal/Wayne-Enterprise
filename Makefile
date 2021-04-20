JFLAGS = -g
JC = javac
JVM = java
.SUFFIXES: .java .class
.java.class: 
	$(JC) $(JFLAGS) $*.java

CLASSES = / RBT.java / minHeap.java / risingCity.java

default: classes

classes: $(CLASSES:.java=.class)

clean: 
	$(RM) *.class