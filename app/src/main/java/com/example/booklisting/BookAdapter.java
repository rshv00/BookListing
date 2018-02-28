package com.example.booklisting;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Рома on 2/7/2018.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Book currentBook = getItem(position);
        TextView title = itemView.findViewById(R.id.name_of_book);
        TextView author = itemView.findViewById(R.id.author);
        TextView year = itemView.findViewById(R.id.year);
        title.setText(currentBook.getTitle());
        author.setText(currentBook.getAuthors());
        year.setText(String.valueOf(currentBook.getYear()));

        ImageView picture = itemView.findViewById(R.id.picture);
        String imageLink = currentBook.getImageLink();
        DrawableAsyncTask asyncTask = new DrawableAsyncTask(imageLink);
        asyncTask.execute();
        Drawable drawable = asyncTask.getDrawable();
        if (drawable != null)
            picture.setImageDrawable(drawable);
        else picture.setImageDrawable(null);
        return itemView;
    }
}
