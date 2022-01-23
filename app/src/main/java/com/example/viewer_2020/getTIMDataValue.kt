package com.example.viewer_2020

import android.util.Log

fun getTIMDataValue(teamNumber: String, field: String, path: String) : Map<String, String> {
    val matchNumList = getMatchSchedule(teamNumber).keys
    val result : MutableMap<String, String> = mutableMapOf()
    for (matchNumber in matchNumList) {
        for (`object` in getDirectField(MongoDatabaseStartupActivity.databaseReference!!, path)
                as List<*>) {
                    Log.e("important", getDirectField(`object`!!, "team_number").toString())
            if (getDirectField(`object`, "match_number").toString() == matchNumber &&
                getDirectField(`object`, "team_number").toString() == teamNumber
            ) {
               result[matchNumber] = getDirectField(`object`, field).toString()
            }
        }
    }
    return result
    }