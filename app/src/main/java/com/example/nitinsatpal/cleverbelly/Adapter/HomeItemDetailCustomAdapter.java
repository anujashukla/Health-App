package com.example.nitinsatpal.cleverbelly.Adapter;

import java.util.ArrayList;
import java.util.TreeSet;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nitinsatpal.cleverbelly.BitmapWorkerTask;
import com.example.nitinsatpal.cleverbelly.R;

import org.w3c.dom.Text;

public class HomeItemDetailCustomAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;


    private LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<String> mDetailKeysList1;
    private ArrayList<String> mDetailValueList1;
    public HomeItemDetailCustomAdapter(Context context, ArrayList<String> detailKeysList1, ArrayList<String> detailValueList1) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        mDetailKeysList1 = detailKeysList1;
        mDetailValueList1 = detailValueList1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 4) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getCount() {
        return mDetailKeysList1.size();
    }

    @Override
    public String getItem(int position) {
        return mDetailKeysList1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.fragment_home_category_detail_listitem, null);
                    holder.detail_param_title = (TextView) convertView.findViewById(R.id.detail_param_name);
                    holder.detail_param_value = (TextView) convertView.findViewById(R.id.detail_param_value);
                    holder.detail_param_icon = (ImageView) convertView.findViewById(R.id.detail_param_icon);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.fragment_home_category_detail_list_sectionheader, null);
                    holder.section_header = (TextView) convertView.findViewById(R.id.section_header);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        switch (rowType) {
            case TYPE_ITEM:
                holder.detail_param_title.setText(mDetailKeysList1.get(position));
                holder.detail_param_value.setText(mDetailValueList1.get(position));

                break;
            case TYPE_SEPARATOR:
                break;
        }

        return convertView;
    }

    public static class ViewHolder {
        TextView detail_param_value;
        TextView detail_param_title;
        ImageView detail_param_icon;
        TextView section_header;
    }

}