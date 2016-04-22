package com.example.nitinsatpal.cleverbelly.Models;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.nitinsatpal.cleverbelly.R;
import com.example.nitinsatpal.cleverbelly.Util;

/**
 * Created by nitinsatpal on 17-01-2016.
 */
public class Item {
    public int mCategoryId;
    public String mCategoryName;
    public int mSubCategoryId;
    public String mSubCategoryName;
    public float mWTRating;
    public int mItemId;
    public String mItemName;
    public boolean mIsInMyTummyFuel;
    public boolean mIsInMyCart;
    public int mCartCount;
    public int mItemIconDrawableId;
    public String mEveryContent;
    public String mStomochFillingScrore;
    public String mCalorieDensity;
    public String mNutritionValue;
    public String mEnergyPer100Gm;
    public String mProtiensPer100Gm;
    public String mCarbohydratesPer100Gm;
    public String mSugarPer100Gm;
    public String mCholesterolPer100Gm;

    public int getmCategoryId() {
        return mCategoryId;
    }

    public void setmCategoryId(int mCategoryId) {
        this.mCategoryId = mCategoryId;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }

    public void setmCategoryName(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }

    public int getmSubCategoryId() {
        return mSubCategoryId;
    }

    public void setmSubCategoryId(int mSubCategoryId) {
        this.mSubCategoryId = mSubCategoryId;
    }

    public String getmSubCategoryName() {
        return mSubCategoryName;
    }

    public void setmSubCategoryName(String mSubCategoryName) {
        this.mSubCategoryName = mSubCategoryName;
    }

    public float getmWTRating() {
        return mWTRating;
    }

    public void setmWTRating(float mWTRating) {
        this.mWTRating = mWTRating;
    }

    public int getmItemId() {
        return mItemId;
    }

    public void setmItemId(int mItemId) {
        this.mItemId = mItemId;
    }

    public String getmItemName() {
        return mItemName;
    }

    public void setmItemName(String mItemName) {
        this.mItemName = mItemName;
    }

    public boolean ismIsInMyTummyFuel() {
        return mIsInMyTummyFuel;
    }

    public void setmIsInMyTummyFuel(int mIsInMyTummyFuel) {
        if(mIsInMyTummyFuel == 0) this.mIsInMyTummyFuel = false;
        else this.mIsInMyTummyFuel = true;
    }

    public boolean ismIsInMyCart() {

        return mIsInMyCart;
    }

    public void setmIsInMyCart(int mIsInMyCart) {
        if(mIsInMyCart == 0) this.mIsInMyCart = false;
        else this.mIsInMyCart = true;
    }

    public int getmCartCount() {
        return mCartCount;
    }

    public void setmCartCount(int mCartCount) {
        this.mCartCount = mCartCount;
    }

    public int getmItemIconDrawableId() {
        return mItemIconDrawableId;
    }

    public void setmItemIconDrawableId(String mItemIconDrawableId) {
        this.mItemIconDrawableId = Util.getResId(mItemIconDrawableId,  R.drawable.class);
    }

    public String getmEveryContent() {
        return mEveryContent;
    }

    public void setmEveryContent(String mEveryContent) {
        this.mEveryContent = mEveryContent;
    }

    public String getmStomochFillingScrore() {
        return mStomochFillingScrore;
    }

    public void setmStomochFillingScrore(String mStomochFillingScrore) {
        this.mStomochFillingScrore = mStomochFillingScrore;
    }

    public String getmCalorieDensity() {
        return mCalorieDensity;
    }

    public void setmCalorieDensity(String mCalorieDensity) {
        this.mCalorieDensity = mCalorieDensity;
    }

    public String getmNutritionValue() {
        return mNutritionValue;
    }

    public void setmNutritionValue(String mNutritionValue) {
        this.mNutritionValue = mNutritionValue;
    }

    public String getmEnergyPer100Gm() {
        return mEnergyPer100Gm;
    }

    public void setmEnergyPer100Gm(String mEnergyPer100Gm) {
        this.mEnergyPer100Gm = mEnergyPer100Gm;
    }

    public String getmProtiensPer100Gm() {
        return mProtiensPer100Gm;
    }

    public void setmProtiensPer100Gm(String mProtiensPer100Gm) {
        this.mProtiensPer100Gm = mProtiensPer100Gm;
    }

    public String getmCarbohydratesPer100Gm() {
        return mCarbohydratesPer100Gm;
    }

    public void setmCarbohydratesPer100Gm(String mCarbohydratesPer100Gm) {
        this.mCarbohydratesPer100Gm = mCarbohydratesPer100Gm;
    }

    public String getmSugarPer100Gm() {
        return mSugarPer100Gm;
    }

    public void setmSugarPer100Gm(String mSugarPer100Gm) {
        this.mSugarPer100Gm = mSugarPer100Gm;
    }

    public String getmCholesterolPer100Gm() {
        return mCholesterolPer100Gm;
    }

    public void setmCholesterolPer100Gm(String mCholesterolPer100Gm) {
        this.mCholesterolPer100Gm = mCholesterolPer100Gm;
    }
}
