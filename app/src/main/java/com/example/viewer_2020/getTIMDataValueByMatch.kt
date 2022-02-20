package com.example.viewer_2020

import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.data.TeamInMatch

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
        val objTim = StartupActivity.databaseReference!!.obj_tim.find {
            it.match_number.toString() == matchNumber && it.team_number.toString() == teamNumber
        }
        val tbaTim = StartupActivity.databaseReference!!.tba_tim.find {
            it.match_number.toString() == matchNumber && it.team_number.toString() == teamNumber
        }
        timObject = TeamInMatch(matchNumber.toInt(), teamNumber.toInt())
        timObject.objTim = objTim
        timObject.tbaTim = tbaTim
        MainViewerActivity.timCache.add(timObject)
    }
    var fieldValue = getDirectField(timObject.objTim!!, field).toString()
    if (fieldValue == Constants.NULL_CHARACTER) {
        fieldValue = getDirectField(timObject.tbaTim!!, field).toString()
    }
    return fieldValue
}
