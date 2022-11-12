package com.example.viewer_2022

import android.util.Log

val teamNumberRegex = Regex("(\\d*)(\\w*)")

fun getTeamName(teamNumber: String): String? {
    val groups = teamNumberRegex.find(teamNumber)?.groups ?: return null
    val parsedTeamNumber = if (groups.size > 1) groups[1]?.value else null
    val parsedLetter = if (groups.size > 2) groups[2]?.value else null
    return parsedTeamNumber?.let { realTeamNumber ->
        getTeamDataValue(realTeamNumber, "team_name")?.let { teamName ->
        if (teamName == "UNKNOWN NAME") {
            return null
        }
        buildString {
            append(teamName)
            if (parsedLetter != null && parsedLetter.isNotEmpty()) {
                append(" Robot $parsedLetter")
            }
        }
    } }
}