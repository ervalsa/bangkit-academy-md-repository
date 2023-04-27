package com.palsaloid.storydicoding.ui.setting

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.palsaloid.storydicoding.R
import com.palsaloid.storydicoding.databinding.FragmentSettingBinding
import java.util.Locale

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadLocale()
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChangeLanguage.setOnClickListener {
            val languages = arrayOf("English", "Indonesia")

            val langSelectorBuilder = AlertDialog.Builder(requireActivity())
            langSelectorBuilder.setTitle(resources.getString(R.string.change_language) + " :")
            langSelectorBuilder.setSingleChoiceItems(languages, -1) { dialog, selection ->
                when(selection) {
                    0 -> {
                        setLocale("en")
                    }
                    1 -> {
                        setLocale("in")
                    }
                }
                requireActivity().recreate()
                dialog.dismiss()
            }
            langSelectorBuilder.create().show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setLocale(localToSet: String) {
        val localeListToSet = LocaleList(Locale(localToSet))
        LocaleList.setDefault(localeListToSet)
        resources.configuration.setLocales(localeListToSet)
        resources.updateConfiguration(resources.configuration, resources.displayMetrics)

        val sharedPref = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE).edit()
        sharedPref.putString("locale_to_set", localToSet)
        sharedPref.apply()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadLocale() {
        val sharedPref = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val localeToSet: String = sharedPref.getString("locale_to_set", "")!!
        setLocale(localeToSet)
    }
}