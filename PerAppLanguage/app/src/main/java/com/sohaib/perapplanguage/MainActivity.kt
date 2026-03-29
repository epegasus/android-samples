package com.sohaib.perapplanguage

import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.doOnPreDraw
import com.sohaib.perapplanguage.dataProvider.DPCountries
import com.sohaib.perapplanguage.databinding.ActivityMainBinding
import com.sohaib.perapplanguage.databinding.StubLanguageDropDownBinding

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val dpCountries by lazy { DPCountries() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Using 'doOnPreDraw' & 'viewStub' for drop-down to behave normally
        binding.root.doOnPreDraw {
            initExposedDropDown()
        }


        // Use the following line to get applied locale (language code)
        val defaultLanguageCode = AppCompatDelegate.getApplicationLocales().toLanguageTags()
        Log.d(TAG, "onCreate: $defaultLanguageCode")
    }

    private fun initExposedDropDown() {
        val view = binding.vsLanguageMain.inflate()
        val stubBinding = StubLanguageDropDownBinding.bind(view)
        val languageNames = dpCountries.getLanguagesList().map { it.second }
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, languageNames)
        stubBinding.actLanguageMain.setAdapter(arrayAdapter)

        // Item Click Listener
        stubBinding.actLanguageMain.onItemClickListener = AdapterView.OnItemClickListener { _, _, p2, _ ->
            val listItem = dpCountries.getLanguagesList()[p2]
            val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(listItem.first)
            AppCompatDelegate.setApplicationLocales(appLocale)
        }
    }
}