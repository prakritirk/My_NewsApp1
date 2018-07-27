package com.example.android.my_newsapp1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by prajbhanda on 7/22/2018.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.settings_activity );
    }

    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate( savedInstanceState );
            addPreferencesFromResource( R.xml.settings_main );

            Preference newsCategory = findPreference( getString( R.string.settings_category_key ) );
            bindPreferenceSummaryToValue( newsCategory );

        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener( this );
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences( preference.getContext() );
            String preferenceString = preferences.getString( preference.getKey(), "" );
            onPreferenceChange( preference, preferenceString );
        }


        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            preference.setSummary( stringValue );
            return true;
        }
    }
}