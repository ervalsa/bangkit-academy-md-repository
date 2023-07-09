package com.dicoding.courseschedule.ui.setting

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.work.WorkManager
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode
import java.util.*

class SettingsFragment : PreferenceFragmentCompat() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        themePreference?.setOnPreferenceChangeListener { preference, newValue ->
            newValue?.let { value ->
                value as String
                val mode = when (value.toUpperCase(Locale.ROOT)) {
                    NightMode.ON.name -> NightMode.ON
                    NightMode.OFF.name -> NightMode.OFF
                    else -> NightMode.AUTO
                }
                updateTheme(mode.value)
            }
            true
        }
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val prefNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        prefNotification?.setOnPreferenceChangeListener { preference, newValue ->
            if (newValue == true) {
                DailyReminder().setDailyReminder(requireContext())
            } else {
                DailyReminder().cancelAlarm(requireContext())
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}