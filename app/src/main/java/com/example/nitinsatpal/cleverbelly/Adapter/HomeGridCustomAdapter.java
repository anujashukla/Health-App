package com.example.nitinsatpal.cleverbelly.Adapter;

import android.content.Context;
import android.util.DisplayMetrics;
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

import com.example.nitinsatpal.cleverbelly.R;
import com.example.nitinsatpal.cleverbelly.BitmapWorkerTask;

public class HomeGridCustomAdapter extends BaseAdapter{

    String [] mCategoryList;
    Context mContext;
    int [] mCategoryIcons;
    private static LayoutInflater inflater=null;
    public HomeGridCustomAdapter(Context context, String[] categoryList, int[] categoryIcons) {
        // TODO Auto-generated constructor stub
        mCategoryList = categoryList;
        mContext = context;
        mCategoryIcons = categoryIcons;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mCategoryList.length;
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

    public class Holder
    {
        TextView category_name;
        ImageView category_icon;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder;
        View rowView = convertView;
        if (convertView == null) {
            rowView = inflater.inflate(R.layout.home_grid_item, null);
            holder = new Holder();
            RelativeLayout gridItemlayout = (RelativeLayout) rowView.findViewById(R.id.home_grid_item_layout);
            holder.category_name =(TextView) rowView.findViewById(R.id.category_name);
            holder.category_icon=(ImageView) rowView.findViewById(R.id.category_icon);

            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();

            holder.category_icon.getLayoutParams().height = (display.getWidth()/2 - (int) mContext.getResources().getDimension(R.dimen.grid_spacing));
            holder.category_icon.requestLayout();

            rowView.setTag( holder );
        } else
            holder= (Holder) rowView.getTag();

        holder.category_name.setText(mCategoryList[position]);

        BitmapWorkerTask task = new BitmapWorkerTask(holder.category_icon, mContext);
        task.execute(mCategoryIcons[position]);

        //holder.img.setImageResource(mCategoryIcons[position]);

        return rowView;
    }
}