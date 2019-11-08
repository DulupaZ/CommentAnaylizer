package com.dulupa.commentanaylizer.converter;

import android.os.Parcel;

public class ParcleUtil {

    //讀取一個字串在移動parcel data position
    public static String readString(Parcel in){
        String result=in.readString();
        in.setDataPosition(in.dataPosition()+1);
        return result;
    }
    //讀取一個INT在移動parcel data position
    public static int readInteger(Parcel in){
        int result=in.readInt();
        in.setDataPosition(in.dataPosition()+1);
        return result;
    }
}
