package com.palsaloid.dicodingstoryapp.ui.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.palsaloid.dicodingstoryapp.R

class PreferenceFragment : PreferenceFragmentCompat() {

    private lateinit var LANGUAGE: String

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preference_setting)
        init()
    }

    private fun init() {
        LANGUAGE = resources.getString(R.string.key_language)
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        if (preference.key == LANGUAGE) {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        return super.onPreferenceTreeClick(preference)
    }
}