package com.example.android.my_newsapp1;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by PRAJBHANDA on 7/17/2018.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {


    /**
     * Query URL
     */
    private String mUrl;

    public NewsLoader(Context context, String url) {
        super( context );
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    public List<News> loadInBackground() {
        // Perform the HTTP request for earthquake data and process the response.
        if (mUrl == null) {
            return null;
        }
        List<News> news = QueryUtils.fetchNewsData( mUrl );
        return news;
    }

}
