// Example.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "Gradient.h"

int main()
{
	Stock HPE;
	HPE.getData();
	string s;
	double c;
	for (int i = 0; i < 5000000; i++)
	{
		HPE.gradient();
	}
	HPE.setData();
}