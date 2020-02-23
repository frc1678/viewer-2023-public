/*
* Constants.kt
* viewer
*
* Created on 1/26/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2020.constants

//Class that contains a collection of Constant values, or final values that never change
class Constants {
    companion object {
        //Game specific data.
        const val TBA_EVENT_KEY = "2020cloudtest"
        const val DATABASE_NAME = "scouting_system_cloud"
        const val COLLECTION_NAME = "competitions"
        const val MONGO_ATLAS = "mongodb-atlas"
        const val MY_TEAM_NUMBER = "58"

        val FIELDS_TO_BE_DISPLAYED: List<String> = listOf(
            "processed"
        )

        val FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS: Map<String, ArrayList<String>> = mapOf(
            "Auto" to arrayListOf(
                "auto_avg_balls_low",
                "auto_avg_balls_high"
            ),
            "Tele" to arrayListOf(
                "tele_avg_balls_low",
                "tele_avg_balls_high",
                "tele_avg_time_incap"
            ),
            "Endgame" to arrayListOf(
                "climb_all_attempts"
            )
        )

        val FIELDS_TO_BE_DISPLAYED_TEAM_DETAILS: Map<String, ArrayList<String>> = mapOf(
            "Auto" to arrayListOf(
                "auto_avg_balls_low",
                "auto_avg_balls_high"
            ),
            "Tele" to arrayListOf(
                "tele_avg_balls_low",
                "tele_avg_balls_high",
                "tele_avg_time_incap"
            ),
            "Endgame" to arrayListOf(
                "climb_all_attempts"
            )
        )

        val FIELDS_TO_BE_DISPLAYED_RANKING_NAVIGATION_BAR: Map<String, String> = mapOf(
            "Current Seed" to "current_rank",
            "Predicted Seed" to "predicted_rank"
        )

        val FIELDS_TO_BE_DISPLAYED_SECTION_ONE_RANKING: List<String> = listOf(
            "current_seed",
            "current_rps",
            "current_avg_rps"
        )

        val FIELDS_TO_BE_DISPLAYED_SECTION_TWO_RANKING: List<String> = listOf(
            "predicted_rank",
            "predicted_rps"
        )

        val FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS_HEADER: List<String> = listOf(
            "predicted_rp1",
            "predicted_score",
            "predicted_rp2"
        )

        //String literal translations.
        const val TEAM_NUMBER = "teamNumber"
        const val MATCH_NUMBER = "matchNumber"
        const val BLUE = "blue"
        const val RED = "red"

        //Util.
        const val NULL_PREDICTED_SCORE_CHARACTER = "-"
        const val NULL_CHARACTER = "?"
        const val EMPTY_CHARACTER = ""
    }

    enum class TYPE_OBJECT {
        MATCH,
        TEAM
    }

    enum class PROCESSED_OBJECT(val value: String) {
        REPLAY_OUTDATED_QR("replay_outdated_qr"),
        UNCONSOLIDATED_OBJECTIVE_TEAM_IN_MATCH("unconsolidated_obj_tim"),
        CALCULATED_OBJECTIVE_TEAM_IN_MATCH("calc_obj_tim"),
        SUBJECTIVE_ALLIANCE_IN_MATCH("subj_aim"),
        CALCULATED_OBJECTIVE_TEAM("calc_obj_team"),
        CALCULATED_SUBJECTIVE_TEAM("calc_subj_team"),
        CALCULATED_MATCH("calc_match"),
        CALCULATED_PREDICTED_ALLIANCE_IN_MATCH("calc_predicted_aim"),
        CALCULATED_PREDICTED_TEAM("calc_predicted_team"),
        CALCULATED_TBA_TEAM("calc_tba_team"),
        CALCULATED_PICK_ABILITY_TEAM("calc_pick_ability_team"),
        CONSOLIDATED_OBJECTIVE_TEAM_IN_MATCH("consolidated_obj_tim"),
        CALCULATED_TEAM("calc_team")
    }
}