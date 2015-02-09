package com.sinnerschrader.alfred;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class UserSettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
