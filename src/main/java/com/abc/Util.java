package com.abc;

import static java.lang.Math.abs;

public class Util {
    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
