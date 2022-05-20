package com.example.viewer_2022

import com.example.viewer_2022.data.Match

fun getMatchSchedule(
    teamNumbers: List<String> = listOf(),
    starred: Boolean = false
): Map<String, Match> {
    if (starred) {
        val starredMatches = mutableMapOf<String, Match>()
        val searchedMatches = mutableMapOf<String, Match>()
        val returnedMatches = mutableMapOf<String, Match>()
        for (i in MainViewerActivity.matchCache) {
            if (MainViewerActivity.starredMatches.contains(i.value.matchNumber)) {
                starredMatches[i.key] = i.value
            }
        }
        if (teamNumbers.isNotEmpty()) {
            for (i in MainViewerActivity.matchCache) {
                if (teamNumbers.size == 1) {
                    if ((teamNumbers[0] in i.value.redTeams) or (teamNumbers[0] in i.value.blueTeams)) {
                        searchedMatches[i.key] = i.value
                    }
                } else if (((teamNumbers[0] in i.value.redTeams) or (teamNumbers[0] in i.value.blueTeams))
                    and ((teamNumbers[1] in i.value.redTeams) or (teamNumbers[1] in i.value.blueTeams))
                ) {
                    searchedMatches[i.key] = i.value
                }
            }
            for (i in starredMatches) {
                for (x in searchedMatches) {
                    if (i == x) {
                        returnedMatches[x.key] = x.value
                    }
                }
            }
            return returnedMatches
        } else {
            return starredMatches
        }
    } else if (teamNumbers.isNotEmpty()) {
        val tempMatches = mutableMapOf<String, Match>()
        for (i in MainViewerActivity.matchCache) {
            if (teamNumbers.size == 1) {
                if ((teamNumbers[0] in i.value.redTeams) or (teamNumbers[0] in i.value.blueTeams)) {
                    tempMatches[i.key] = i.value
                }
            } else if (((teamNumbers[0] in i.value.redTeams) or (teamNumbers[0] in i.value.blueTeams))
                and ((teamNumbers[1] in i.value.redTeams) or (teamNumbers[1] in i.value.blueTeams))
            ) {
                tempMatches[i.key] = i.value
            }
        }
        return tempMatches
    }

    return MainViewerActivity.matchCache
}
