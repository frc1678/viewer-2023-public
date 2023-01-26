package com.example.viewer_2022

import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentActivity
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
    // Returns the field value for the given Team, Match, and Field requested
//    purple_cube.png
    var fieldValue = StartupActivity.databaseReference?.tim?.get(matchNumber)?.get(teamNumber)
        ?.get(field)?.jsonPrimitive?.content
    fieldValue = when (fieldValue) {
        "U" -> "🟪"
        "O" -> "▲"
        else -> fieldValue
    }
    return fieldValue
}
