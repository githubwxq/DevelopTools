package com.example.interviewdemo.glide2.load.model.data;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2018/5/7.
 */

public class HttpUriFetcher implements DataFetcher<InputStream> {

    private final Uri uri;
    private boolean isCanceled;

    public HttpUriFetcher(Uri uri) {
        this.uri = uri;
    }

    @Override
    public void loadData(DataFetcherCallback<? super InputStream> callback) {
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            URL url = new URL(uri.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            is = conn.getInputStream();
            int responseCode = conn.getResponseCode();
            if (isCanceled) {
                return;
            }
            if (responseCode == HttpURLConnection.HTTP_OK) {
                callback.onFetcherReady(is);
            } else {
                callback.onLoadFaled(new RuntimeException(conn.getResponseMessage()));
            }
        } catch (Exception e) {
            callback.onLoadFaled(e);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != conn) {
                conn.disconnect();
            }
        }
    }

    @Override
    public void cancel() {
        isCanceled = true;
    }

    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }
}
