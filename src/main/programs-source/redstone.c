#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include "bettercc.h"
#include "devices/redstone.h"

int main(int argc, char* argv[]){
	if (argc < 2){
		puts("Usage: redstone <side name> [<value>]");
		return 0;
	}
	bus_t stdbus = openBus("/dev/hvc0");
	
	device_t dev = findDev(stdbus, "redstone");
	if (!dev.exists){
		fputs("This program requires a Redstone Interface Card or Redstone Interface.\n", stderr);
		return -1;
	}
	
	char* side = argv[1];
	int i = 0;
	while (side[i] != 0){
		side[i] = tolower(side[i]);
		i++;
	}
	
	result_t in = getRedstoneInput(&dev, side);
	if (in.type == CO_ERROR){
		printf("%s.\n", in.errString);
		return -1;
	}
	
	
	if (argc > 2){
		int strength;
		char* yesval[] = {"true", "on", "yes"};
		for (int i = 0; i < 3; i++){
			if (strcmp(yesval[i], argv[2]) == 0){
				strength = 15;
				break;
			}
			if (i == 2){
				strength = atoi(argv[2]);
			}
		}
		
		setRedstoneOutput(&dev, side, strength);
	}
	
	
	result_t out = getRedstoneOutput(&dev, side);
	printf("Input: %d\nOutput: %d\n", (int)in.retNumber, (int)out.retNumber);
	closeBus(stdbus);
	return 0;
}
