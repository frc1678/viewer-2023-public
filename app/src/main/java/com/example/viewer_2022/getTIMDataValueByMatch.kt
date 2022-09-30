package com.example.viewer_2022

import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.data.TeamInMatch

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
        val subjTim = StartupActivity.databaseReference!!.subj_tim.find {
            it.match_number.toString() == matchNumber && it.team_number.toString() == teamNumber
        }
        timObject = TeamInMatch(teamNumber.toInt(), matchNumber.toInt())
        timObject.objTim = objTim
        timObject.tbaTim = tbaTim
        timObject.subjTim = subjTim
        MainViewerActivity.timCache.add(timObject)
    }
    var fieldValue = Constants.NULL_CHARACTER
    if (timObject.objTim != null) {
        fieldValue = getDirectField(timObject.objTim!!, field).toString()
    }
    if (fieldValue == Constants.NULL_CHARACTER) {
        if (timObject.tbaTim != null) {
            fieldValue = getDirectField(timObject.tbaTim!!, field).toString()
            if (fieldValue != Constants.NULL_CHARACTER) {
                return fieldValue
            }
        }
        if (timObject.subjTim != null) {
            fieldValue = getDirectField(timObject.subjTim!!, field).toString()
        }
    }
    fieldValue = when (fieldValue) {
        "ONE" -> "1"
        "TWO" -> "2"
        "THREE" -> "3"
        "FOUR" -> "4"

        else -> fieldValue
    }
    return fieldValue
}
