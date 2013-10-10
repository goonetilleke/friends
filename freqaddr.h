#ifndef FREQADDR_H__
#define FREQADDR_H

struct hexStructure
{
   	unsigned long hexNum;
   	unsigned long lineNum;
   	int freqNum;
};

int hash_key(struct hexStructure hex);

void init_table(char *fileName, int size);
void insert_data(int key, struct hexStructure hex, struct hexStructure unique[], char *filename);
void print_buckets(char *filename);
int lineCount(FILE *file);
void  hexVals(FILE *file, struct hexStructure arr[], int num);
int getFreq(char *filename, struct hexStructure hex);
void printFreqHightoLow(struct hexStructure unique[],int n);

#endif
