package com.example.nitinsatpal.cleverbelly.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nitinsatpal.cleverbelly.Adapter.HomeCategoryGridCustomAdapter;
import com.example.nitinsatpal.cleverbelly.Database.DatabaseHandler;
import com.example.nitinsatpal.cleverbelly.Database.DatabaseQueries;
import com.example.nitinsatpal.cleverbelly.HomeActivity;
import com.example.nitinsatpal.cleverbelly.Models.Item;
import com.example.nitinsatpal.cleverbelly.R;
import com.example.nitinsatpal.cleverbelly.Util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    private String mCategoryName;
    private GridView mGridView;
    private Spinner mFilterOptions;
    private Spinner mSortOptions;

    public final static String ARRAY_VALUES = "array_values";
    public final static String ARRAY_TITLES = "array_titles";
    public final static String ITEM_NAME = "item_name";
    public final static String ITEM_DRAWABLE_ID = "drawable_id";
    public final static String ITEM_RATING = "item_rating";
    public final static String ITEM_IN_CART = "item_in_cart";
    public final static String ITEM_ID = "item_id";

    private ArrayList<Item> mItemList;
    private HomeCategoryGridCustomAdapter mAdapter;
    private int mCategoryId;
    ArrayList<String> mFilterOptionsList;
    private int mSortSelectedPosition = 0;
    private int mFilterSelectedPosition = 0;

    public CategoryFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCategoryName = getArguments().getString(HomeFragment.CATEGORY_TYPE);
        mCategoryId = getArguments().getInt(HomeFragment.CATEGORY_ID);
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(mCategoryName);
        View rootView = inflater.inflate(R.layout.fragment_home_category, container, false);
        mGridView = (GridView) rootView.findViewById(R.id.category_items_grid_view);
        mFilterOptions = (Spinner) rootView.findViewById(R.id.filter);
        mSortOptions = (Spinner) rootView.findViewById(R.id.sort);

        mSortOptions.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        List<String> sortOptionsList = new ArrayList<>();
        String[] list = getActivity().getApplicationContext().getResources().getStringArray(R.array.sort_options);
        for(String str: list) sortOptionsList.add(str);
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, sortOptionsList);
        mSortOptions.setAdapter(sortAdapter);

        mFilterOptions.setOnItemSelectedListener(new CustomOnItemFilterSelectedListener());
        ArrayList<String> filterOptionsReturnList = DatabaseQueries.selectDistinct(mCategoryId, getActivity().getApplicationContext());
        mFilterOptionsList = new ArrayList<>();
        mFilterOptionsList.add(0,"All");
        mFilterOptionsList.addAll(1,filterOptionsReturnList);
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, mFilterOptionsList);
        mFilterOptions.setAdapter(filterAdapter);
        return rootView;
    }

    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            mSortSelectedPosition = pos;
            if (pos == 0) {
                if(mFilterSelectedPosition == 0)
                    mItemList = DatabaseQueries.getCtegoryDetails(mCategoryId, DatabaseHandler.Item_Name, getActivity().getApplicationContext(), "");
                else
                    mItemList = DatabaseQueries.getCtegoryDetails(mCategoryId, DatabaseHandler.Item_Name, getActivity().getApplicationContext(), mFilterOptionsList.get(pos));
                mAdapter = new HomeCategoryGridCustomAdapter(getActivity(), mItemList);
                mGridView.setAdapter(mAdapter);
                setClickListenerOnGridView();
            }
            if(pos == 1) {
                if(mFilterSelectedPosition == 0)
                    mItemList = DatabaseQueries.getCtegoryDetails(mCategoryId, DatabaseHandler.WT_Rating, getActivity().getApplicationContext(), "");
                else
                    mItemList = DatabaseQueries.getCtegoryDetails(mCategoryId, DatabaseHandler.WT_Rating, getActivity().getApplicationContext(),mFilterOptionsList.get(mFilterSelectedPosition));
                mAdapter = new HomeCategoryGridCustomAdapter(getActivity(), mItemList);
                mGridView.setAdapter(mAdapter);
                setClickListenerOnGridView();
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }

    public class CustomOnItemFilterSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            mFilterSelectedPosition = pos;
            if(pos==0){
                if(mSortSelectedPosition == 0)
                    mItemList = DatabaseQueries.getCtegoryDetails(mCategoryId, DatabaseHandler.Item_Name, getActivity().getApplicationContext(), "");
                else
                    mItemList = DatabaseQueries.getCtegoryDetails(mCategoryId, DatabaseHandler.WT_Rating, getActivity().getApplicationContext(), "");
                mAdapter = new HomeCategoryGridCustomAdapter(getActivity(), mItemList);
                mGridView.setAdapter(mAdapter);
                setClickListenerOnGridView();
            }
            else {
                if(mSortSelectedPosition == 0)
                    mItemList = DatabaseQueries.getCtegoryDetails(mCategoryId, DatabaseHandler.Item_Name, getActivity().getApplicationContext(), mFilterOptionsList.get(pos));
                else
                    mItemList = DatabaseQueries.getCtegoryDetails(mCategoryId, DatabaseHandler.WT_Rating, getActivity().getApplicationContext(), mFilterOptionsList.get(pos));
                mAdapter = new HomeCategoryGridCustomAdapter(getActivity(), mItemList);
                mGridView.setAdapter(mAdapter);
                setClickListenerOnGridView();
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    public void setClickListenerOnGridView() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Bundle bundle = new Bundle();
                ArrayList<String> arrayValues = new ArrayList<String>();
                ArrayList<String> arrayTitles = new ArrayList<String>();
                arrayValues.add(mItemList.get(position).getmEveryContent());
                arrayTitles.add("Healthy Energy Content");
                arrayValues.add(mItemList.get(position).getmStomochFillingScrore());
                arrayTitles.add("Stomoch Filling Score");
                arrayValues.add(mItemList.get(position).getmCalorieDensity());
                arrayTitles.add("Calorie Density");
                arrayValues.add(mItemList.get(position).getmNutritionValue());
                arrayTitles.add("Nutrition Label Transparency");

                if (!mItemList.get(position).getmEnergyPer100Gm().equals("0")) {
                    arrayValues.add(mItemList.get(position).getmEnergyPer100Gm());
                    arrayTitles.add("Energy (kcal)");
                }
                if (!mItemList.get(position).getmProtiensPer100Gm().equals("0")) {
                    arrayValues.add(mItemList.get(position).getmProtiensPer100Gm());
                    arrayTitles.add("Protiens (g)");
                }
                if (!mItemList.get(position).getmCarbohydratesPer100Gm().equals("0")) {
                    arrayValues.add(mItemList.get(position).getmCarbohydratesPer100Gm());
                    arrayTitles.add("Carbohydrates (g)");
                }
                if (!mItemList.get(position).getmSugarPer100Gm().equals("0")) {
                    arrayValues.add(mItemList.get(position).getmSugarPer100Gm());
                    arrayTitles.add("Of Which Sugar (g)");
                }
                if (!mItemList.get(position).getmCholesterolPer100Gm().equals("0")) {
                    arrayValues.add(mItemList.get(position).getmCholesterolPer100Gm());
                    arrayTitles.add("Cholesterol (g)");
                }

                bundle.putStringArrayList(ARRAY_VALUES, arrayValues);
                bundle.putStringArrayList(ARRAY_TITLES, arrayTitles);
                bundle.putString(ITEM_NAME, mItemList.get(position).getmItemName());
                bundle.putInt(ITEM_DRAWABLE_ID, mItemList.get(position).getmItemIconDrawableId());
                bundle.putFloat(ITEM_RATING, mItemList.get(position).getmWTRating());
                bundle.putBoolean(ITEM_IN_CART, mItemList.get(position).ismIsInMyCart());
                bundle.putInt(ITEM_ID, mItemList.get(position).getmItemId());

                ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
                itemDetailFragment.setArguments(bundle);
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, itemDetailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}