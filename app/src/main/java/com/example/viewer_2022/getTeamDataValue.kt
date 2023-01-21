/*
* getTeamDataValue.kt
* viewer
*
* Created on 3/5/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2022

import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.constants.Translations

// Parses through every local database key to return the value of the given field.
fun getTeamDataValue(teamNumber: String, field: String): String? {

        try {
            if (getTeamObjectByKey(
                    teamNumber, field
                ) != null
            ) {
                return if (field in Constants.PERCENT_DATA) {
                    ((getTeamObjectByKey(
                        teamNumber, field
                    )?.toFloat())?.times(100.0)).toString()
                } else {
                    replaceNums(getTeamObjectByKey(
                        teamNumber, field
                    ))
                }
            }
        } catch (e: Exception) {

        }

        try {
            if (getRawObjectByKey(
                    teamNumber, field
                ) != null
            ) {
                when (field) {
                    "drivetrain" -> {
                        return Translations.DRIVETRAIN[getRawObjectByKey(
                            teamNumber, field
                        )] ?: Constants.NULL_CHARACTER
                    }
                    "drivetrain_motor_type" -> {
                        return Translations.DRIVETRAIN_MOTOR_TYPE[getRawObjectByKey(
                            teamNumber, field
                        )] ?: Constants.NULL_CHARACTER
                    }
                    else -> {
                        return replaceNums(getRawObjectByKey(
                            teamNumber, field
                        ))
                    }
                }
            }
        } catch (e: Exception) {

        }

    return Constants.NULL_CHARACTER
}
private fun replaceNums(string: String?): String? {
    return string?.replace("ONE", "1")
        ?.replace("TWO", "2")
        ?.replace("THREE", "3")
        ?.replace("FOUR", "4")
        ?.replace("N1", "NONE")
}