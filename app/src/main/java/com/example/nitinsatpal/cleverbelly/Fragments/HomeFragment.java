package com.example.nitinsatpal.cleverbelly.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.example.nitinsatpal.cleverbelly.Adapter.HomeGridCustomAdapter;
import com.example.nitinsatpal.cleverbelly.Adapter.ViewPagerAdapter;
import com.example.nitinsatpal.cleverbelly.CustomViews.ExpandableHeightGridView;
import com.example.nitinsatpal.cleverbelly.Database.DatabaseHandler;
import com.example.nitinsatpal.cleverbelly.Database.DatabaseQueries;
import com.example.nitinsatpal.cleverbelly.HomeActivity;
import com.example.nitinsatpal.cleverbelly.Models.Item;
import com.example.nitinsatpal.cleverbelly.R;
import com.example.nitinsatpal.cleverbelly.Util;
import com.example.nitinsatpal.cleverbelly.ViewPagerIndocator.CirclePageIndicator;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private ImageView mViewPagerNextButton;
    private ImageView mViewPagerPreviosButton;
    private ExpandableHeightGridView mGridView;
    private Handler mViewPagerHandler;
    private Runnable mUpdate;
    private Timer mSwipeTimer = new Timer();
    private TimerTask mTimerTask;
    private CirclePageIndicator mCirclePageIndicator;
    private int mCurrentPage;
    private int mTotalPages;

    public final static String CATEGORY_TYPE = "category_type";
    public final static String CATEGORY_ID = "category_id";

    private String[] messages = new String[] {"Mom gives me healthy breakfast!", "My healthy meal after workout..", "I am sweet addict, but its healthy!", "Eat healthy, Live healthy!"};
    private int[] scrolling_images = new int[] {R.drawable.scrolling_image1, R.drawable.scrolling_image2, R.drawable.scrolling_image3, R.drawable.scrolling_image4};

    /* GET FROM DATABASE*/
    public static String [] mCategoryList = {"Beverages","Breakfast Cereal","Ready To Cook","Sweet Tooth","Snacks","Condiments",
            "Cooking","Dairy","Frozen, Canned Food"};
    public static int[] mCategoryIdList = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    public static int [] mCategoryIcons = {R.drawable.home_grid_beverages, R.drawable.home_grid_cereals, R.drawable.home_grid_ready_to_cook,
            R.drawable.home_grid_sweet_tooth, R.drawable.home_grid_snacks, R.drawable.home_grid_condiments, R.drawable.home_grid_cooking,
            R.drawable.home_grid_diary, R.drawable.home_grid_canned_frozen};

    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle("Home");
        ArrayList<Item> mItemList = DatabaseQueries.getCtegoryDetails(-1, DatabaseHandler.Item_Name, getActivity().getApplicationContext(), "");
        Util.setItemList(mItemList);

        mViewPagerHandler = new Handler();
        startTimer();
        mViewPagerNextButton = (ImageView) rootView.findViewById(R.id.next_button);
        mViewPagerPreviosButton = (ImageView) rootView.findViewById(R.id.previous_button);
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mCirclePageIndicator = (CirclePageIndicator) rootView.findViewById(R.id.indicator);
        mGridView = (ExpandableHeightGridView) rootView.findViewById(R.id.category_grid_view);
        mGridView.setAdapter(new HomeGridCustomAdapter(getActivity(), mCategoryList, mCategoryIcons));
        Log.v("Hello", " .. "+mViewPager.requestFocus());
        mGridView.setExpanded(true);
        mViewPager.requestFocusFromTouch();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString(CATEGORY_TYPE, mCategoryList[position]);
                bundle.putInt(CATEGORY_ID, mCategoryIdList[position]);
                CategoryFragment categoryFragment = new CategoryFragment();
                categoryFragment.setArguments(bundle);
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, categoryFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        mViewPagerNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeTimer.cancel();
                mViewPager.setCurrentItem(mCurrentPage, true);
                mSwipeTimer = new Timer();
                startTimer();
            }
        });

        mViewPagerPreviosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeTimer.cancel();
                mCurrentPage = mCurrentPage-2;
                mViewPager.setCurrentItem(mCurrentPage, true);
                mSwipeTimer = new Timer();
                startTimer();
            }
        });

        mViewPagerAdapter = new ViewPagerAdapter(getActivity(), messages, scrolling_images);
        mViewPager.setAdapter(mViewPagerAdapter);

        mCirclePageIndicator.setViewPager(mViewPager);
        mCirclePageIndicator.setSnap(true);

        final float density = getResources().getDisplayMetrics().density;
        // mCirclePageIndicator.setBackgroundColor(getApplicationContext().getColor(R.color.bkg_gradient_bottom_to_top));
        mCirclePageIndicator.setRadius(5 * density);
        mCirclePageIndicator.setPageColor(0x880000FF);
        mCirclePageIndicator.setFillColor(0xFF888888);
        mCirclePageIndicator.setStrokeColor(0xFF000000);
        mCirclePageIndicator.setStrokeWidth(2 * density);
        /*--- Runnable for auto swipe ---*/
        mTotalPages = mViewPagerAdapter.getCount();
        mUpdate = new Runnable() {
            public void run() {
                if (mCurrentPage == mTotalPages) {
                    mCurrentPage = 0;
                }
                mViewPager.setCurrentItem(mCurrentPage++, true);
            }
        };

        mCirclePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                if (position == 0) mViewPagerPreviosButton.setVisibility(View.GONE);
                if (position == mTotalPages - 1) mViewPagerNextButton.setVisibility(View.GONE);
                if (position != 0) mViewPagerPreviosButton.setVisibility(View.VISIBLE);
                if(position != mTotalPages - 1) mViewPagerNextButton.setVisibility(View.VISIBLE);
                if (mCurrentPage == mTotalPages) {
                    mCurrentPage = 0;
                }
            }
        });

        return rootView;
    }


    public void startTimer() {
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mViewPagerHandler.post(mUpdate);
            }
        };
        mSwipeTimer.schedule(mTimerTask, 0, 4000);
    }
}