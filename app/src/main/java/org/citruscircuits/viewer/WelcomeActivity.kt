package org.citruscircuits.viewer

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.citruscircuits.viewer.MainViewerActivity.*
import org.citruscircuits.viewer.constants.Constants


/**
 * The activity that greets the user and asks them to choose a profile.
 */
class WelcomeActivity : ViewerActivity(), ActivityCompat.OnRequestPermissionsResultCallback {
    // Storage Permissions
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyStoragePermissions(this)
//        Constants.STORAGE_FOLDER = getExternalFilesDir(null)!!
        Constants.DOWNLOADS_FOLDER = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        // Create/read the user profile file, the starred matches file, and the starred teams file
        UserDatapoints.read(this)
        StarredMatches.read()
        StarredTeams.read()
        EliminatedAlliances.read()

        setContentView(R.layout.activity_welcome)

        val names = resources.getStringArray(R.array.user_array)

        val radioGroup = findViewById<RadioGroup>(R.id.profile_list)
        //set default action Baar
        setToolbarText(actionBar, supportActionBar)
        // Add the radio buttons to the radio group
        names.forEach {
            val radioButton = RadioButton(this)
            radioButton.text = it
            radioGroup.addView(radioButton)
        }

        // Find which profile is currently selected and show it
        val previouslySelected = UserDatapoints.contents?.get("selected")?.asString ?: "OTHER"
        for (i in 0 until radioGroup.childCount) {
            val radioButton = radioGroup.getChildAt(i) as RadioButton
            if (radioButton.text.toString().uppercase() == previouslySelected.uppercase()) {
                radioButton.toggle()
                break
            }
        }

        findViewById<Button>(R.id.continue_button).setOnClickListener { onContinue() }
    }

    /**
     * When the 'continue' button is clicked, disable it, save the user preference, and go to the main activity.
     */
    private fun onContinue() {
        findViewById<Button>(R.id.continue_button).isEnabled = false
        val radioGroup = findViewById<RadioGroup>(R.id.profile_list)
        for (i in 0 until radioGroup.childCount) {
            val radioButton = radioGroup.getChildAt(i) as RadioButton
            if (radioButton.isChecked) {
                UserDatapoints.contents?.addProperty(
                    "selected", radioButton.text.toString().uppercase()
                )
                UserDatapoints.write()
                break
            }
        }
        ContextCompat.startActivity(
            this, Intent(this, MainViewerActivity::class.java), null
        )
    }

    /**
     * Don't do anything when the back button is pressed.
     */
    override fun onBackPressed() {}
}
