package com.example.nitinsatpal.cleverbelly.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by nitinsatpal on 17-01-2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "CleverBellyManager";

    // Contacts table name
    public static final String TABLE_PRODUCTS = "finaldata";
    private String CREATE_PRODUCTS_TABLE;

    // CleverBellyManager Table Columns names
    public static final String Category_ID = "cid";
    public static final String Category_Name = "cname";
    public static final String Sub_Category_ID = "sid";
    public static final String Sub_Category_Name = "sname";
    public static final String WT_Rating = "rating";
    public static final String Item_ID = "iid";
    public static final String Item_Name = "iname";
    public static final String Drawable_ID = "did";
    public static final String Energy_Content = "energycontent";
    public static final String Stomoch_Fill_Content = "stomochfillcontent";
    public static final String Calorie_Density = "calorie_density";
    public static final String Nutrition_Value = "nutritionvalue";
    public static final String ENERGY = "energy";
    public static final String PROTEIN = "protein";
    public static final String CARBOHYDRATES = "carbohydrates";
    public static final String SUGAR = "sugar";
    public static final String CHOLSTEROL = "cholsterol";
    public static final String In_Tummy_Fuel = "inbellyfuel";
    public static final String In_Cart = "incart";
    public static final String Count_In_Cart = "countincart";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);

        // Create tables again
        onCreate(db);
    }


    public void insertDataInDB (Context context, int resId) throws IOException {
        CREATE_PRODUCTS_TABLE  = "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCTS + "("
                + Category_ID + " INTEGER,"
                + Category_Name + " TEXT,"
                + Sub_Category_ID + " INTEGER,"
                + Sub_Category_Name + " TEXT,"
                + WT_Rating + " FLOAT,"
                + Item_ID + " INTEGER PRIMARY KEY,"
                + Item_Name + " TEXT,"
                + Drawable_ID + " TEXT,"
                + Energy_Content + " TEXT,"
                + Stomoch_Fill_Content + " TEXT,"
                + Calorie_Density + " TEXT,"
                + Nutrition_Value + " TEXT,"
                + ENERGY + " TEXT,"
                + PROTEIN + " TEXT,"
                + CARBOHYDRATES + " TEXT,"
                + SUGAR + " TEXT,"
                + CHOLSTEROL + " TEXT,"
                + In_Tummy_Fuel + " INTEGER,"
                + In_Cart + " INTEGER,"
                + Count_In_Cart + " INTEGER)";

        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL(CREATE_PRODUCTS_TABLE);

        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM "+TABLE_PRODUCTS, null);
        cur.moveToFirst();
        if (cur != null && cur.getInt(0) == 0) {
            cur = null;
        }
        if(cur == null){
            InputStream is = context.getResources().openRawResource(resId);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader buffer = new BufferedReader(isr, 5000);
            String line = "";
            String tableName =TABLE_PRODUCTS;
            String columns = Category_ID+","+ Category_Name+","+ Sub_Category_ID+","+ Sub_Category_Name+","+ WT_Rating +","+Item_ID + ","+
                    Item_Name+","+ Drawable_ID+","+ Energy_Content+","+ Stomoch_Fill_Content+","+ Calorie_Density+","+ Nutrition_Value+","+
                    ENERGY +","+ PROTEIN +","+ CARBOHYDRATES +","+ SUGAR +","+ CHOLSTEROL +","+
                    In_Tummy_Fuel+","+ In_Cart+","+Count_In_Cart;
            String str1 = "INSERT INTO " + tableName + " (" + columns + ") values(";
            String str2 = ");";

            db.beginTransaction();
            while ((line = buffer.readLine()) != null) {
                StringBuilder sb = new StringBuilder(str1);
                String[] str = line.split(",");
                sb.append(str[0] + ",");
                sb.append("'" + str[1] + "',");
                sb.append(str[2] + ",");
                sb.append("'" + str[3] + "',");
                sb.append(str[4] + ",");
                sb.append(str[5] + ",");
                sb.append("'" + str[6] + "',");
                sb.append("'" + str[7] + "',");
                sb.append("'" + str[8] + "',");
                sb.append("'" + str[9] + "',");
                sb.append("'" + str[10] + "',");
                sb.append("'" + str[11] + "',");
                sb.append("'" + str[12] + "',");
                sb.append("'" + str[13] + "',");
                sb.append("'" + str[14] + "',");
                sb.append("'" + str[15] + "',");
                sb.append("'" + str[16] + "', ");
                sb.append(str[17] + ",");
                sb.append(str[18] + ",");
                sb.append(str[19]);
                sb.append(str2);
                db.execSQL(sb.toString());
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }
}
