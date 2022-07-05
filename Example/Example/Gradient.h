#pragma once
#include <vector>
#include <iostream>
#include <fstream>
#include <cmath>

using namespace std;

class Stock
{
public:
	void getData();
	void gradient();
	void setData();
	int linRegress(int n);
	void inverse();
	Stock();
	~Stock();

private:
	double Numbers[4][335];
	long double invNum[4][4];
	long double w[4];
	double y[334];
	long double s[4][335];
	double g[4];
	long double f[4];
	double r = .01;
};