package com.example.nitinsatpal.cleverbelly.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nitinsatpal.cleverbelly.Adapter.TrackMyDietListCustomAdapter;
import com.example.nitinsatpal.cleverbelly.Adapter.TummyFuelListCustomAdapter;
import com.example.nitinsatpal.cleverbelly.Database.DatabaseHandler;
import com.example.nitinsatpal.cleverbelly.Database.DatabaseQueries;
import com.example.nitinsatpal.cleverbelly.Models.Item;
import com.example.nitinsatpal.cleverbelly.R;
import com.example.nitinsatpal.cleverbelly.Util;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by nitinsatpal on 01-02-2016.
 */
public class TrackMyDietFragment extends Fragment {
    ListView mDietListView;
    TextView mEmptyListMsg;
    private static TextView mCalCountText;
    static float mCalCount = 0;
    static float mCheckedCount = 0;
    private ArrayList<Item> mItemList = new ArrayList<>();
    private ArrayList<Item> mDietItemList = new ArrayList<>();
    private static TrackMyDietListCustomAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_track_my_diet, container, false);
        mDietListView = (ListView) rootView.findViewById(R.id.track_diet_list_view);
        mEmptyListMsg = (TextView) rootView.findViewById(R.id.empty_list);
        mCalCountText = (TextView) rootView.findViewById(R.id.cal_count);

        mItemList = DatabaseQueries.getCtegoryDetails(-1, DatabaseHandler.Item_Name, getActivity().getApplicationContext(), "");
        Util.setItemList(mItemList);

        for (Item item : mItemList) {
            if (item.mIsInMyTummyFuel) {
                mDietItemList.add(item);
            }
        }

        mAdapter = new TrackMyDietListCustomAdapter(getActivity().getApplicationContext(), mDietItemList);
        mDietListView.setAdapter(mAdapter);
        if(mDietItemList.size() == 0) {
            mEmptyListMsg.setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.footer_divider).setVisibility(View.GONE);
            rootView.findViewById(R.id.cal_count_layout).setVisibility(View.GONE);
        }
        return rootView;
    }

    public static void updateCalorie(float countperGram, boolean add){
        float countPer100gram = countperGram*100;
        if(add) {
            mCheckedCount++;
            BigDecimal mCalCnt;
            mCalCount = (mCalCount*(mCheckedCount-1)+countPer100gram)/mCheckedCount;
            mCalCnt = new BigDecimal(Float.toString(mCalCount));
            mCalCnt.round(new MathContext(2, RoundingMode.HALF_UP));
            mCalCountText.setText(""+mCalCnt.floatValue());
        } else {
            mCheckedCount--;
            BigDecimal mCalCnt;
            if(mCheckedCount == 0)
                mCalCount = 0;
            else
                mCalCount = (mCalCount*(mCheckedCount+1)-countPer100gram)/mCheckedCount;
            mCalCnt = new BigDecimal(Float.toString(mCalCount));
            mCalCnt.round(new MathContext(2, RoundingMode.HALF_UP));
            mCalCountText.setText(""+mCalCnt.floatValue());
        }
    }
}
