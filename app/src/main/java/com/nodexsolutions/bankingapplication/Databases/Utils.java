package com.nodexsolutions.bankingapplication.Databases;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class Utils {

    public static byte[] getPictureByteOfArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap getBitmapFromByte(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static String getText(EditText textView){
        return textView.getText().toString().trim();
    }

    public static boolean isEmpty(EditText textView){
        if (textView.getText().toString().isEmpty()){
            return true;
        }else{
            return false;
        }
    }
}
