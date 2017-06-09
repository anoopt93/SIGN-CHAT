package edu.scn.utils;

import android.util.Patterns;

/**
 * Created by pvr on 23/9/16.
 */
public class Validation {

    public static boolean checkEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean checkPhoneNo(String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }
}
