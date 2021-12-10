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
        const val DATABASE_NAME = "test2020cadm"
        const val MONGO_ATLAS = "mongodb-atlas"
        const val MY_TEAM_NUMBER = "1678"
        //In order to change the URL, see the GeDataFromWebsite Async Task

        val FIELDS_TO_BE_DISPLAYED: List<String> = listOf(
            "processed",
            "raw"
        )

        val FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS_PLAYED: List<String> = listOf(
            "Auto",
            "auto_balls_low",
            "auto_balls_high",
            "Tele",
            "tele_balls_low",
            "tele_balls_high",
            "Other",
            "incap",
            "climb_time"
        )

        val FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS_NOT_PLAYED: List<String> = listOf(
            "Auto",
            "auto_avg_balls_low",
            "auto_avg_balls_high",
            "auto_max_balls_low",
            "auto_max_balls_high",
            "Tele",
            "tele_avg_balls_low",
            "tele_avg_balls_high",
            "tele_max_balls_low",
            "tele_max_balls_high",
            "max_incap",
            "Endgame",
            "climb_percent_success",
            "climb_all_attempts",
            "climb_all_success_avg_time",
            "Other",
            "driver_quickness",
            "driver_field_awareness",
            "driver_ability"
        )

        val FIELDS_TO_BE_DISPLAYED_TEAM_DETAILS: List<String> = listOf(
            "current_rps",
            "current_rank",
            "current_avg_rps",
            "predicted_rps",
            "predicted_rank",
            "Auto",
            "auto_avg_balls_low",
            "auto_avg_balls_high",
            "auto_line_successes",
            "auto_max_balls_low",
            "auto_max_balls_high",
            "Tele",
            "tele_avg_balls_low",
            "tele_avg_balls_high",
            "driver_ability",
            "driver_field_awareness",
            "driver_quickness",
            "can_cross_trench",
            "tele_max_balls_low",
            "tele_max_balls_high",
            "max_incap",
            "avg_incap_time",
            "Endgame",
            "climb_percent_success",
            "climb_all_attempts",
            "climb_all_successes",
            "climb_level_successes",
            "climb_all_success_avg_time",
            "park_successes",
            "Pit Data",
            "drivetrain",
            "drivetrain_motors",
            "drivetrain_motor_type",
            "has_ground_intake"
        )

        val FIELDS_TO_BE_DISPLAYED_RANKING: List<String> = listOf(
            "current_seed",
            "current_avg_rps",
            "current_rps",
            "predicted_rps",
            "predicted_rank"
        )

        val FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS_HEADER_NOT_PLAYED: List<String> = listOf(
            "predicted_rp1",
            "predicted_score",
            "predicted_rp2"
        )

        val FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS_HEADER_PLAYED: List<String> = listOf(
            "actual_rp1",
            "actual_score",
            "actual_rp2"
        )

      val DRIVER_DATA: List<String> = listOf(
            "driver_quickness",
            "driver_field_awareness",
            "driver_ability"
        )

          val PERCENT_DATA: List<String> = listOf(
            "climb_percent_success"
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
        const val RANKING_POINT_CHARACTER = "•"
        const val PREDICTED_RANKING_POINT_QUALIFICATION = 0.65
        const val VERSION_NUM = "1.3.18"
    }

    enum class PROCESSED_OBJECT(val value: String) {
        CALCULATED_OBJECTIVE_TEAM_IN_MATCH("obj_tim"),
        CALCULATED_OBJECTIVE_TEAM("obj_team"),
        CALCULATED_SUBJECTIVE_TEAM("subj_team"),
        CALCULATED_PREDICTED_ALLIANCE_IN_MATCH("predicted_aim"),
        CALCULATED_PREDICTED_TEAM("predicted_team"),
        CALCULATED_TBA_TEAM("tba_team"),
        CALCULATED_PICKABILITY("pickability")
    }
}
