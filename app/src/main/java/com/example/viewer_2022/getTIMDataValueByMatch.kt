package com.example.viewer_2022

import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.data.TeamInMatch
import kotlinx.serialization.json.*

private fun getTIMDocument(matchNumber: String, teamNumber: String): JsonObject? {
    return StartupActivity.databaseReference?.tim?.get(matchNumber)?.get(teamNumber)
}

/**
 * Gets a single requested TIM data value given the match number, team number, and data field.
 * @param matchNumber The match number to search for.
 * @param teamNumber The team number to search for in the match.
 * @param field The field to get the value of.
 * @return The value of the requested field.
 */
fun getTIMDataValueByMatch(matchNumber: String, teamNumber: String, field: String): String? {
    return StartupActivity.databaseReference?.tim?.get(matchNumber)?.get(teamNumber)?.get(field)?.jsonPrimitive?.content
}
