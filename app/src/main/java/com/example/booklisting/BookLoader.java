package com.example.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Рома on 2/12/2018.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    public static List<Book> books;
    private String mUrl;
    private static final String LOG_TAG = BookLoader.class.getSimpleName();

    @Override
    protected void onStartLoading() {

        Log.v(LOG_TAG, "onStartLoading() method");
        forceLoad();
    }

    public BookLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    public List<Book> loadInBackground() {
        if (mUrl ==null){
            return null;
        }
        books = QueryUtils.fetchBookData(mUrl);
        return books;
    }
}
