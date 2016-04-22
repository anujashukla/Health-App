package com.example.nitinsatpal.cleverbelly.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nitinsatpal.cleverbelly.BitmapWorkerTask;
import com.example.nitinsatpal.cleverbelly.Fragments.TrackMyDietFragment;
import com.example.nitinsatpal.cleverbelly.Models.Item;
import com.example.nitinsatpal.cleverbelly.R;

import java.util.ArrayList;

/**
 * Created by nitinsatpal on 01-02-2016.
 */

public class TrackMyDietListCustomAdapter extends BaseAdapter {

    ArrayList<Item> mDietList;
    Context mContext;
    private static LayoutInflater inflater=null;
    public TrackMyDietListCustomAdapter(Context context, ArrayList<Item> dietlist) {
        // TODO Auto-generated constructor stub
        mDietList = dietlist;
        mContext = context;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mDietList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView item_name;
        ImageView item_icon;
        TextView item_rate;
        CheckBox checkBox;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        final Holder holder;
        View rowView = convertView;
        if (convertView == null) {
            rowView = inflater.inflate(R.layout.fragment_track_my_diet_list_item, null);
            holder = new Holder();
            holder.item_name = (TextView) rowView.findViewById(R.id.item_name);
            holder.item_icon = (ImageView) rowView.findViewById(R.id.item_icon);
            holder.item_rate = (TextView) rowView.findViewById(R.id.item_rate);
            holder.checkBox = (CheckBox) rowView.findViewById(R.id.checkbox);

            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();

            holder.item_icon.getLayoutParams().height = (display.getWidth()/2 - (int) mContext.getResources().getDimension(R.dimen.grid_spacing));
            holder.item_icon.requestLayout();

            rowView.setTag(holder);
        } else
            holder= (Holder) rowView.getTag();

        holder.item_name.setText(mDietList.get(position).getmItemName());
        holder.item_rate.setText(mDietList.get(position).getmWTRating() + " / 5");
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    TrackMyDietFragment.updateCalorie(Float.parseFloat(mDietList.get(position).getmCalorieDensity()), true);
                    //TrackMyDietFragment.mMyCheckedDiet.add(mDietList.get(position));
                } else {
                    TrackMyDietFragment.updateCalorie(Float.parseFloat(mDietList.get(position).getmCalorieDensity()), false);
                    //if (TrackMyDietFragment.mMyCheckedDiet.contains(mDietList.get(position)))
                    //    TrackMyDietFragment.mMyCheckedDiet.remove(mDietList.get(position));
                }
            }
        });

        BitmapWorkerTask task = new BitmapWorkerTask(holder.item_icon, mContext);
        task.execute(mDietList.get(position).getmItemIconDrawableId());

        return rowView;
    }
}
