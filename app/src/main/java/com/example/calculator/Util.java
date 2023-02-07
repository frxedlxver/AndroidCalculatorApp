package com.example.calculator;

public class Util {

    public static int getSigFig(double input) {
        String temp = Double.toString(input);

        String[] split = temp.split("/.");

        if (split.length > 1) {
            return split[1].length();
        } else return 0;

    }
}
