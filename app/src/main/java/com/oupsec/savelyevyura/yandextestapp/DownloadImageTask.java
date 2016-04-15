package com.oupsec.savelyevyura.yandextestapp;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
* Created by savelyevyura on 07/04/16.
*/
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;
    private CacheClass mCache = new CacheClass();

    DownloadImageTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String url = params[0];
        Bitmap image = mCache.get(url);
        if (image == null) {
            try {
                InputStream stream = new URL(url).openStream();
                image = BitmapFactory.decodeStream(stream);
                mCache.put(url, image);
                Log.d("cache", "cached");
            } catch (Exception e) {
                Log.d("cache","not cached");
                image = BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.artist_unknown);
                e.printStackTrace();
            }
        }
        return image;
    }

    @SuppressLint("NewApi")
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            if (imageView != null)
                imageView.setImageBitmap(result);
        }

    }
}
