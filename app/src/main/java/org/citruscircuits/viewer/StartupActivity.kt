package org.citruscircuits.viewer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.startup_splash_screen.*
import kotlinx.coroutines.launch
import org.citruscircuits.viewer.constants.Constants
import org.citruscircuits.viewer.data.DataApi
import org.citruscircuits.viewer.data.getDataFromWebsite
import org.citruscircuits.viewer.data.loadTestData

// Splash screen activity that waits for the data to pull from the MongoDB database until it
// begins the other Viewer activities. AKA once MainViewerActivity.databaseReference is not null,
// it will begin the actual viewer activity so ensure that all data is accessible before the viewer
// activity begins.
class StartupActivity : ViewerActivity() {
    companion object {
        var databaseReference: DataApi.ViewerData? = null
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.startup_splash_screen)
        supportActionBar?.hide()
        if (Environment.isExternalStorageManager()) {
            // Permission is granted
            // Access the downloads folder here
            Constants.DOWNLOADS_FOLDER = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        } else {
            // Permission is not granted
            // Request permission from the user
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }
//        Constants.STORAGE_FOLDER = getExternalFilesDir(null)!!

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
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            try {
                ActivityCompat.requestPermissions(
                    this, arrayOf(

                        Manifest.permission.INTERNET
                    ), 100
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun getData() {
        try {
            if (Constants.USE_TEST_DATA) {
                loadTestData(this.resources)
            } else {
                // Tries to get data from website when starting the app and throws an error if fails
                getDataFromWebsite()
            }
            ContextCompat.startActivity(
                this, Intent(this, WelcomeActivity::class.java), null
            )
        } catch (e: Throwable) {
            Log.e(
                "data",
                "Error fetching data from ${if (Constants.USE_TEST_DATA) "files" else "website"}: ${
                    Log.getStackTraceString(
                        e
                    )
                }"
            )
            runOnUiThread {
                // Stuff that updates the UI
                btn_retry.visibility = View.VISIBLE
                Snackbar.make(
                    splash_screen_layout,
                    "Could not find match_schedule file for schedule key ${Constants.SCHEDULE_KEY}",
                    1000000000
                ).show()
            }
        }
    }

    fun btnRetryOnClick(view: View) {
        MainViewerActivity.refreshManager.start(lifecycleScope)

        lifecycleScope.launch { getData() }
    }
}