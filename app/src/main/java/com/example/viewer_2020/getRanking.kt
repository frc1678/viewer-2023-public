package com.example.viewer_2022

import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.fragments.team_ranking.floatToString

fun getRankingList(datapoint: String, descending: Boolean): List<TeamRankingItem> {
    if(MainViewerActivity.leaderboardCache.containsKey(datapoint)){
        val leaderboard = MainViewerActivity.leaderboardCache[datapoint] as Leaderboard
        if(descending){
            if(leaderboard.descending != null){
                return leaderboard.descending!!
            }
        } else {
            if(leaderboard.ascending != null){
                return leaderboard.ascending!!
            }
        }
    }

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

    var sortedData = dataTeams.sortedWith(compareBy { it.value.toFloatOrNull() })

    if (descending) sortedData = sortedData.reversed()

    val finalList = (sortedData + nullTeams).mapIndexed { index, item -> TeamRankingItem(item.teamNumber, item.value, index + 1) }
    if(MainViewerActivity.leaderboardCache.containsKey(datapoint)){
        val leaderboard = MainViewerActivity.leaderboardCache[datapoint] as Leaderboard
        if(descending){
            leaderboard.descending = finalList
        } else {
            leaderboard.ascending = finalList
        }
    } else {
        if(descending){
            MainViewerActivity.leaderboardCache[datapoint] = Leaderboard(descending = finalList)
        } else {
            MainViewerActivity.leaderboardCache[datapoint] = Leaderboard(ascending = finalList)

        }
    }
    return finalList
}

fun getRankingTeam(teamNumber: String, datapoint: String, descending: Boolean): String{
    val rankList = getRankingList(datapoint, descending)

    val teamEntry = rankList.first { it.teamNumber == teamNumber }

    return if(teamEntry.value == Constants.NULL_CHARACTER) teamEntry.value else teamEntry.placement.toString()
}


data class TeamUnplacedItem(val teamNumber: String, val value: String)
data class TeamRankingItem(val teamNumber: String, val value: String, val placement: Int)
data class Leaderboard(var descending: List<TeamRankingItem>? = null, var ascending: List<TeamRankingItem>? = null)
