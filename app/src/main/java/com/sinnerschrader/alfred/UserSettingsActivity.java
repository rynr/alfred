package com.sinnerschrader.alfred;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.List;

public class UserSettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new UserSettingsFragment()).commit();
    }

}
