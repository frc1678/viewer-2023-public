/*
* MainViewerActivity.kt
* viewer
*
* Created on 1/26/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2020

import android.Manifest
<<<<<<< HEAD
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
=======
import android.app.ActionBar
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
>>>>>>> 3e14a203e8397b7345d40b51cf52d7589afc689e
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
<<<<<<< HEAD
=======
import android.view.View
import android.widget.*
>>>>>>> 3e14a203e8397b7345d40b51cf52d7589afc689e
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.viewer_2020.constants.Constants
<<<<<<< HEAD
=======
import com.example.viewer_2020.constants.MatchDetailsConstants
>>>>>>> 3e14a203e8397b7345d40b51cf52d7589afc689e
import com.example.viewer_2020.data.GetDataFromWebsite
import com.example.viewer_2020.data.Match
import com.example.viewer_2020.data.Team
import com.example.viewer_2020.fragments.match_schedule.OurScheduleFragment
import com.example.viewer_2020.fragments.match_schedule.StarredMatchesFragment
import com.example.viewer_2020.fragments.pickability.PickabilityFragment
import com.example.viewer_2020.fragments.pickability.PickabilityMode
import com.example.viewer_2020.fragments.team_list.TeamListFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_main.*
<<<<<<< HEAD
import java.io.*
=======
import kotlinx.android.synthetic.main.mongodb_database_startup_splash_screen.*
import java.io.File
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.marginRight
import androidx.core.view.setPadding
import kotlinx.android.synthetic.main.map_popup.view.*
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.action_bar.*
>>>>>>> 3e14a203e8397b7345d40b51cf52d7589afc689e


// Main activity class that handles the dual fragment view.
class MainViewerActivity : ViewerActivity() {

    lateinit var toggle: ActionBarDrawerToggle


    private var matchScheduleFragment = MatchScheduleFragment()
    private var ourScheduleFragment = OurScheduleFragment()
    private var rankingFragment = RankingFragment()
    private var firstPickabilityFragment = PickabilityFragment(PickabilityMode.FIRST)
    private var secondPickabilityFragment = PickabilityFragment(PickabilityMode.SECOND)
    private val teamListFragment = TeamListFragment()
    private val preferencesFragment = PreferencesFragment()
    private val userPreferencesFragment = UserPreferencesFragment()

    private val frags: List<IFrag> =
        listOf(
            matchScheduleFragment,
            ourScheduleFragment,
            rankingFragment,
            firstPickabilityFragment,
            secondPickabilityFragment,
            teamListFragment,
            preferencesFragment,
            userPreferencesFragment
        )

    companion object {
        var currentRankingMenuItem: MenuItem? = null
        var teamCache: HashMap<String, Team> = HashMap()
        var matchCache: MutableMap<String, Match> = HashMap()
        var teamList: List<String> = listOf()
        var starredMatches: HashSet<String> = HashSet()
    }

    //Overrides back button to go back to last fragment.
    //Disables the back button and returns nothing when in the startup match schedule.
    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.container)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(GravityCompat.START)
        if (supportFragmentManager.fragments.last().tag == "rankings") {
            supportFragmentManager.popBackStack(0, 0)
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.nav_host_fragment, MatchScheduleFragment(), "matchSchedule")
                .commit()
        } else if (supportFragmentManager.backStackEntryCount > 1) supportFragmentManager.popBackStack()
    }

    fun reloadAllListViews(){
        frags.forEachIndexed { i, e ->
            Log.e("help", "refreshing $i")
            e.updateListView()
        }
    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            try {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    100
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        UserDatapoints.read(this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        setToolbarText(actionBar, supportActionBar)

        val drawerLayout: DrawerLayout = findViewById(R.id.container)
        val navView: NavigationView = findViewById(R.id.navigation)
        navView.setCheckedItem(R.id.nav_menu_match_schedule)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val matchScheduleFragment = MatchScheduleFragment()
        val ourScheduleFragment = OurScheduleFragment()
        val starredMatchesFragment = StarredMatchesFragment()
        val rankingFragment = RankingFragment()
        val firstPickabilityFragment = PickabilityFragment(PickabilityMode.FIRST)
        val secondPickabilityFragment = PickabilityFragment(PickabilityMode.SECOND)
        val teamListFragment = TeamListFragment()
        val preferencesFragment = PreferencesFragment()

        updateNavFooter()

        // Pull the set of starred matches from the shared preferences.
        starredMatches = HashSet(baseContext.getSharedPreferences("VIEWER", 0)
            .getStringSet("starredMatches", HashSet()) as HashSet<String>)

        //default screen when the viewer starts (after pulling data)
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.nav_host_fragment, matchScheduleFragment, "matchSchedule")
            .commit()

        Log.e("ALL_DATA_FROM_WEBSITE", "${StartupActivity.databaseReference}")

        data_refresh_button.setOnClickListener {
            data_refresh_button.isEnabled = false
            if (Constants.USE_TEST_DATA) {
                GetDataFromFiles(this, {
                    data_refresh_button.isEnabled = true
                    Snackbar.make(container, "Refreshed Data!", 2500).show()
                    reloadAllListViews()
                    updateNavFooter()
                }, {
                    data_refresh_button.isEnabled = true
                    Snackbar.make(container, "Data Failed to load", 2500).show()
                }).execute()
            } else {
                GetDataFromWebsite({
                    data_refresh_button.isEnabled = true
                    Snackbar.make(container, "Refreshed Data!", 2500).show()
                    reloadAllListViews()
                    updateNavFooter()
                }, {
                    data_refresh_button.isEnabled = true
                    Snackbar.make(container, "Data Failed to load", 2500).show()
                }).execute()
            }
        }

        navView.setNavigationItemSelectedListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.closeDrawer(
                GravityCompat.START
            )
            when (it.itemId) {

                R.id.nav_menu_match_schedule -> {
                    supportFragmentManager.popBackStack(0, 0)
                    supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.nav_host_fragment, matchScheduleFragment, "matchSchedule")
                        .commit()
                }

                R.id.nav_menu_our_match_schedule -> {
                    val ft = supportFragmentManager.beginTransaction()
                    if (supportFragmentManager.fragments.last().tag != "ourSchedule") ft.addToBackStack(
                        null
                    )
                    ft.replace(R.id.nav_host_fragment, ourScheduleFragment, "ourSchedule")
                        .commit()
                }

                R.id.nav_menu_starred_matches -> {
                    val ft = supportFragmentManager.beginTransaction()
                    if (supportFragmentManager.fragments.last().tag != "starredMatches")
                        ft.addToBackStack(null)
                    ft.replace(R.id.nav_host_fragment, starredMatchesFragment, "starredMatches")
                        .commit()
                }

                R.id.nav_menu_rankings -> {
                    supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.nav_host_fragment, rankingFragment, "rankings")
                        .commit()
                }


                R.id.nav_menu_pickability_first -> {
                    val ft = supportFragmentManager.beginTransaction()
                    if (supportFragmentManager.fragments.last().tag != "pickabilityFirst") ft.addToBackStack(
                        null
                    )
                    ft.replace(R.id.nav_host_fragment, firstPickabilityFragment, "pickabilityFirst")
                        .commit()
                }

                R.id.nav_menu_pickability_second -> {
                    val ft = supportFragmentManager.beginTransaction()
                    if (supportFragmentManager.fragments.last().tag != "pickabilitySecond") ft.addToBackStack(
                        null
                    )
                    ft.replace(
                        R.id.nav_host_fragment,
                        secondPickabilityFragment,
                        "pickabilitySecond"
                    )
                        .commit()
                }

                R.id.nav_menu_team_list -> {
                    val ft = supportFragmentManager.beginTransaction()
                    if (supportFragmentManager.fragments.last().tag != "teamList") ft.addToBackStack(
                        null
                    )
                    ft.replace(R.id.nav_host_fragment, teamListFragment, "teamlist")
                        .commit()
                }

                R.id.nav_preferences -> {
                    val ft = supportFragmentManager.beginTransaction()
                    if (supportFragmentManager.fragments.last().tag != "preferences") ft.addToBackStack(
                        null
                    )
                    ft.replace(R.id.nav_host_fragment, preferencesFragment, "preferences")
                        .commit()
                }

            }

            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {

            return true

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar, menu)
        val mapItem : MenuItem = menu.findItem(R.id.map_button)
        val button = mapItem.actionView
        button.setOnClickListener {
            val popupView = View.inflate(this, R.layout.map_popup, null)
            val width = LinearLayout.LayoutParams.MATCH_PARENT
            val height = LinearLayout.LayoutParams.MATCH_PARENT
            val popupWindow = PopupWindow(popupView, width, height, false)
            popupWindow.showAtLocation(it, Gravity.CENTER, 0, 0)
            popupView.close_button.setOnClickListener {
                popupWindow.dismiss()
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    fun updateNavFooter(){
        findViewById<TextView>(R.id.nav_footer).text =
            getString(R.string.last_updated, super.getTimeText())
    }

    object UserDatapoints {

        var contents: JsonObject? = null
        var gson = Gson()

        private val file = File("/storage/emulated/0/${Environment.DIRECTORY_DOWNLOADS}/viewer_user_data_prefs.json")

        fun read(context: Context) {
            if (!fileExists()){
                copyDefaults(context)
            }
            try { contents = JsonParser.parseReader(FileReader(file)).asJsonObject }
            catch (e: Exception) {
                Log.e("UserDatapoints.read", "Failed to read user datapoints file")
            }
        }

        fun write() {
            var writer = FileWriter(file, false)
            gson.toJson(contents as JsonElement, writer)

            writer.close()
        }

        fun fileExists(): Boolean = file.exists()

        fun copyDefaults(context: Context){
            val inputStream : InputStream = context.resources.openRawResource(R.raw.default_prefs)

            try {
                val outputStream : OutputStream = FileOutputStream(file)

                val buffer = ByteArray(1024)
                var len: Int? = null
                while (inputStream.read(buffer, 0, buffer.size).also({ len = it }) != -1) {
                    outputStream.write(buffer, 0, len!!)
                }
                inputStream.close()
                outputStream.close()

            } catch (e: Exception) {
                Log.e("copyDefaults", "Failed to copy default preferences to file, $e")
            }

        }
    }

}