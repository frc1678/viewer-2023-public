package com.example.viewer_2022.data

import android.util.Log
import com.example.viewer_2022.DataApi
import com.example.viewer_2022.MainViewerActivity
import com.example.viewer_2022.StartupActivity
import com.example.viewer_2022.constants.Constants
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.buildJsonObject

suspend fun getDataFromWebsite() {

    // Sets
    MainViewerActivity.teamList =
        DataApi.getTeamList(Constants.EVENT_KEY)

    // Creates a list of all of the collection names
    var listOfCollectionNames: List<String> =
        listOf(
            "raw_obj_pit",
            "tba_tim",
            "obj_tim",
            "obj_team",
            "subj_team",
            "predicted_aim",
            "predicted_team",
            "tba_team",
            "pickability",
            "picklist",
            "subj_tim"
        )

    // Creates a mutable map to store all of the collections data
    val collections = mutableMapOf<String, JsonArray>()

    // For each of the collections it pulls the collection data from grosbeak
    // Then it puts each collection data into the mutable map collections
    for (collectionName in listOfCollectionNames) {
        val result =
            DataApi.getCollection(collectionName, Constants.EVENT_KEY)
        collections[collectionName] = result
        Log.d("dataFromWebsite", result.toString())
    }

    // Consolidates all of the data in collections into one Json object
    StartupActivity.databaseReference = buildJsonObject {
        for ((collectionName, data) in collections.entries) {
            put(collectionName, data)
        }
    }

    // Pulls the match schedule from grosbeak and then puts it in rawMatchSchedule
    val rawMatchSchedule = DataApi.getMatchSchedule(Constants.EVENT_KEY)

    // For each team in the match schedule it adds the team to either the redTeams list of the blueTeams list
    for (i in rawMatchSchedule) {
        val match = Match(i.key)
        for (j in i.value.teams) {
            when (j.color) {
                "red" -> {
                    match.redTeams.add(j.number.toString())
                }
                "blue" -> {
                    match.blueTeams.add(j.number.toString())
                }
            }
        }

        Log.d("parsedmap", match.toString())
        MainViewerActivity.matchCache[i.key] = match
    }

}