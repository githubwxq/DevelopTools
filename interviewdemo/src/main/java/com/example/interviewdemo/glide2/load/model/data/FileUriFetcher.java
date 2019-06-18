package com.example.interviewdemo.glide2.load.model.data;

import android.content.ContentResolver;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/5/7.
 */

public class FileUriFetcher implements DataFetcher<InputStream> {

    private final Uri uri;
    private final ContentResolver cr;

    public FileUriFetcher(Uri uri, ContentResolver cr) {
        this.uri = uri;
        this.cr = cr;
    }

    @Override
    public void loadData(DataFetcherCallback<? super InputStream> callback) {
        InputStream is = null;
        try {
            is = cr.openInputStream(uri);
            callback.onFetcherReady(is);
        } catch (FileNotFoundException e) {
            callback.onLoadFaled(e);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void cancel() {

    }
    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }
}
