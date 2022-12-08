package com.example.viewer_2022

import kotlinx.serialization.json.jsonPrimitive

/**
 * Gets a value from the TIM data.
 * @param teamNumber The team number for the TIM Value
 * @param field The field to get the value for
 */
fun getTIMDataValue(teamNumber: String, field: String): Map<String, String?> {
    val matchNumList = getMatchSchedule(listOf(teamNumber)).keys
    val result: MutableMap<String, String?> = mutableMapOf()
    for (matchNumber in matchNumList) {
        result[matchNumber] =
            StartupActivity.databaseReference?.tim?.get(matchNumber)?.get(teamNumber)
                ?.get(field)?.jsonPrimitive?.content
    }
    return result
}