package com.example.viewer_2022

import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.data.TeamInMatch
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

private fun getTIMDocument(collection: String, matchNumber: String, teamNumber: String): JsonObject? {
    return StartupActivity.databaseReference?.get(collection)?.jsonArray?.find {
        val document = it.jsonObject
        document["match_number"]!!.jsonPrimitive.content == matchNumber &&
                document["team_number"]!!.jsonPrimitive.content == teamNumber
    }?.jsonObject
}

/**
 * Gets a single requested TIM data value given the match number and the team number. Inserts the
 * TIM object into the [TIM cache][MainViewerActivity.timCache] if not already present.
 *
 * @param matchNumber The match number to search for.
 * @param teamNumber The team number to search for in the match.
 * @param field The field to get the value of.
 * @return The value of the requested field.
 */
fun getTIMDataValueByMatch(matchNumber: String, teamNumber: String, field: String): String {
    var timObject: TeamInMatch? = MainViewerActivity.timCache.find {
        it.match_number.toString() == matchNumber && it.team_number.toString() == teamNumber
    }
    if (timObject == null) { // the TIM object was not found in the cache, needs to be created
        val objTim = getTIMDocument("obj_tim", matchNumber, teamNumber)
        val tbaTim = getTIMDocument("tba_tim", matchNumber, teamNumber)
        val subjTim = getTIMDocument("subj_tim", matchNumber, teamNumber)
        timObject = TeamInMatch(teamNumber.toInt(), matchNumber.toInt())
        timObject.objTim = objTim
        timObject.tbaTim = tbaTim
        timObject.subjTim = subjTim
        MainViewerActivity.timCache.add(timObject)
    }
    for (document in timObject.collections) {
            if (document != null && document.containsKey(field)) {
                return document[field]!!.jsonPrimitive.content
            }
    }
    return Constants.NULL_CHARACTER

}
