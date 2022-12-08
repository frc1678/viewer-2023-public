package com.example.viewer_2022

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

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
    var fieldValue = StartupActivity.databaseReference?.tim?.get(matchNumber)?.get(teamNumber)
        ?.get(field)?.jsonPrimitive?.content
    fieldValue = when (fieldValue) {
        "ONE" -> "1"
        "TWO" -> "2"
        "THREE" -> "3"
        "FOUR" -> "4"
        "TRAVERSAL" -> "LVL 4"
        "HIGH" -> "LVL 3"
        "MID" -> "LVL 2"
        "LOW" -> "LVL 1"
        else -> fieldValue
    }
    return fieldValue
}
