package com.example.viewer_2022

import android.util.Log
import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.fragments.team_ranking.floatToString

fun getRankingList(datapoint: String, descending: Boolean): Leaderboard {
    if (MainViewerActivity.leaderboardCache.containsKey(datapoint)) {
        Log.d("getRankingList", "using cached leaderboard")
        val leaderboard = MainViewerActivity.leaderboardCache[datapoint] as Leaderboard
        val rankedTeams = leaderboard.map {
            it.teamNumber
        }
        val nullTeams = (MainViewerActivity.teamList - rankedTeams).map {
            return@map TeamRankingItem(it, Constants.NULL_CHARACTER, -1)
        }

        val finalLeaderboard = if (descending) leaderboard.reversed() else leaderboard
        return finalLeaderboard + nullTeams
    }
    Log.d("getRankingList", "generating new leaderboard")

    val data = mutableListOf<TeamUnplacedItem>()

    MainViewerActivity.teamList.forEach {
        val value = getTeamDataValue(it, datapoint).toFloatOrNull()
        data.add(
            TeamUnplacedItem(
                it,
                value?.let { it1 -> floatToString(it1) } ?: Constants.NULL_CHARACTER))
    }

    val nullTeams = data.filter { item -> item.value == Constants.NULL_CHARACTER }
    val dataTeams = data.filter { item -> item.value != Constants.NULL_CHARACTER }

    val unidirectionalSortedData = dataTeams.sortedWith(compareBy { it.value.toFloatOrNull() })

    val sortedData = if (descending) unidirectionalSortedData.reversed() else unidirectionalSortedData


    val orderedDataList = sortedData.mapIndexed { index, item ->
        TeamRankingItem(
            item.teamNumber,
            item.value,
            if (item.value != Constants.NULL_CHARACTER) index + 1 else -1
        )
    }

    val orderedNullList = nullTeams.mapIndexed { index, item ->
        TeamRankingItem(
            item.teamNumber,
            item.value,
            if (item.value != Constants.NULL_CHARACTER) index + 1 else -1
        )
    }
    val finalList = (orderedDataList + orderedNullList)

    MainViewerActivity.leaderboardCache[datapoint] = unidirectionalSortedData.mapIndexed { index, item ->
        TeamRankingItem(
            item.teamNumber,
            item.value,
            if (item.value != Constants.NULL_CHARACTER) index + 1 else -1
        )
    }

    return finalList
}

fun getRankingTeam(teamNumber: String, datapoint: String, descending: Boolean): String {
    val rankList = getRankingList(datapoint, descending)

        val teamEntry = rankList.first { it.teamNumber == teamNumber }

    return if (teamEntry.value == Constants.NULL_CHARACTER) {
        teamEntry.value
    } else{
        if(!descending){
            teamEntry.placement.toString()
        } else{
            (rankList.size - teamEntry.placement).toString()
        }
    }
}


data class TeamUnplacedItem(val teamNumber: String, val value: String)
data class TeamRankingItem(val teamNumber: String, val value: String, val placement: Int)
typealias Leaderboard = List<TeamRankingItem>
