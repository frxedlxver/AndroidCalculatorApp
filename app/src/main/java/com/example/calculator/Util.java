package com.example.calculator;

public class Util {

    public int getSigDig(double input) {
        String temp = Double.toString(input);

        String left = temp.split("/.")[1];
        
        return left.length();
    }
}
