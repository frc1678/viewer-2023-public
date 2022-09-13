package com.example.viewer_2022

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.viewer_2022.MainViewerActivity.*


/**
 * The activity that greets the user and asks them to choose a profile.
 */
class WelcomeActivity : ViewerActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Before showing anything, ask for permissions
        try {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE
                ), 100
            )
        } catch (e: Exception) {
            Log.e("WelcomeActivity", "Unable to request file permissions: $e")
        }
    }

    /**
     * Shows the welcome page after the file permissions are accepted/declined.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // TODO: show another prompt instead of crashing when permissions are denied
        if (grantResults.contains(PERMISSION_DENIED)) {
            throw Exception("File permissions denied, please reopen the app and accept file permissions")
        }

        // Create/read the user profile file, the starred matches file, and the starred teams file
        UserDatapoints.read(this)
        StarredMatches.read()
        StarredTeams.read()

        setContentView(R.layout.activity_welcome)

        val names = resources.getStringArray(R.array.user_array)

        val radioGroup = findViewById<RadioGroup>(R.id.profile_list)

        // Add the radio buttons to the radio group
        names.forEach {
            val radioButton = RadioButton(this)
            radioButton.text = it
            radioGroup.addView(radioButton)
        }

        // Find which profile is currently selected and show it
        val previouslySelected = UserDatapoints.contents?.get("selected")?.asString ?: "NONE"
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
