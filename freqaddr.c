#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "freqaddr.h"

int uniqueEntries=0;

unsigned long txtLineNum=0;

int main(int argc, char *argv[]){
	
	int i, key, num, n,p;
	FILE *file1, *file2;
	struct hexStructure *arrHex;
	struct hexStructure *uniqueHex;
	
	/*count lines in file*/
	*file1=fopen(argv[3], "r");
	int lineCount();
	num=lineCount(file1);
	    
	arrHex = calloc(num, sizeof(struct hexStructure));
	
	/*make array for hex of unique, cant be bigger than size of arrhex*/
	uniqueHex = calloc(num, sizeof(struct hexStructure));
	
	/*populate with hex structs*/
	*file2=fopen(argv[3], "r");
	void hexVals();
	hexVals(file2, arrHex, num);
	
	/*initialize table to size 100801*/
	void init_table();
	init_table("myhashtable", 100801);
	
	
	/*insert data*/
	void insert_data();
	void print_buckets();
	
	for (i=0; i<num; i++){
	int hash_key();
	key=hash_key(arrHex[i]);
	insert_data(key, arrHex[i], uniqueHex, "myhashtable");
	
	}
	int getFreq();
	
	/*check arguments*/
	n=(int)strtoul(argv[2], 0, 0); 
	if (n>uniqueEntries){
		perror("error: -n too big");
		exit(1);
	}
	if (n<0){
	perror("error: -n too small");
		exit(1);
	}
	
	
	/*get frequency for each unique entry*/
	for (p; p<uniqueEntries; p++){
	uniqueHex[p].freqNum=getFreq("myhashtable", uniqueHex[p]);
	}
	
	/*print frequencies*/
	void printFreqHightoLow();
	printFreqHightoLow(uniqueHex, n);
	

	free(arrHex);
	free(uniqueHex);
	return 0;


}

int hash_key(struct hexStructure hex){
	unsigned long hexNumber;
	int key, i;
	hexNumber=hex.hexNum;
	/*divide key by prime number for better hashing*/
	key=(hex.hexNum)%100801;
	i=0;
	
/*	printf("key=%d, hexNumber=%lx\n", key, hexNumber);*/

	return key;
}

/*make initial table*/
void init_table(char *fileName, int size){
	FILE *fp; 
	hexBucket data;
	fp=fopen(fileName,"w+");
	if (fp==NULL){
		perror("fopen: init_table");
		exit(1);
	}
	/*initialize everything to 0*/
	memset (&data, 0 ,sizeof(data));
	int i=0;
	for (i; i<size; i++)
	{
	fwrite(&data, sizeof(hexBucket), 1, fp);
	}
	fclose(fp);
}

void insert_data(int key, struct hexStructure hex, struct hexStructure unique[], char *filename){
	/*make sure table isn't full*/
	if (uniqueEntries<100801){
	FILE *fp;
	hexBucket data, slot;
	int num=hex.freqNum;
	int flagRepeat;
	int uniqueNum=1;
	int pos;
	pos= key;
	data.used=1;
	data.hexStruct.hexNum=hex.hexNum;
	data.hexStruct.freqNum=hex.freqNum;
	data.hexStruct.lineNum=hex.lineNum;
	data.hexStruct.freqNum=hex.freqNum;

	
	/*read and write*/
	fp=fopen(filename, "r+");
	if (fp==NULL){
		perror("fopen: insert_data");
		exit(1);
	}
	while (1) {
		data.key=pos;

		/*pointer moves by hexBucket size, searching from key*/
		fseek(fp, pos*sizeof(hexBucket), SEEK_SET);
		fread(&slot, sizeof(hexBucket), 1, fp);
		
		/*check if slot is used*/
		if (slot.used==0){
		break;
		}
		/*printf("collision\n");*/
		if (slot.hexStruct.hexNum==data.hexStruct.hexNum){
		uniqueNum=0;

		
		num=slot.hexStruct.freqNum;

		num++;
		data.hexStruct.freqNum=num;
		break;
		}else{

				pos++;
				pos=pos%100801;

		}
		pos=pos%100801;
	}

	if (flagRepeat==0){
	/*find avail pos*/
	fseek(fp, pos*sizeof(hexBucket), SEEK_SET);
	fwrite(&data, sizeof(hexBucket), 1, fp);
	
	
	}
	
	if (uniqueNum==1&&flagRepeat==0){
	/*make unique entries to help later with printing frequencies of unique hex numbers*/
	unique[uniqueEntries].hexNum=data.hexStruct.hexNum;
	unique[uniqueEntries].lineNum=data.hexStruct.lineNum;
	uniqueEntries++;
	}
	fclose(fp);
	}else{
	perror("exit hash table full");
	exit(1);
	}
}

