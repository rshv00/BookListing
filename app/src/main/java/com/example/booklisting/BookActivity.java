package com.example.booklisting;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    BookLoader loader;
    private static final String LOG_TAG = BookActivity.class.getSimpleName();
    private static StringBuilder booksRequestSb;
    String booksRequestUrl;
    private static final int LOADER_ID = 1;
    private LoaderManager loaderManager = getLoaderManager();
    BookAdapter mAdapter;
    EditText searchET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        loaderManager.initLoader(LOADER_ID, null, this);
        ListView booksListView = findViewById(R.id.list);
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        searchET = findViewById(R.id.search);
        booksListView.setAdapter(mAdapter);
        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book currentBook = mAdapter.getItem(i);
                Uri bookUri = Uri.parse(currentBook.getInfoLink());
                Intent bookSiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
                startActivity(bookSiteIntent);

            }
        });


        searchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String text = textView.getText().toString();
                booksRequestSb = new StringBuilder();
                booksRequestSb.append("https://www.googleapis.com/books/v1/volumes?q=");
                booksRequestSb.append(text);
                booksRequestUrl = booksRequestSb.toString();
                Log.v(LOG_TAG, booksRequestUrl);
                mAdapter.clear();
                loaderManager.restartLoader(LOADER_ID, null, BookActivity.this);
                return false;
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        Log.v(LOG_TAG, "onCreateLoader()");
        loader = new BookLoader(this, booksRequestUrl);
        return loader;

    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        mAdapter.clear();
        try {
            mAdapter.addAll(books);
        } catch (Exception e) {
            e.printStackTrace();
        }
        booksRequestSb = new StringBuilder();
        booksRequestUrl = null;
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();

    }
}
