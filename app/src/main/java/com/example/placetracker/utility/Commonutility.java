package com.example.placetracker.utility;


import android.util.Base64;

public class Commonutility {
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeToString(imageByteArray, Base64.NO_WRAP);
    }

    public static byte[] decodeImage(String imageDataString) {
        return Base64.decode(imageDataString, Base64.NO_WRAP);
    }
}
