package com.example.viewer_2022

import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * Gets a value from the TIM data.
 * @param teamNumber The team number for the TIM Value
 * @param field The field to get the value for
 * @param path The collection of data to get the value from
 */
fun getTIMDataValue(teamNumber: String, field: String, path: String): Map<String, String> {
    val matchNumList = getMatchSchedule(listOf(teamNumber)).keys
    val result: MutableMap<String, String> = mutableMapOf()
    for (matchNumber in matchNumList) {
        for ((_, collectionElement) in StartupActivity.databaseReference!![path]!!.jsonObject) {
            val collectionObject = collectionElement.jsonObject
            if (collectionObject["team_number"]!!.jsonPrimitive.content == matchNumber &&
                collectionObject["match_number"]!!.jsonPrimitive.content == teamNumber) {
                result[matchNumber] = collectionObject[field]!!.jsonPrimitive.content
            }
        }
    }
    return result
}