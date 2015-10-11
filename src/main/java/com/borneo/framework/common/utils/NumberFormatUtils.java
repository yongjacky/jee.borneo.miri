package com.borneo.framework.common.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberFormatUtils {
    public static final NumberFormat pointFormat = new DecimalFormat("#");
    public static final NumberFormat pointDecimalFormat = new DecimalFormat("#.00");
   
    public static String convertPointToNoDecimal(Object points) {
        String pointStr = null;
        try {
            pointStr = pointFormat.format(points);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return pointStr;
    }

    public static String convertPointToDecimal(Object points) {
        String pointStr = null;
        try {
            pointStr = pointDecimalFormat.format(points);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return pointStr;
    }
    
    public static String convertPointToDecimalWithoutDot(Object points){
    	String pointStr = null;
        try {
            pointStr = convertPointToDecimal(points);
            pointStr = pointStr.replace(".", "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return pointStr;
    }
    
    public static void main(String[] args) {
    	System.out.println(convertPointToDecimalWithoutDot(new Double("10000.5")));
    }
}
