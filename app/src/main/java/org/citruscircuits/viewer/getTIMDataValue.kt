package org.citruscircuits.viewer

import kotlinx.serialization.json.jsonPrimitive

/**
 * Gets a value from the TIM data.
 * @param teamNumber The team number for the TIM Value
 * @param field The field to get the value for
 */
fun getTIMDataValue(teamNumber: String, field: String): Map<String, String?> {
    val matchNumList = getMatchSchedule(listOf(teamNumber)).keys.sortedBy { it.toIntOrNull() }
    val result: MutableMap<String, String?> = mutableMapOf()
    for (matchNumber in matchNumList) {
        result[matchNumber] =
            StartupActivity.databaseReference?.tim?.get(matchNumber)?.get(teamNumber)
                ?.get(field)?.jsonPrimitive?.content
    }
    return result
}