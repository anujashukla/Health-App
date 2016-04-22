package com.example.nitinsatpal.cleverbelly.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nitinsatpal.cleverbelly.Database.DatabaseHandler;
import com.example.nitinsatpal.cleverbelly.Database.DatabaseQueries;
import com.example.nitinsatpal.cleverbelly.Fragments.MyTummyFuelFragment;
import com.example.nitinsatpal.cleverbelly.Models.Item;
import com.example.nitinsatpal.cleverbelly.R;
import com.example.nitinsatpal.cleverbelly.BitmapWorkerTask;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TummyFuelListCustomAdapter extends BaseAdapter {

    ArrayList<Item> mTummyFuelList;
    Context mContext;
    int [] mCategoryIcons;
    Boolean mIsTummyFuel;
    private static LayoutInflater inflater=null;
    public TummyFuelListCustomAdapter(Context context, ArrayList<Item> tummyItemList, boolean isTummyFuel) {
        // TODO Auto-generated constructor stub
        mTummyFuelList = tummyItemList;
        mContext = context;
        mIsTummyFuel = isTummyFuel;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mTummyFuelList.size();
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
        TextView item_name;
        ImageView item_icon;
        TextView item_rate;
        TextView remove_button;

        RelativeLayout inCartCountLayout;
        TextView increase;
        TextView count;
        TextView decrease;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder;
        View rowView = convertView;
        if (convertView == null) {
            rowView = inflater.inflate(R.layout.tummy_fuel_list_item_layout, null);
            holder = new Holder();
            holder.item_name = (TextView) rowView.findViewById(R.id.item_name);
            holder.item_icon = (ImageView) rowView.findViewById(R.id.item_icon);
            holder.item_rate = (TextView) rowView.findViewById(R.id.item_rate);
            holder.remove_button = (TextView) rowView.findViewById(R.id.remove_button);

            holder.inCartCountLayout = (RelativeLayout) rowView.findViewById(R.id.in_cart_count_layout);
            holder.increase = (TextView) rowView.findViewById(R.id.increase);
            holder.count = (TextView) rowView.findViewById(R.id.count);
            holder.decrease = (TextView) rowView.findViewById(R.id.decrease);

            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();

            holder.item_icon.getLayoutParams().height = (display.getWidth()/2 - (int) mContext.getResources().getDimension(R.dimen.grid_spacing));
            holder.item_icon.requestLayout();

            rowView.setTag(holder);
        } else
            holder= (Holder) rowView.getTag();

        holder.item_name.setText(mTummyFuelList.get(position).getmItemName());
        holder.item_rate.setText(mTummyFuelList.get(position).getmWTRating()+" / 5");
        BitmapWorkerTask task = new BitmapWorkerTask(holder.item_icon, mContext);
        task.execute(mTummyFuelList.get(position).getmItemIconDrawableId());

        if(!mIsTummyFuel) {
            holder.inCartCountLayout.setVisibility(View.VISIBLE);
            holder.count.setText(""+mTummyFuelList.get(position).getmCartCount());
            if(mTummyFuelList.get(position).getmCartCount() == 1) {
                holder.decrease.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
            }
        }

        //holder.img.setImageResource(mCategoryIcons[position]);
        holder.remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsTummyFuel)
                    DatabaseQueries.updateQuery(DatabaseHandler.In_Tummy_Fuel, 0, mTummyFuelList.get(position).getmItemId(), mContext);
                else
                    DatabaseQueries.updateQuery(DatabaseHandler.In_Cart, 0, mTummyFuelList.get(position).getmItemId(), mContext);
                mTummyFuelList.remove(position);
                MyTummyFuelFragment.refreshList(mTummyFuelList.size());
            }
        });

        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseQueries.updateQuery(DatabaseHandler.Count_In_Cart, mTummyFuelList.get(position).getmCartCount() + 1, mTummyFuelList.get(position).getmItemId(), mContext);
                mTummyFuelList.get(position).setmCartCount(mTummyFuelList.get(position).getmCartCount() + 1);
                MyTummyFuelFragment.refreshList(mTummyFuelList.size());
                holder.decrease.setBackgroundColor(mContext.getResources().getColor(R.color.app_color));
            }
        });

        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTummyFuelList.get(position).getmCartCount()  > 1) {
                    DatabaseQueries.updateQuery(DatabaseHandler.Count_In_Cart, mTummyFuelList.get(position).getmCartCount() - 1, mTummyFuelList.get(position).getmItemId(), mContext);
                    mTummyFuelList.get(position).setmCartCount(mTummyFuelList.get(position).getmCartCount() - 1);
                }
                else if(mTummyFuelList.get(position).getmCartCount() == 1) {
                    holder.decrease.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                    Toast.makeText(mContext, "Can't reduce quantity, Please Remove the item!", Toast.LENGTH_SHORT).show();
                }
                MyTummyFuelFragment.refreshList(mTummyFuelList.size());
            }
        });
        return rowView;
    }
}
