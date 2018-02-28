package com.example.booklisting;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Рома on 2/14/2018.
 */

public class DrawableAsyncTask extends AsyncTask<URL, Void, Drawable> {
    private String mUrl;
    private Drawable mDrawable;

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    public DrawableAsyncTask(String mUrl) {
        this.mUrl = mUrl;
    }


    @Override
    protected void onPostExecute(Drawable drawable) {
    }

    @Override
    protected Drawable doInBackground(URL... urls) {

        try {
            InputStream is = new URL(mUrl).openStream();
            Drawable d = Drawable.createFromStream(is, null);
            is.close();
            setDrawable(d);
            return d;
        } catch (Exception e) {
            Log.v("DrawableAsyncTask", "Exc=" + e);
            return null;
        }
    }
}
