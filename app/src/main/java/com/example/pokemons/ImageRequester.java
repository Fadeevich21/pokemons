package com.example.pokemons;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageRequester extends AsyncTask<String, Void, Bitmap> {
    @SuppressLint("StaticFieldLeak")
    private ImageView imageView;

    public void execute(String urlString, ImageView imageView) {
        this.imageView = imageView;
        this.execute(urlString);
    }

    protected Bitmap doInBackground(String... urls) {
        return loadImageFromSite(urls[0]);
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }

    private Bitmap loadImageFromSite(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream inputstream = conn.getInputStream();
            return BitmapFactory.decodeStream(inputstream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}