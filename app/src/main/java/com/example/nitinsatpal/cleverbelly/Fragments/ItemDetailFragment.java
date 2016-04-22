package com.example.nitinsatpal.cleverbelly.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nitinsatpal.cleverbelly.Adapter.HomeItemDetailCustomAdapter;
import com.example.nitinsatpal.cleverbelly.BitmapWorkerTask;
import com.example.nitinsatpal.cleverbelly.CustomViews.ExpandableHeightGridView;
import com.example.nitinsatpal.cleverbelly.CustomViews.ExpandableHeightListView;
import com.example.nitinsatpal.cleverbelly.Database.DatabaseHandler;
import com.example.nitinsatpal.cleverbelly.Database.DatabaseQueries;
import com.example.nitinsatpal.cleverbelly.HomeActivity;
import com.example.nitinsatpal.cleverbelly.Models.Item;
import com.example.nitinsatpal.cleverbelly.R;

import java.util.ArrayList;

public class ItemDetailFragment extends Fragment {
    private int mItemId;
    private String mItemName;
    private int mItemDrawableId;

    private ImageView mItemIcon;
    private TextView mWtratingText;
    private ExpandableHeightListView mDetailParamsList;
    private TextView mAddToCart;

    HomeItemDetailCustomAdapter mAdapter;
    ArrayList<String> mDetailKeysList1;
    ArrayList<String> mDetailValuesList1;
    Boolean mIsInMyCart;

    public ItemDetailFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDetailKeysList1 = getArguments().getStringArrayList(CategoryFragment.ARRAY_TITLES);
        mDetailValuesList1 = getArguments().getStringArrayList(CategoryFragment.ARRAY_VALUES);
        mIsInMyCart = getArguments().getBoolean(CategoryFragment.ITEM_IN_CART);
        mItemId = getArguments().getInt(CategoryFragment.ITEM_ID);
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle(getArguments().getString(CategoryFragment.ITEM_NAME));
        mItemDrawableId = getArguments().getInt(CategoryFragment.ITEM_DRAWABLE_ID);
        View rootView = inflater.inflate(R.layout.fragment_home_category_item_detail, container, false);
        mItemIcon = (ImageView) rootView.findViewById(R.id.item_icon);
        mWtratingText = (TextView) rootView.findViewById(R.id.wt_rating_text);
        mDetailParamsList = (ExpandableHeightListView) rootView.findViewById(R.id.detail_param_list);
        mAddToCart = (TextView) rootView.findViewById(R.id.add_to_cart);
        mDetailParamsList.setExpanded(true);
        if(!mIsInMyCart)
            mAddToCart.setBackgroundColor(getActivity().getResources().getColor(R.color.app_color));

        BitmapWorkerTask task = new BitmapWorkerTask(mItemIcon, getActivity().getApplicationContext());
        task.execute(mItemDrawableId);

        //TODO add rating here
        mWtratingText.setText("Clever Belly Rating : "+getArguments().getFloat(CategoryFragment.ITEM_RATING));

        mAdapter = new HomeItemDetailCustomAdapter(getActivity().getApplicationContext(), mDetailKeysList1, mDetailValuesList1);
        mDetailParamsList.setAdapter(mAdapter);

        mAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsInMyCart) {
                    DatabaseQueries.updateQuery(DatabaseHandler.In_Cart, 1, mItemId, getActivity().getApplicationContext());
                    DatabaseQueries.updateQuery(DatabaseHandler.Count_In_Cart, 1, mItemId, getActivity().getApplicationContext());
                    mAddToCart.setBackgroundColor(getActivity().getApplication().getResources().getColor(R.color.gray));
                    mIsInMyCart = true;
                } else {
                    Toast.makeText(getActivity(), "Already added in your cart!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }
}
