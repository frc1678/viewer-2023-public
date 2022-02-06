package com.example.viewer_2020

import android.util.Log
import com.example.viewer_2020.data.Match
import com.example.viewer_2020.data.WebsiteMatchSchedule

fun getMatchSchedule(teamNumber: String? = null, starred: Boolean = false): Map<String, Match> {
    if (starred) {
        val tempMatches = mutableMapOf<String, Match>()
        val staredMatches = mutableMapOf<String, Match>()
        val returnedMatches = mutableMapOf<String, Match>()
        for (i in MainViewerActivity.matchCache) {
            if (MainViewerActivity.starredMatches.contains(i.value.matchNumber)) {
                tempMatches[i.key] = i.value
            }
        }
        if (teamNumber != null) {
            for (i in MainViewerActivity.matchCache) {
                if ((teamNumber in i.value.redTeams) or (teamNumber in i.value.blueTeams)) {
                    staredMatches[i.key] = i.value
                }
            }
            for (i in tempMatches) {
                for (x in staredMatches) {
                    if (i == x) {
                        returnedMatches[x.key] = x.value
                    }
                }
            }
            return returnedMatches
        }
        else {
            return tempMatches
        }

    } else if (teamNumber != null) {
        val tempMatches = mutableMapOf<String, Match>()
        for (i in MainViewerActivity.matchCache) {
            if ((teamNumber in i.value.redTeams) or (teamNumber in i.value.blueTeams)) {
                tempMatches[i.key] = i.value
            }
        }
        return tempMatches
    }

    return MainViewerActivity.matchCache
}
