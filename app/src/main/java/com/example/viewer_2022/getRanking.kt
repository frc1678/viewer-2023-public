package com.example.viewer_2022

import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.fragments.team_ranking.floatToString

/**
 * Initializes a leaderboard for a given datapoint. The order of the leaderboard (ascending or
 * descending) is determined by [`Constants.RANKABLE_FIELDS`][Constants.RANKABLE_FIELDS]. Once the
 * leaderboard is generated, it is added to the [cache][MainViewerActivity.leaderboardCache]. This
 * function should be called for every datapoint on startup.
 */
fun createLeaderboard(datapoint: String) {
    val data = mutableListOf<TeamRankingItem>()
    MainViewerActivity.teamList.forEach {
        if (datapoint !in Constants.PIT_DATA) {
            val value = getTeamDataValue(it, datapoint)?.toFloatOrNull()
            data.add(
                TeamRankingItem(
                    it,
                    value?.let { it1 -> floatToString(it1) } ?: Constants.NULL_CHARACTER,
                    null
                )
            )
        } else {
            data.add(TeamRankingItem(it, getTeamDataValue(it, datapoint), null))
        }
    }

    val descending = Constants.RANKABLE_FIELDS[datapoint] ?: true

    val nullTeams = data.filter { it.value == Constants.NULL_CHARACTER }
    val nonNullTeams = data.filter { it.value != Constants.NULL_CHARACTER }

    var sorted = nonNullTeams.sortedBy {
        if (datapoint in Constants.PIT_DATA && datapoint != "drivetrain_motors") {
            (Constants.RANK_BY_PIT[it.value] ?: 0).toFloat()
        } else it.value?.toFloatOrNull()
    }

    if (descending) sorted = sorted.reversed()

    sorted.forEachIndexed { index, teamRankingItem ->
        if (index != 0 && sorted[index - 1].value == teamRankingItem.value) {
            sorted[index].placement = sorted[index - 1].placement
        } else {
            sorted[index].placement = index + 1
        }
    }

    MainViewerActivity.leaderboardCache[datapoint] = sorted + nullTeams
}

fun getRankingList(datapoint: String): Leaderboard {
    return MainViewerActivity.leaderboardCache[datapoint]!!
}

fun getRankingTeam(teamNumber: String, datapoint: String): TeamRankingItem? {
    val data = MainViewerActivity.leaderboardCache[datapoint]
    return data?.find { it.teamNumber == teamNumber }
}

data class TeamRankingItem(val teamNumber: String, val value: String?, var placement: Int?)
typealias Leaderboard = List<TeamRankingItem>
