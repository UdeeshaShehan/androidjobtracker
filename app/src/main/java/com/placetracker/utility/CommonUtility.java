package com.placetracker.utility;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Base64;
import android.util.Log;

import java.util.List;
import java.util.Locale;

public class CommonUtility {
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeToString(imageByteArray, Base64.NO_WRAP);
    }

    public static byte[] decodeImage(String imageDataString) {
        return Base64.decode(imageDataString, Base64.NO_WRAP);
    }

    public static String getCompleteAddressString(Context context, double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("Location", strReturnedAddress.toString());
            } else {
                Log.w("Location", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Location", "Canont get Address!");
        }
        return strAdd;
    }
}
