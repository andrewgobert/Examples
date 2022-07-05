/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.example2;

/**
 *
 * @author Andrew Gobert
 */
public class Example2 {

    public static void main(String[] args) {
        ML Stock;
        Stock = new ML();
        Stock.getData();
        for(int i=0; i<5000000; i++){
            Stock.gradient();
        }
        for(int i=0; i<4; i++){
            Stock.setData(i);
        }
    }
}
