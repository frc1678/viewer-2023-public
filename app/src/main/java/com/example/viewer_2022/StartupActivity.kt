package com.example.viewer_2022

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.data.GetDataFromFiles
import com.example.viewer_2022.data.getDataFromWebsite
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.mongodb_database_startup_splash_screen.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject

// Splash screen activity that waits for the data to pull from the MongoDB database until it
// begins the other Viewer activities. AKA once MainViewerActivity.databaseReference is not null,
// it will begin the actual viewer activity so ensure that all data is accessible before the viewer
// activity begins.
class StartupActivity : ViewerActivity() {
    companion object {
        var databaseReference: JsonObject? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mongodb_database_startup_splash_screen)
        supportActionBar?.hide()

        // Interface to access the DatabaseReference -> CompetitionObject object that
        // should be an exact replica of every WANTED data value from MongoDB.

        // 'response' is a CompetitionObject, so you should be able to access whatever datapoint
        // you want by referencing response. Example: response.raw.qr[0] -> specified value in database.
        // TODO Make not crash when permissions are denied.
        MainViewerActivity.refreshManager.start(lifecycleScope)

        lifecycleScope.launch { getData() }

    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
            != PackageManager.PERMISSION_GRANTED
        ) {
            try {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(

                        Manifest.permission.INTERNET
                    ),
                    100
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun getData() {
        if (Constants.USE_TEST_DATA) {
            GetDataFromFiles(this, {
                ContextCompat.startActivity(
                    this,
                    Intent(this, WelcomeActivity::class.java),
                    null
                )

            }) {
                Log.e("error", it)
                runOnUiThread {
                    // Stuff that updates the UI
                    Snackbar.make(splash_screen_layout, "Data Failed to load", 2500).show()
                }

            }.execute()
        } else {
            // Tries to get data from website when starting the app and throws an error if fails
            try {
                getDataFromWebsite()
                ContextCompat.startActivity(
                    this,
                    Intent(this, WelcomeActivity::class.java),
                    null
                )
            } catch (e: Throwable) {

                Log.e("error", "Error fetching data from website $e")
                runOnUiThread {
                    // Stuff that updates the UI
                    Snackbar.make(splash_screen_layout, "Data Failed to load", 2500).show()
                }
            }
        }
    }
}