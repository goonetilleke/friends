#this is a comment
COMPILER = gcc
CCFLAGS= -pedantic -Wall
all: freqaddr

debug:
	make DEBUG=TRUE

freqaddr: freqaddr.o
	$(COMPILER) $(CCFLAGS) -o freqaddr freqaddr.o
freqaddr.o: freqaddr.c
	$(COMPILER) $(CCFLAGS) -c freqaddr.c

ifeq ($(DEBUG), TRUE)
 CCFLAGS += -g
endif

clean:
	rm -f freqaddr
	rm -f *.o
