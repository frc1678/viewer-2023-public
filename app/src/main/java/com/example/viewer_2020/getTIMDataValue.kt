package com.example.viewer_2020

import android.util.Log
import com.example.viewer_2020.constants.Constants

fun getTIMDataValue(teamNumber: String, field: String) : Map<String, String> {
    val matches = getMatchSchedule(teamNumber)
    Log.e("here", "${matches.keys}")
    Log.e("here", "${matches.values}")
    val matchNums : List<String> = matches.keys.toList()
    val numColorMap : MutableMap<String, String> = mutableMapOf()
    val numValueMap : MutableMap<String, String> = mutableMapOf()

    for(matchNum in matchNums){
        if (teamNumber in matches[matchNum]!!.redTeams){
            numColorMap[matchNum] = "red"
        }
        else if (teamNumber in matches[matchNum]!!.blueTeams){
            numColorMap[matchNum] = "blue"
        }
    }

    for(match in numColorMap){
        numValueMap[match.key] = getAllianceInMatchObjectByKey(Constants.PROCESSED_OBJECT.CALCULATED_OBJECTIVE_TEAM_IN_MATCH.value,
            match.value, match.key, field)
    }

    return numValueMap
}