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
    private ImageView view;

    public void execute (String urlString, ImageView v) {
        view = v;
        this.execute(urlString);
    }

    protected Bitmap doInBackground(String... urls) {
        return loadImageFromSite(urls[0]);
    }

    protected void onPostExecute(Bitmap result) {
        view.setImageBitmap(result);
    }

    private Bitmap loadImageFromSite (String urlString) {
        URL url = null;
        try {
            url= new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        try {
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
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