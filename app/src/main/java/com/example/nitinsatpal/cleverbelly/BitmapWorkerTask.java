package com.example.nitinsatpal.cleverbelly;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
    private WeakReference<Bitmap> resultBitmap;
    private final WeakReference<ImageView> imageViewReference;
    private Context mContext;
    private int data = 0;

    public BitmapWorkerTask(ImageView imageView, Context context) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<ImageView>(imageView);
        mContext = context;
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(Integer... params) {
        data = params[0];
        return decodeSampledBitmapFromResource(mContext.getResources(), data, 100, 100);
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        /*if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }*/
        ImageView imageView = imageViewReference.get();
        if(bitmap != null) {
            resultBitmap = new WeakReference<Bitmap>(Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig()));
            //Bitmap b = Bitmap.createBitmap(resultBitmap, bitmap.getWidth(), bitmap.getHeight(), true);
            Canvas canvas = new Canvas(resultBitmap.get());
            canvas.drawBitmap(bitmap, 0, 0, null);
            canvas.drawBitmap(bitmap, bitmap.getWidth(), 0, null);
            if (imageView != null) {
                imageView.setImageBitmap(resultBitmap.get());
            }
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inJustDecodeBounds = true;
        //  BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    @Override
    protected void onCancelled(Bitmap result) {
        if(resultBitmap!=null) {
            resultBitmap.get().recycle();
            resultBitmap = null;
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}