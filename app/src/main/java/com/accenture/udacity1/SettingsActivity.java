package com.accenture.udacity1;

/**
 * Created by roger.chee.meng.lee on 19/08/2015.
 */

import com.accenture.udacity0.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity
        extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);

        // register a change listener
        Preference pref = findPreference(getString(R.string.pref_sort_key));
        pref.setOnPreferenceChangeListener(this);

        // use it to set the initial preference value (from known value or default)
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(pref.getContext());
        onPreferenceChange(pref, prefs.getString(pref.getKey(), getString(R.string.pref_sort_default)));
    }


    /**
     * On a preference change, make sure the summary is updated to reflect the changed value
     *
     * @param preference
     * @param newValue
     * @return
     */
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            preference.setSummary(stringValue);
        }
        return true;
    }
}
