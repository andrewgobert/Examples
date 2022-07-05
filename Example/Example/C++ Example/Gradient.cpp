#include "Gradient.h"

Stock::Stock()
{
}

Stock::~Stock()
{
}

void Stock::getData()
{
	ifstream data;
	data.open("HPE.txt");
	double number = 0;
	for (int j = 0; j < 335; j++)
	{
		for (int i = 0; i < 4; i++)
		{
			data >> number;
			Numbers[i][j] = number;
			s[i][j] = 0;
		}
	}
	for (int i = 0; i < 4; i++)
	{
		f[i] = 0;
	}
	for (int i = 0; i < 4; i++)
	{
		g[i] = 0;
		w[i] = 0;
	}
	data.close();
}
int Stock::linRegress(int n)
{
	for (int i = 0; i < 4; i++)
	{
		g[i] = 0;
		w[i] = 0;
	}
	for (int i = 0; i < 4; i++)
	{
		for (int j = 0; j < 4; j++)
		{
			invNum[i][j] = 0;
		}
	}
	for (int i = 0; i < 334; i++)
	{
		int s = i + 1;
		y[i] = Numbers[n + 1][s];
	}
	for (int h = 0; h < 4; h++)
	{
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 335; j++)
			{
				invNum[h][i] += (Numbers[h][j] * Numbers[i][j]);
			}
		}
	}
	inverse();
	for (int i = 0; i < 4; i++)
	{
		for (int j = 0; j < 335; j++)
		{
			for (int k = 0; k < 4; k++)
			{
				s[i][j] += (invNum[k][i] * Numbers[k][j]);
			}
			cout << s[i][j] << '\n';
		}

	}
	for (int j = 0; j < 334; j++)
	{
		w[n] = w[n] + (s[n][j] * y[j]);
	}
	g[n] = w[n] * Numbers[n][331];
	return g[n];
}

void Stock::gradient()
{
	for (int i = 0; i < 4; i++)
	{
		for (int j = 0; j < 334; j++)
		{
			y[j] = Numbers[i][j + 1];
			f[i] += ((y[j] * Numbers[i][j]) / (1 + exp(y[j] * w[i] * Numbers[i][j])));
		}
		f[i] = (f[i] / 335);
		w[i] += (r * f[i]);
		g[i] = w[i] * Numbers[i][331];
	}
}

void Stock::setData()
{
	ofstream output;
	output.open("output.csv");
	output << ',' << "Open" << ',' << "High" << ',' << "Low" << ',' << "Close";
	output << "Actual:" << ',';
	for (int i = 0; i < 3; i++)
	{
		output << Numbers[i][332] << ',';
	}
	output << Numbers[3][332] << '\n';
	output << "Predicted:" << ',';
	for (int i = 0; i < 3; i++)
	{
		output << g[i] << ',';
	}
	output << g[3];
	output.close();
}

void Stock::inverse()
{
	int m, n, c, d, b, t;
	double det;
	det = 0;
	d = 0;
	t = 0;
	for (int i = 0; i < 4; i++)
	{
		b = 0;
		for (int j = 0; j < 6; j++)
		{
			m = n = 1;
			c = 1;
			d = 0;
			for (int h = 0; h < 3; h++)
			{
				if (m == i) {
					++m;
					++d;
				}
				c = c * invNum[m][n];
				switch (j)
				{
				default:
					break;
				case 0:
					break;
				case 1:
					if (m == 3) {
						n = 3;
						m = 1+d;
					}
					else {
						if (h > 2) {
							--m;
							--n;
						}
						else {
							++m;
							++n;
						}
					}
					break;
				case 2:
					if (m == 3) {
						n = 3;
						m = 0+d;
					}
					else {
						if (h > 2) {
							++m;
							++n;
						}
						else {
							--m;
							--n;
						}
					}
					break;
				case 3:
					break;
				case 4:
					if (m == 1) {
						n = 3;
						m = 3-d;
					}
					else {
						if (h > 3) {
							++m;
							--n;
						}
						else {
							--m;
							++n;
						}
					}
					break;
				case 5:
					if (m == 1) {
						n = 3;
						m = 2-d;
					}
					else {
						if (h > 2) {
							++m;
							--n;
						}
						else {
							--m;
							++n;
						}
					}
					break;
				}
			}
			if (j < 5)
			{
				b += c;
			}
			else
				b -= c;
		}
		if (t == 0) {
			det = det + (invNum[i][0] * b);
			++t;
		}
		else {
			det = det - (invNum[i][0] * b);
			--t;
		}
	}
	for (int i = 0; i < 4; i++)
	{
		for (int j = 0; j < 4; j++)
		{
			invNum[i][j] = invNum[i][j] / det;
		}
	}
}