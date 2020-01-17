package com.zupu.zp.utliss;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class zz {

    public static boolean isMobileNO(String accont) {
        Pattern p = Pattern.compile("^1\\d{10}$");
        Matcher m = p.matcher(accont);
        return m.matches();
    }
    public static boolean isPasswordNO(String password) {
        Pattern p = Pattern.compile("[0-9a-zA-Z_]{6,16}");
        Matcher m = p.matcher(password);
        return m.matches();
    }
    public static boolean isMobileNO1(String accont) {
        Pattern p = Pattern.compile("\\d{10}$");
        Matcher m = p.matcher(accont);
        return m.matches();
    }
}