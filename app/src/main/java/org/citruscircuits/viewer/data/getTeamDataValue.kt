package org.citruscircuits.viewer

import org.citruscircuits.viewer.constants.Constants
import org.citruscircuits.viewer.constants.Translations

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
                    getTeamObjectByKey(
                        teamNumber, field
                    )
                        //removes extra brackets and apostrophes (for Mode Start Position)
                        ?.replace("]", "")
                        ?.replace("\'", "")
                        ?.replace("[","")
                        ?.replace(",",", ") //adds space after comma to separate list items
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
                        return getRawObjectByKey(
                            teamNumber, field
                        ) ?: Constants.NULL_CHARACTER
                    }
                    "drivetrain_motor_type" -> {
                        return getRawObjectByKey(
                            teamNumber, field
                        ) ?: Constants.NULL_CHARACTER
                    }
                    "mode_start_position" -> {
                        getTeamObjectByKey(
                            teamNumber, field
                        ) ?.replace('[', ' ')
                            ?.replace(']', ' ')
                    }
                    else -> {
                        getRawObjectByKey(
                            teamNumber, field
                        )?.replace("O", "▲")
                            ?.replace("U", "🟪")
                    }
                }
            }
        } catch (e: Exception) {

        }

    return Constants.NULL_CHARACTER
}