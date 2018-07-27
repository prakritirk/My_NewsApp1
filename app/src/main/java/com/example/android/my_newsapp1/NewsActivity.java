package com.example.android.my_newsapp1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    public static final String LOG_TAG = NewsActivity.class.getName();
    private static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search?";
    /**
     * Constant value for the news loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;
    private static final String STUDENT_KEY = "62af3243-cd1c-4aa9-9afc-7f2e1ad5b19a";
    private NewsAdapter mAdapter;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_news );

        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = (ListView) findViewById( R.id.list );

        mEmptyStateTextView = (TextView) findViewById( R.id.empty_view );
        newsListView.setEmptyView( mEmptyStateTextView );


        // Create a new adapter that takes an empty list of newss as input
        mAdapter = new NewsAdapter( this, new ArrayList<News>() );
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter( mAdapter );


        newsListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News currentNews = mAdapter.getItem( i );
                String link = currentNews.getLink();
                Uri newsUri = Uri.parse( link );
                Intent intent = new Intent( Intent.ACTION_VIEW, newsUri );
                startActivity( intent );

            }
        } );
        ConnectivityManager cm = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE );

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {


            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader( NEWS_LOADER_ID, null, this );
        } else {
            ProgressBar progressView = (ProgressBar) findViewById( R.id.progress_view );
            progressView.setVisibility( View.GONE );

            mEmptyStateTextView.setText( R.string.no_internet );
        }


    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        Log.v( LOG_TAG, "onCreateLoader called" );
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences( this );
        String newsCategory = sharedPrefs.getString( getString( R.string.settings_category_key ), getString( R.string.settings_category_default ) );

        String category = newsCategory.toLowerCase();

        Uri baseUri = Uri.parse( GUARDIAN_REQUEST_URL );
        Uri.Builder uriBuilder = baseUri.buildUpon();
        if (!category.equalsIgnoreCase( "all" )) {
            uriBuilder.appendQueryParameter( "section", category );
        }
        uriBuilder.appendQueryParameter( "order-by", "newest" );
        uriBuilder.appendQueryParameter( "show-tags", "contributor" );
        uriBuilder.appendQueryParameter( "page-size", "15" );
        uriBuilder.appendQueryParameter( "api-key", STUDENT_KEY );

        return new NewsLoader( this, uriBuilder.toString() );


    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        // Set empty state text to display "No news found."
        ProgressBar progressView = (ProgressBar) findViewById( R.id.progress_view );
        progressView.setVisibility( View.GONE );

        mEmptyStateTextView.setText( R.string.no_news );
        mAdapter.clear();
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll( news );
        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();

    }

    @Override
    // This method initialize the contents of the Activity's options menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    @Override
    // This method is called whenever an item in the options menu is selected.
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent( this, SettingsActivity.class );
            startActivity( settingsIntent );
            return true;
        }
        return super.onOptionsItemSelected( item );

    }
}