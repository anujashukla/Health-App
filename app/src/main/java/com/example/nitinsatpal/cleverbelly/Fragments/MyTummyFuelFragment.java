package com.example.nitinsatpal.cleverbelly.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nitinsatpal.cleverbelly.Adapter.HomeCategoryGridCustomAdapter;
import com.example.nitinsatpal.cleverbelly.Adapter.TummyFuelListCustomAdapter;
import com.example.nitinsatpal.cleverbelly.Database.DatabaseHandler;
import com.example.nitinsatpal.cleverbelly.Database.DatabaseQueries;
import com.example.nitinsatpal.cleverbelly.HomeActivity;
import com.example.nitinsatpal.cleverbelly.Models.Item;
import com.example.nitinsatpal.cleverbelly.R;
import com.example.nitinsatpal.cleverbelly.Util;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyTummyFuelFragment extends Fragment {
    private ListView mTummyFuelListView;
    private ArrayList<Item> mItemList = new ArrayList<>();
    private ArrayList<Item> mTummyFuelItemList = new ArrayList<>();
    private static TummyFuelListCustomAdapter mAdapter;
    private static View rootView;
    private static boolean mIsTummyFuel;
    private TextView mCheckoutButton;

    public MyTummyFuelFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mIsTummyFuel = getArguments().getBoolean(HomeActivity.IS_TUMMY_FUEL);
        mTummyFuelItemList.clear();
        mItemList = DatabaseQueries.getCtegoryDetails(-1, DatabaseHandler.Item_Name, getActivity().getApplicationContext(), "");
        Util.setItemList(mItemList);

        for (Item item : mItemList) {
            if (mIsTummyFuel) {
                if (item.mIsInMyTummyFuel) {
                    mTummyFuelItemList.add(item);
                }
            } else {
                if (item.mIsInMyCart) {
                    mTummyFuelItemList.add(item);
                }
            }
        }
        rootView = inflater.inflate(R.layout.fragment_tummy_fuel, container, false);
        mTummyFuelListView = (ListView) rootView.findViewById(R.id.tummy_fuel_list_view);
        mCheckoutButton = (TextView) rootView.findViewById(R.id.checkout_button);
        if (!mIsTummyFuel) {
            mCheckoutButton.setVisibility(View.VISIBLE);
            mCheckoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity().getApplicationContext(), "Feature coming soon!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(mTummyFuelItemList.size() == 0) {
            TextView emptyList = (TextView) rootView.findViewById(R.id.empty_list);
            if(!mIsTummyFuel) {
                emptyList.setText(getActivity().getApplicationContext().getResources().getString(R.string.empty_order_list));
                mCheckoutButton.setVisibility(View.GONE);
            }
            emptyList.setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.footer_divider).setVisibility(View.GONE);
        } else {
            mAdapter = new TummyFuelListCustomAdapter(getActivity(), mTummyFuelItemList, mIsTummyFuel);
            mTummyFuelListView.setAdapter(mAdapter);

            mTummyFuelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    //start detail fragment here.// REDUNDENT CODE
                    Bundle bundle = new Bundle();
                    ArrayList<String> arrayValues = new ArrayList<String>();
                    ArrayList<String> arrayTitles = new ArrayList<String>();
                    arrayValues.add(mTummyFuelItemList.get(position).getmEveryContent());
                    arrayTitles.add("Healthy Energy Content");
                    arrayValues.add(mTummyFuelItemList.get(position).getmStomochFillingScrore());
                    arrayTitles.add("Stomoch Filling Score");
                    arrayValues.add(mTummyFuelItemList.get(position).getmCalorieDensity());
                    arrayTitles.add("Calorie Density");
                    arrayValues.add(mTummyFuelItemList.get(position).getmNutritionValue());
                    arrayTitles.add("Nutrition Label Transparency");

                    if (!mTummyFuelItemList.get(position).getmEnergyPer100Gm().equals("0")) {
                        arrayValues.add(mTummyFuelItemList.get(position).getmEnergyPer100Gm());
                        arrayTitles.add("Energy (kcal)");
                    }
                    if (!mTummyFuelItemList.get(position).getmProtiensPer100Gm().equals("0")) {
                        arrayValues.add(mTummyFuelItemList.get(position).getmProtiensPer100Gm());
                        arrayTitles.add("Protiens (g)");
                    }
                    if (!mTummyFuelItemList.get(position).getmCarbohydratesPer100Gm().equals("0")) {
                        arrayValues.add(mTummyFuelItemList.get(position).getmCarbohydratesPer100Gm());
                        arrayTitles.add("Carbohydrates (g)");
                    }
                    if (!mTummyFuelItemList.get(position).getmSugarPer100Gm().equals("0")) {
                        arrayValues.add(mTummyFuelItemList.get(position).getmSugarPer100Gm());
                        arrayTitles.add("Of Which Sugar (g)");
                    }
                    if (!mTummyFuelItemList.get(position).getmCholesterolPer100Gm().equals("0")) {
                        arrayValues.add(mTummyFuelItemList.get(position).getmCholesterolPer100Gm());
                        arrayTitles.add("Cholesterol (g)");
                    }

                    bundle.putStringArrayList(CategoryFragment.ARRAY_VALUES, arrayValues);
                    bundle.putStringArrayList(CategoryFragment.ARRAY_TITLES, arrayTitles);
                    bundle.putString(CategoryFragment.ITEM_NAME, mTummyFuelItemList.get(position).getmItemName());
                    bundle.putInt(CategoryFragment.ITEM_DRAWABLE_ID, mTummyFuelItemList.get(position).getmItemIconDrawableId());
                    bundle.putFloat(CategoryFragment.ITEM_RATING, mTummyFuelItemList.get(position).getmWTRating());
                    //bundle.putInt(ITEM, position);
                    ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
                    itemDetailFragment.setArguments(bundle);
                    getActivity().getFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, itemDetailFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
        if(mIsTummyFuel)
            getActivity().setTitle("My Tummy Fuel");
        else
            getActivity().setTitle("My Cart");
        return rootView;
    }

    public static void refreshList(int listSize) {
        mAdapter.notifyDataSetChanged();
        if(listSize == 0) {
            TextView emptyList = (TextView) rootView.findViewById(R.id.empty_list);
            if(!mIsTummyFuel) {
                emptyList.setText("Ooops! Your cart is empty.. !");
                rootView.findViewById(R.id.checkout_button).setVisibility(View.GONE);
            }
            emptyList.setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.footer_divider).setVisibility(View.GONE);
        }
    }
}

