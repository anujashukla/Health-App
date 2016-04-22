package com.example.nitinsatpal.cleverbelly;

import android.util.Log;

import com.example.nitinsatpal.cleverbelly.Models.Item;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Util {
    int BEVERAGE = 1;
    int BREAKFAST_CEREAL = 2;
    int READY_TO_COOK = 3;
    int SWEET_TOOTH = 4;
    int SNACKS = 5;
    int CONDIMENTS = 6;
    int COOKING = 7;
    int DAIRY = 8;
    int FROZEN_CANNED = 9;

    public static ArrayList<Item> mItemList = new ArrayList<>();
    public void init() {

    }

    public static void setItemList(ArrayList<Item> itemList) {
        Log.v("Hello", ".. set "+itemList.size());
        mItemList = itemList;
    }

    public static ArrayList<Item> getItemList() {
        Log.v("Hello", ".. get "+mItemList.size());
        return mItemList;
    }

    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
