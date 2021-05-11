package com.example.wumpusworldgame.menuItems.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.wumpusworldgame.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}