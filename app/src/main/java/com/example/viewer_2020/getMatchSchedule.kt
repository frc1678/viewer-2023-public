package com.example.viewer_2020

import com.example.viewer_2020.data.Match
import com.example.viewer_2020.data.WebsiteMatchSchedule

fun getMatchSchedule(teamNumber: String? = null): Map<String, Match> {
    if (teamNumber != null) {
        val tempMatches = mutableMapOf<String, Match>()
        for (i in MainViewerActivity.matchCache) {
            if (teamNumber in i.value.redTeams || teamNumber in i.value.blueTeams) tempMatches[i.key] =
                i.value
        }
        return tempMatches
    }

    return MainViewerActivity.matchCache
}
