package com.example.viewer_2022.data

import android.util.Log
import com.example.viewer_2022.MainViewerActivity
import com.example.viewer_2022.StartupActivity
import com.example.viewer_2022.constants.Constants

suspend fun getDataFromWebsite() {

    // Sets databaseReference to the data in Grosbeak for the given Event Key
    StartupActivity.databaseReference = DataApi.getViewerData(Constants.EVENT_KEY)

    Log.d("Viewer Data", "${StartupActivity.databaseReference!!.aim}")

    // Sets the teamList to the new team list for the competition
    // Gets this team list from grosbeak/api/team-list
    MainViewerActivity.teamList =
        DataApi.getTeamList(Constants.EVENT_KEY).map { it }

    // Pulls the match schedule from grosbeak and then puts it in rawMatchSchedule
    val rawMatchSchedule = DataApi.getMatchSchedule(Constants.EVENT_KEY)

    // For each team in the match schedule it adds the team to either the redTeams list of the blueTeams list
    for (i in rawMatchSchedule.toList().sortedBy { it.first.toInt() }.toMap()) {
        val match = Match(i.key)
        for (j in i.value.teams) {
            when (j.color) {
                "red" -> {
                    match.redTeams.add(j.number)
                }
                "blue" -> {
                    match.blueTeams.add(j.number)
                }
            }
        }

        MainViewerActivity.matchCache[i.key] = match
    }

}