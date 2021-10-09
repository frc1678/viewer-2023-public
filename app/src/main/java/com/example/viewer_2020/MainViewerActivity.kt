/*
* MainViewerActivity.kt
* viewer
*
* Created on 1/26/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2020

import android.app.AlertDialog
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.viewer_2020.data.DatabaseReference
import com.example.viewer_2020.data.Match
import com.example.viewer_2020.data.Team
import com.google.android.material.navigation.NavigationView
import java.io.File


// Main activity class that handles the dual fragment view.
class MainViewerActivity : ViewerActivity() {

    lateinit var toggle : ActionBarDrawerToggle

    companion object {
        var currentRankingMenuItem: MenuItem? = null
        var teamCache: HashMap<String, Team> = HashMap()
        var matchCache: HashMap<String, Match> = HashMap()
    }

    fun verifyCSVFileExists(file: String) {
        val csvFile = File("/storage/emulated/0/${Environment.DIRECTORY_DOWNLOADS}/$file")
        if (!csvFile.exists()) {
            AlertDialog.Builder(this).setMessage("There is no CSV file on this device").show()
        }
    }

    //Overrides back button to go back to last fragment.
    //Disables the back button and returns nothing when in the startup match schedule.
    override fun onBackPressed() {
        var backCount = supportFragmentManager.backStackEntryCount
        if (backCount == 1) {
            return
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        verifyCSVFileExists("match_schedule.csv")
        setToolbarText(actionBar, supportActionBar)

        val drawerLayout : DrawerLayout = findViewById(R.id.container)
        val navView : NavigationView = findViewById(R.id.navigation)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val matchScheduleFragment = MatchScheduleFragment()
        val rankingFragment = RankingFragment()
        val bundle = Bundle()

        //default screen when the viewer starts (after pulling data)
        bundle.putString("selection", "Match Schedule")
        matchScheduleFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.nav_host_fragment, matchScheduleFragment, "matchSchedule")
            .commit()

        Log.e("ALL_DATA_FROM_WEBSITE","${MongoDatabaseStartupActivity.databaseReference}")

        navView.setNavigationItemSelectedListener {

            when(it.itemId) {

                R.id.nav_menu_match_schedule -> {
                    bundle.putString("selection", "Match Schedule")
                    matchScheduleFragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.nav_host_fragment, matchScheduleFragment, "matchSchedule")
                        .commit()
                }

                R.id.nav_menu_our_match_schedule -> {
                    bundle.putString("selection", "Our Schedule")
                    matchScheduleFragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.nav_host_fragment, matchScheduleFragment, "matchSchedule")
                        .commit()
                }

                R.id.nav_menu_rankings -> {
                    supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.nav_host_fragment, rankingFragment, "rankings")
                        .commit()
                }
            }

            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){

            return true

        }
        return super.onOptionsItemSelected(item)
    }
}