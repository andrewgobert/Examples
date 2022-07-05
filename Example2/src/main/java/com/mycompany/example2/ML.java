/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.example2;
import java.io.*;
import static java.lang.Math.exp;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

/**
 *
 * @author Andrew Gobert
 */
public class ML {

    public ML() {
    }
    public void getData(){      //Gets the stock data and initializes the necessary values.
        File file;
        Scanner data = new Scanner(System.in);
        file = new File("C:\\Users\\Andrew Gobert\\Documents\\NetBeansProjects\\Example2\\src\\main\\java\\com\\mycompany\\example2\\AMZN.txt");
        try {
            data = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ML.class.getName()).log(Level.SEVERE, null, ex);
        }
	double number;
        int j=0;
        while(data.hasNext()){
            for (int i = 0; i < 4; i++)
            {
                number=data.nextDouble();
                Numbers[i][j] = number;
                s[i][j] = 0;
            }
            j++;
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
    }
    public void gradient(){     //Calculates the gradient for the weights of a given value
        for (int i = 0; i < 4; i++)
	{
		for (int j = 0; j < 1208; j++)
		{
			y[j] = Numbers[i][j + 1];
			f[i] += ((y[j] * Numbers[i][j]) / (1 + exp(y[j] * w[i] * Numbers[i][j])));
		}
		f[i] = (f[i] / 1208);
		w[i] += (r * f[i]);
		g[i] = w[i] * Numbers[i][331];
	}
    }
    public void setData(int n){     //Sends the actual values for the stocks and then what what was predicted by the software.
        try {
            File file;
            FileWriter output;
            file = new File("C:\\Users\\Andrew Gobert\\Documents\\NetBeansProjects\\Example2\\src\\main\\java\\com\\mycompany\\example2\\AMZNoutput.csv");
            output = new FileWriter(file);
            output.write(',');
            output.write("Open");
            output.write(',');
            output.write("High");
            output.write(',');
            output.write("Low");
            output.write(',');
            output.write("Close");
            output.write('\n');
            output.write("Actual");
            output.write(',');
            for (int i = 0; i < 3; i++)
            {
                try {
                    output.write(String.valueOf(Numbers[i][332]));
                    output.write(',');
                } catch (IOException ex) {
                    Logger.getLogger(ML.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            output.write(String.valueOf(Numbers[3][332]));
            output.write('\n');
            output.write("Predicted");
            output.write(',');
            for (int i = 0; i < 3; i++)
            {
                try {
                    output.write(String.valueOf(g[i]));
                    output.write(',');
                } catch (IOException ex) {
                    Logger.getLogger(ML.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            output.write(String.valueOf(g[3]));
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(ML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public double linRegress(int n){        //Calculates the linear regression
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
		int m = i + 1;
		y[i] = Numbers[n + 1][m];
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
	inverse();      //Calculates the inverse of invNum.
	for (int i = 0; i < 4; i++)
	{
		for (int j = 0; j < 335; j++)
		{
			for (int k = 0; k < 4; k++)
			{
				s[i][j] += (invNum[k][i] * Numbers[k][j]);
			}
		}

	}
	for (int j = 0; j < 334; j++)
	{
		w[n] = w[n] + (s[n][j] * y[j]);     //Calculate the weights
	}
	g[n] = w[n] * Numbers[n][331];      //Predicts what the next values are based on the calculated weights.
	return g[n];
    }
    
    public void inverse(){      //Calculates the inverse for the linear regression
        int m, n, d, b, t;
	double det, c;
	det = 0;
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
				default -> {
                                }
				case 0 -> {
                                }
				case 1 -> {
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
                                }
				case 2 -> {
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
                                }
				case 3 -> {
                                }
				case 4 -> {
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
                                }
				case 5 -> {
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
                                }
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
    double[][] Numbers = new double[4][1209];
    double[][] invNum = new double[4][4];
    double[] w = new double[4];
    double[] y = new double[1208];
    double[][] s = new double[4][1209];
    double[] g = new double[4];
    double[] f = new double[4];
    double r = .00000193;
}
