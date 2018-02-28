package com.example.booklisting;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Рома on 2/12/2018.
 */

class QueryUtils {

    private static String LOG_TAG = BookActivity.class.getSimpleName();


    @Nullable
    private static URL createURL(String stringURL) {
        URL url;
        try {
            url = new URL(stringURL);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error with creating URL");
            return null;
        }
        return url;
    }


    private static String makeHttpRequest(URL url) throws IOException {
        HttpURLConnection urlConnection = null;
        String jsonResponse = "";
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setReadTimeout(15000);
            urlConnection.connect();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else Log.v(LOG_TAG, "Response code: " + statusCode);
        } catch (IOException e) {
            Log.v(LOG_TAG, "JSON response is not received");
        } finally {
            if (url != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    @Nullable
    private static List<Book> extractFeatureFromJSON(String bookJSON) {
        if (TextUtils.isEmpty(bookJSON)) {
            Log.v(LOG_TAG, "JSON response is empty");
            return null;
        }

        List<Book> books = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(bookJSON);

            JSONArray booksArray = baseJsonResponse.getJSONArray("items");

            for (int i = 0; i < booksArray.length(); i++) {
                JSONObject currentBook = booksArray.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                String title = volumeInfo.getString("title");

                String author = volumeInfo.getJSONArray("authors").getString(0);


                //Getting year of publishing
                String publishedDate = volumeInfo.getString("publishedDate");
                String[] parts = publishedDate.split("-");
                String year = parts[0];

                String description = volumeInfo.getString("description");

//                //Getting smallImageLink
                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                String smallImageLink = imageLinks.getString("smallThumbnail");

                String infoLink = volumeInfo.getString("infoLink");

                books.add(new Book(title, author, year, description, smallImageLink, infoLink));
            }

        } catch (Exception e) {
            Log.e("QueryUtils", "Problem parsing the books JSON results");
        }
        return books;
    }

    public static List<Book> fetchBookData(String requestUrl) {
        URL url = createURL(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request");
        }
        return extractFeatureFromJSON(jsonResponse);
    }


}