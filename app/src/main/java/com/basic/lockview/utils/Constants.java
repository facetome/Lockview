package com.basic.lockview.utils;

import android.util.SparseArray;

/**
 * Created by acer on 2015/8/18.
 */
public class Constants {
    private static final String A = "a";
    private static final String B = "b";
    private static final String C = "c";
    private static final String D = "d";
    private static final String E = "e";
    private static final String F = "f";
    private static final String G = "g";
    private static final String H = "h";
    private static final String I = "i";

    public static SparseArray<String> sPasswordSpare = new SparseArray<>();

    static {
        sPasswordSpare.append(0, A);
        sPasswordSpare.append(1, B);
        sPasswordSpare.append(2, C);
        sPasswordSpare.append(3, D);
        sPasswordSpare.append(4, E);
        sPasswordSpare.append(5, F);
        sPasswordSpare.append(6, G);
        sPasswordSpare.append(7, H);
        sPasswordSpare.append(8, I);
    }

    public static String getType(int id){
        return sPasswordSpare.get(id);
    }


}
