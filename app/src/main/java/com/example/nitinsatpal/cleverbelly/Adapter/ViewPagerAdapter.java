package com.example.nitinsatpal.cleverbelly.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nitinsatpal.cleverbelly.BitmapWorkerTask;
import com.example.nitinsatpal.cleverbelly.R;

public class ViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    int[] images;
    String[] message;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, String[] message, int[] images) {
        this.context = context;
        this.message = message;
        this.images = images;
    }

    @Override
    public int getCount() {
        return message.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Declare Variables
        TextView msg;
        ImageView img;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container, false);

        msg = (TextView) itemView.findViewById(R.id.message);
        msg.setText(message[position]);

        img = (ImageView) itemView.findViewById(R.id.scrolling_image);
        img.setImageResource(images[position]);
        BitmapWorkerTask task = new BitmapWorkerTask(img, context);
        task.execute(images[position]);
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
