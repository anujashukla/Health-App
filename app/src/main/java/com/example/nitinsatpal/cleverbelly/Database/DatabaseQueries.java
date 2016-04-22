package com.example.nitinsatpal.cleverbelly.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.nitinsatpal.cleverbelly.Models.Item;

import java.util.ArrayList;

/**
 * Created by nitinsatpal on 20-01-2016.
 */
public class DatabaseQueries {

    public static ArrayList<Item> getCtegoryDetails(int categoryId, String orderby, Context context, String filetName) {
        ArrayList<Item> itemList = new ArrayList<Item>();
        String[] whereArgs = null;
        String whereClause = null;
        String[] tableColumns = new String[]{
                DatabaseHandler.Category_ID,
                DatabaseHandler.Category_Name,
                DatabaseHandler.Sub_Category_ID,
            DatabaseHandler.Sub_Category_Name,
            DatabaseHandler.WT_Rating,
            DatabaseHandler.Item_ID,
            DatabaseHandler.Item_Name,
            DatabaseHandler.Drawable_ID,
            DatabaseHandler.Energy_Content,
            DatabaseHandler.Stomoch_Fill_Content,
            DatabaseHandler.Calorie_Density,
            DatabaseHandler.Nutrition_Value,
            DatabaseHandler.ENERGY,
            DatabaseHandler.PROTEIN,
            DatabaseHandler.CARBOHYDRATES,
            DatabaseHandler.SUGAR,
            DatabaseHandler.CHOLSTEROL,
            DatabaseHandler.In_Tummy_Fuel,
            DatabaseHandler.In_Cart,
            DatabaseHandler.Count_In_Cart};

        if(categoryId != -1 && filetName.equals("")) {
            whereClause = DatabaseHandler.Category_ID + "=?";
            whereArgs = new String[]{"" + categoryId};
        }
        if(categoryId != -1 && !filetName.equals("")) {
            whereClause = DatabaseHandler.Category_ID + "=? and "+DatabaseHandler.Sub_Category_Name + "=?";
            whereArgs = new String[]{"" + categoryId, filetName};
        }
        String orderBy = orderby;
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor c = db.query(DatabaseHandler.TABLE_PRODUCTS, tableColumns, whereClause, whereArgs, null, null, orderBy);

        if(c != null) {
            if(c.moveToFirst()) {
                do {
                    Item item = new Item();
                    int categoryid = c.getInt(c.getColumnIndex(DatabaseHandler.Category_ID));
                    String categoryName = c.getString(c.getColumnIndex(DatabaseHandler.Category_Name));
                    item.setmSubCategoryId(c.getInt(c.getColumnIndex(DatabaseHandler.Sub_Category_ID)));
                    item.setmSubCategoryName(c.getString(c.getColumnIndex(DatabaseHandler.Sub_Category_Name)));
                    item.setmWTRating(c.getFloat(c.getColumnIndex(DatabaseHandler.WT_Rating)));
                    item.setmItemId(c.getInt(c.getColumnIndex(DatabaseHandler.Item_ID) ));
                    item.setmItemName(c.getString(c.getColumnIndex(DatabaseHandler.Item_Name) ));
                    item.setmItemIconDrawableId(c.getString(c.getColumnIndex(DatabaseHandler.Drawable_ID) ));
                    item.setmEveryContent(c.getString(c.getColumnIndex(DatabaseHandler.Energy_Content) ));
                    item.setmStomochFillingScrore(c.getString(c.getColumnIndex(DatabaseHandler.Stomoch_Fill_Content) ));
                    item.setmCalorieDensity(c.getString(c.getColumnIndex(DatabaseHandler.Calorie_Density) ));
                    item.setmNutritionValue(c.getString(c.getColumnIndex(DatabaseHandler.Nutrition_Value) ));
                    item.setmEnergyPer100Gm(c.getString(c.getColumnIndex(DatabaseHandler.ENERGY) ));
                    item.setmProtiensPer100Gm(c.getString(c.getColumnIndex(DatabaseHandler.PROTEIN) ));
                    item.setmCarbohydratesPer100Gm(c.getString(c.getColumnIndex(DatabaseHandler.CARBOHYDRATES) ));
                    item.setmSugarPer100Gm(c.getString(c.getColumnIndex(DatabaseHandler.SUGAR) ));
                    item.setmCholesterolPer100Gm(c.getString(c.getColumnIndex(DatabaseHandler.CHOLSTEROL) ));
                    item.setmIsInMyTummyFuel(c.getInt(c.getColumnIndex(DatabaseHandler.In_Tummy_Fuel) ));
                    item.setmIsInMyCart(c.getInt(c.getColumnIndex(DatabaseHandler.In_Cart) ));
                    item.setmCartCount(c.getInt(c.getColumnIndex(DatabaseHandler.Count_In_Cart)));
                    itemList.add(item);
                } while (c.moveToNext());
            }
        }
        c.close();
        c = null;
        return itemList;
    }

    public static void updateQuery(String fieldName, int fieldValue, int itemId, Context context) {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(fieldName, fieldValue);
        db.update(DatabaseHandler.TABLE_PRODUCTS, cv, DatabaseHandler.Item_ID+" = "+itemId, null);
    }

    public static ArrayList<String> selectDistinct(int categoryId, Context context) {
        String selectDistinct = "SELECT DISTINCT " + DatabaseHandler.Sub_Category_Name + " FROM " + DatabaseHandler.TABLE_PRODUCTS +" WHERE "+DatabaseHandler.Category_ID+" = "+categoryId;
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor c = db.rawQuery(selectDistinct, null);//execSQL(selectDistinct);  //query(selectDistinct, null, whereClause, , null, null, null);
        ArrayList<String> filterCategoryList = new ArrayList<>();
        if(c != null) {
            if (c.moveToFirst()) {
                do {
                    filterCategoryList.add(c.getString(0));
                } while (c.moveToNext());
            }
        }
        c.close();
        c = null;
        return filterCategoryList;
    }
}