/*just a printing method to help as I went along*/
void print_buckets(char *filename){
	FILE *fp;
	hexBucket data;
	int i;
	
	fp=fopen(filename,"r+");
	if (fp==NULL){
	
	perror("fopen:insert_data");
	exit(1);
	}
	for (i=0; i<100801; i++){
	fread(&data, sizeof(hexBucket), 1, fp);
	/*printf("used=%d, key=%d, data=%lx, freqNumm=%d\n", data.used, data.key,data.hexStruct.hexNum, data.hexStruct.freqNum);*/
	}
	fclose(fp);


}

/*get line count of file*/
int lineCount(FILE *file){
	char extra[590];
	char line[50];
	int numValues=0;
    if ( file == 0 ) /*check if you can open file*/
    {
    perror( "Could not open file\n" );
    exit(1);
    }
    else 
    { 
		while(!feof(file)) 
		{
  	 	fscanf(file,"%[^ \n\t\r]s",line); /*Get line*/
		numValues++; 
    	fscanf(file,"%[ \n\t\r]s",extra); /*Remove any white space characters*/
		}
	fclose(file);
	}
	return numValues;

}

void  hexVals(FILE *file, struct hexStructure arr[], int num){

	int frequencyNum=1;
	
	if ( file == 0 ) 
    {
	printf( "Could not open file\n" );
    }
    else 
    { 
	int i=0;

	while(!feof(file))
	{

	for (i; i<num; i++){
	txtLineNum++; 
	unsigned long hexVal;
    fscanf(file,"%lx", &hexVal);
	/*set values*/
	arr[i].hexNum=hexVal;
	arr[i].lineNum=txtLineNum;
	arr[i].freqNum=frequencyNum;
	
	
	
	}break;

	
	}
	fclose(file);
		
	
		
	}

}
int getFreq(char *filename, struct hexStructure hex){
	/*declare variables*/
	int frequency=-1;
	FILE *fp;
	hexBucket data;
	int i;
	int key=hash_key(hex);
	int pos=key;
	
	fp=fopen(filename,"r+");
	if (fp==NULL){
	
	perror("fopen:print_buckets");
	exit(1);
	}
	
	/*start from where it should hash and then if open addressing
	occurred then check next index*/
	fseek(fp, pos*sizeof(hexBucket), SEEK_SET);
	for (i=0; i<100801; i++){
	fread(&data, sizeof(hexBucket), 1, fp);
	if (data.hexStruct.hexNum==hex.hexNum){
	frequency=data.hexStruct.freqNum;
		fclose(fp);
		return frequency;
	}
	}
	fclose(fp);
	return frequency;


}
void printFreqHightoLow(struct hexStructure unique[],int n){
	int x;
	int y;
	/*selection sort on unique entries */
	for(x=0; x<uniqueEntries; x++)
	{
		int max = x;
		for(y=x; y<uniqueEntries; y++)
		{
			if(unique[max].freqNum<unique[y].freqNum)
			{
				max = y;
			}
			/*if freqnums are the same check which came first in text file*/
			if (unique[max].freqNum==unique[y].freqNum){
				if (unique[max].lineNum>unique[y].lineNum){
					max=y;
				}
			}
		}
		struct hexStructure temp = unique[x];
		unique[x] = unique[max];
		unique[max] = temp;

	}
	if (n==0){
	n=uniqueEntries;
	}
	int p=0;
	for (p; p<n; p++){
	
	char str[80];
	char str2[80];
	
  	 sprintf(str, "0x%lx", unique[p].hexNum);
  	 sprintf(str2, ": %d", unique[p].freqNum );
  	printf("%s",  str);
  	 printf("%s\n", str2);
  	 
	
	}
	
}
