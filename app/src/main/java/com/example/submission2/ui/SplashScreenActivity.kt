package com.example.submission2.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewPropertyAnimator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submission2.R
import com.example.submission2.data.SettingPreferences
import com.example.submission2.ui.viewmodel.SettingViewModel
import com.example.submission2.ui.viewmodel.SettingViewModelFactory

class SplashScreenActivity : AppCompatActivity() {

    private var propertyAnim: ViewPropertyAnimator? = null
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        val image = findViewById<ImageView>(R.id.img_splash)
        val pref = SettingPreferences.getInstance(dataStore)
        val appViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        image.alpha = 0f

        propertyAnim = image.animate().setDuration(4_000L).alpha(1f).withEndAction {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }

        appViewModel.getThemeSettings().observe(this, { state ->
            Log.d("Splash", "$state")

            val mode = if (state) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO

            AppCompatDelegate.setDefaultNightMode(mode)

            propertyAnim?.start()
        })
    }

    override fun onDestroy() {
        propertyAnim?.cancel()
        super.onDestroy()
    }
}