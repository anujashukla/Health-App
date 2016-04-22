package com.example.nitinsatpal.cleverbelly.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nitinsatpal.cleverbelly.BitmapWorkerTask;
import com.example.nitinsatpal.cleverbelly.Database.DatabaseHandler;
import com.example.nitinsatpal.cleverbelly.Database.DatabaseQueries;
import com.example.nitinsatpal.cleverbelly.Models.Item;
import com.example.nitinsatpal.cleverbelly.R;
import com.example.nitinsatpal.cleverbelly.Util;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HomeCategoryGridCustomAdapter extends BaseAdapter {
    private ArrayList<Item> mItemList;
    Context mContext;
    Holder holder;
    int clickPosition;
    private static LayoutInflater inflater=null;
    public HomeCategoryGridCustomAdapter(Context context, ArrayList<Item> itemList) {
        mItemList = itemList;
        mContext = context;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView item_name;
        ImageView item_icon;
        RelativeLayout item_rate_fav_bar;
        TextView item_rating;
        ImageView item_fav_button;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (convertView == null) {
            rowView = inflater.inflate(R.layout.home_grid_item, null);
            holder = new Holder();
            RelativeLayout gridItemlayout = (RelativeLayout) rowView.findViewById(R.id.home_grid_item_layout);
            holder.item_name =(TextView) rowView.findViewById(R.id.category_name);
            holder.item_icon =(ImageView) rowView.findViewById(R.id.category_icon);
            holder.item_rate_fav_bar = (RelativeLayout) rowView.findViewById(R.id.rate_fav_bar);
            holder.item_rating = (TextView) rowView.findViewById(R.id.rating);
            holder.item_fav_button = (ImageView) rowView.findViewById(R.id.fav_button);

            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();

            holder.item_icon.getLayoutParams().height = (display.getWidth()/2 - (int) mContext.getResources().getDimension(R.dimen.grid_spacing));
            holder.item_icon.requestLayout();
            holder.item_rate_fav_bar.setVisibility(View.VISIBLE);
            rowView.setTag(holder);
        } else
            holder= (Holder) rowView.getTag();

        if(mItemList.get(position).ismIsInMyTummyFuel())
            holder.item_fav_button.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_favourite_selected));
        else
            holder.item_fav_button.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_favourite_unselected));
        holder.item_rating.setText("" + mItemList.get(position).getmWTRating());
        holder.item_fav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemList.get(position).ismIsInMyTummyFuel()) {
                    mItemList.get(position).setmIsInMyTummyFuel(0);
                    notifyDataSetChanged();
                    DatabaseQueries.updateQuery(DatabaseHandler.In_Tummy_Fuel, 0, mItemList.get(position).getmItemId(), mContext);
                } else {
                    mItemList.get(position).setmIsInMyTummyFuel(1);
                    notifyDataSetChanged();
                    DatabaseQueries.updateQuery(DatabaseHandler.In_Tummy_Fuel, 1, mItemList.get(position).getmItemId(), mContext);
                }
            }
        });

        holder.item_name.setText(mItemList.get(position).getmItemName());
        BitmapWorkerTask task = new BitmapWorkerTask(holder.item_icon, mContext);
        task.execute(mItemList.get(position).getmItemIconDrawableId());
        //task.execute(R.drawable.beverage_item1);

        return rowView;
    }
}