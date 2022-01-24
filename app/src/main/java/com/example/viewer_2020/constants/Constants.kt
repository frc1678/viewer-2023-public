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
        const val USE_TEST_DATA = true
        //In order to change the URL, see the GeDataFromWebsite Async Task

        val FIELDS_TO_BE_DISPLAYED: List<String> = listOf(
            "processed",
            "raw"
        )

        val FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS_PLAYED: List<String> = listOf(
            "Auto",
            "start_position",
            "quintet",
            "auto_balls_low",
            "auto_balls_high",
            "auto_near_hub_highs",
            "auto_near_other_highs",
            "auto_line",
            "Tele",
            "tele_balls_low",
            "tele_balls_high",
            "tele_near_hub_lows",
            "tele_far_hub_lows",
            "tele_launchpad_highs",
            "tele_near_hub_highs",
            "tele_far_hub_highs",
            "tele_near_other_highs",
            "tele_far_other_highs",
            "Endgame",
            "climb_time",
            "climb_level",
            "Fouls",
            "exit_ball_catches",
            "opp_balls_scored",
            "Other",
            "intakes",
            "incap"
        )

        val FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS: List<String> = listOf(
            "Auto",
            "auto_balls_low",
            "Tele",
            "tele_balls_low",
            "Endgame",
            "avg_climb_time"
        )

        val FIELDS_TO_BE_DISPLAYED_TEAM_DETAILS: List<String> = listOf(
            "current_rps",
            "current_rank",
            "current_avg_rps",
            "predicted_rps",
            "predicted_rank",
            "first_pickability",
            "second_pickability",
            "Auto",
            "auto_avg_balls_low",
            "auto_avg_balls_high",
            "auto_avg_balls_total",
            "auto_line_successes",
            "auto_max_balls_low",
            "auto_max_balls_high",
            "auto_sd_balls_low",
            "auto_sd_balls_high",
            "mode_start_position",
            "Tele",
            "tele_avg_balls_low",
            "tele_avg_balls_high",
            "tele_avg_balls_total",
            "driver_ability",
            "driver_near_field_awareness",
            "driver_far_field_awareness",
            "driver_quickness",
            "tele_max_balls_low",
            "tele_max_balls_high",
            "tele_sd_balls_low",
            "tele_sd_balls_high",
            "max_incap",
            "avg_incap_time",
            "matches_incap",
            "Endgame",
            "climb_percent_success",
            "climb_all_attempts",
            "low_rung_successes",
            "mid_rung_successes",
            "high_rung_successes",
            "traversal_rung_successes",
            "mode_climb_level",
            "max_climb_level",
            "low_avg_time",
            "mid_avg_time",
            "high_avg_time",
            "traversal_avg_time",
            "avg_exit_ball_catches",
            "max_exit_ball_catches",
            "avg_opp_balls_scored",
            "max_opp_balls_scored",
            "Pit Data",
            "drivetrain",
            "drivetrain_motors",
            "drivetrain_motor_type",
            "can_climb",
            "has_ground_intake",
            "can_intake_terminal",
            "can_eject_terminal",
            "has_vision",
            "can_under_low_rung",
            "can_cheesecake"
        )

        val FIELDS_TO_BE_DISPLAYED_RANKING: List<String> = listOf(
            "current_rank",
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

        val GRAPHABLE: List<String> = listOf(
            "auto_avg_balls_low",
            "auto_avg_balls_high",
            "tele_avg_balls_low",
            "tele_avg_balls_high",
            "avg_incap_time",
            "avg_exit_ball_catches",
            "avg_opp_balls_scored",
            "matches_incap"
        )

        val GRAPHABLE_BOOL: List<String> = listOf(
            "auto_line_successes",
            "climb_percent_success",
            "climb_all_attempts",
            "low_rung_successes",
            "mid_rung_successes",
            "high_rung_successes",
            "traversal_rung_successes"
        )

        val GRAPHABLE_CLIMB_TIMES: List<String> = listOf(
            "climb_all_success_avg_time",
            "low_avg_time",
            "mid_avg_time",
            "high_avg_time",
            "traversal_avg_time"
        )

      val DRIVER_DATA: List<String> = listOf(
            "driver_quickness",
            "driver_field_awareness",
            "driver_ability"
        )

        val PERCENT_DATA: List<String> = listOf(
            "climb_percent_success"
        )

        //List of rankable fields
        //The boolean is if it should be sorted descending
        //Not if it is rankable or not
        //True means descending and false means ascending
        //All items in the list are rankable no matter what
        val RANKABLE_FIELDS = mapOf<String, Boolean>(
            "current_rps" to true,
            "current_avg_rps" to true,
            "predicted_rps" to true,
            "first_pickability" to true,
            "second_pickability" to true,
            "auto_avg_balls_low" to true,
            "auto_avg_balls_high" to true,
            "auto_avg_balls_total" to true,
            "auto_line_successes" to true, //?????
            "auto_max_balls_low" to true,
            "auto_max_balls_high" to true,
            "auto_sd_balls_low" to true,
            "auto_sd_balls_high" to true,
            "tele_avg_balls_low" to true,
            "tele_avg_balls_high" to true,
            "tele_avg_balls_total" to true,
            "driver_ability" to false,
            "driver_near_field_awareness" to false,
            "driver_far_field_awareness" to false,
            "driver_quickness" to false,
            "tele_max_balls_low" to false,
            "tele_max_balls_high" to false,
            "tele_sd_balls_low" to false,
            "tele_sd_balls_high" to false,
            "max_incap" to false,
            "avg_incap_time" to false,
            "matches_incap" to false,
            "climb_percent_success" to true,
            "climb_all_attempts" to true,
            "low_rung_successes" to true,
            "mid_rung_successes" to true,
            "high_rung_successes" to true,
            "traversal_rung_successes" to true,
            "low_avg_time" to true,
            "mid_avg_time" to true,
            "high_avg_time" to true,
            "traversal_avg_time" to true,
            "avg_exit_ball_catches" to true,
            "max_exit_ball_catches" to true,
            "avg_opp_balls_scored" to true,
            "max_opp_balls_scored" to true,
            "drivetrain_motors" to true
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
        const val VERSION_NUM = "1.3.19"
    }

    enum class PROCESSED_OBJECT(val value: String) {
        CALCULATED_OBJECTIVE_TEAM_IN_MATCH("obj_tim"),
        CALCULATED_OBJECTIVE_TEAM("obj_team"),
        CALCULATED_SUBJECTIVE_TEAM("subj_team"),
        CALCULATED_PREDICTED_ALLIANCE_IN_MATCH("predicted_aim"),
        CALCULATED_PREDICTED_TEAM("predicted_team"),
        CALCULATED_TBA_TEAM("tba_team"),
        CALCULATED_PICKABILITY("pickability"),
        CALCULATED_TBA_TEAM_IN_MATCH("tba_tim")
    }

}
