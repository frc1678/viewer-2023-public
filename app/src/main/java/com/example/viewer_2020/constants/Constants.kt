/*
* Constants.kt
* viewer
*
* Created on 1/26/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2022.constants

//Class that contains a collection of Constant values, or final values that never change
class Constants {
    companion object {
        //Game specfific data.
        const val DATABASE_NAME = "test2021isjo"
        const val MONGO_ATLAS = "mongodb-atlas"
        const val MY_TEAM_NUMBER = "1678"
        const val USE_TEST_DATA = true
        const val CARDINAL_KEY = "6260ddb0f1414290375cc01f0d7739d79149ac9c"
        //In order to change the URL, see the GeDataFromWebsite Async Task

        val FIELDS_TO_BE_DISPLAYED: List<String> = listOf(
            "processed",
            "raw"
        )

        val FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS_PLAYED: List<String> = listOf(
            "Auto",
            "auto_line",
            "start_position",
            "auto_balls_low",
            "auto_balls_high",
            "Tele",
            "tele_balls_low",
            "tele_balls_high",
            "tele_lows",
            "tele_launchpad_highs",
            "tele_hub_highs",
            "tele_other_highs",
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

        val FIELDS_TO_BE_DISPLAYED_TEAM_DETAILS: List<String> = listOf(
            "current_rps",
            "current_rank",
            "current_avg_rps",
            "predicted_rps",
            "predicted_rank",
            "first_pickability",
            "second_pickability",
            "Auto",
            "auto_line_successes",
            "auto_avg_balls_low",
            "auto_avg_balls_high",
            "auto_avg_balls_total",
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
            "avg_exit_ball_catches",
            "max_exit_ball_catches",
            "avg_opp_balls_scored",
            "max_opp_balls_scored",
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

        val FIELDS_TO_BE_DISPLAYED_LFM: List<String> = listOf(
            "LFM Auto",
            "lfm_auto_avg_lows",
            "lfm_auto_avg_hub_highs",
            "lfm_auto_avg_launchpad_highs",
            "lfm_auto_avg_other_highs",
            "lfm_auto_avg_balls_low",
            "lfm_auto_avg_balls_high",
            "lfm_auto_max_balls_low",
            "lfm_auto_max_balls_high",
            "lfm_mode_start_position",
            "LFM Tele",
            "lfm_tele_avg_lows",
            "lfm_tele_avg_hub_highs",
            "lfm_tele_avg_launchpad_highs",
            "lfm_tele_avg_other_highs",
            "lfm_tele_avg_balls_low",
            "lfm_tele_avg_balls_high",
            "lfm_tele_max_balls_low",
            "lfm_tele_max_balls_high",
            "lfm_max_incap",
            "lfm_avg_incap_time",
            "lfm_matches_incap",
            "lfm_avg_exit_ball_catches",
            "lfm_max_exit_ball_catches",
            "lfm_avg_opp_balls_scored",
            "lfm_max_opp_balls_scored",
            "LFM Endgame",
            "lfm_climb_percent_success",
            "lfm_climb_all_attempts",
            "lfm_low_rung_successes",
            "lfm_mid_rung_successes",
            "lfm_high_rung_successes",
            "lfm_traversal_rung_successes",
            "lfm_max_climb_level",
            "lfm_low_avg_time",
            "lfm_mid_avg_time",
            "lfm_high_avg_time",
            "lfm_traversal_success_avg_time"
        )

        val FIELDS_TO_BE_DISPLAYED_RANKING: List<String> = listOf(
            "current_rank",
            "current_avg_rps",
            "current_rps",
            "predicted_rps",
            "predicted_rank"
        )

        val FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS_HEADER_NOT_PLAYED: List<String> = listOf(
            "predicted_score",
            "predicted_rp1",
            "predicted_rp2"
        )

        val FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS_HEADER_PLAYED: List<String> = listOf(
            "actual_score",
            "actual_rp1",
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
            "traversal_avg_time",
            "mode_climb_level"
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
            "lfm_auto_avg_lows" to true,
            "lfm_auto_avg_hub_highs" to true,
            "lfm_auto_avg_launchpad_highs" to true,
            "lfm_auto_avg_other_highs" to true,
            "lfm_auto_avg_balls_low" to true,
            "lfm_auto_avg_balls_high" to true,
            "lfm_auto_max_balls_low" to true,
            "lfm_auto_max_balls_high" to true,
            "lfm_tele_avg_lows" to true,
            "lfm_tele_avg_hub_highs" to true,
            "lfm_tele_avg_launchpad_highs" to true,
            "lfm_tele_avg_other_highs" to true,
            "lfm_tele_avg_balls_low" to true,
            "lfm_tele_avg_balls_high" to true,
            "lfm_tele_max_balls_low" to false,
            "lfm_tele_max_balls_high" to false,
            "lfm_max_incap" to false,
            "lfm_avg_incap_time" to false,
            "lfm_matches_incap" to false,
            "lfm_avg_exit_ball_catches" to true,
            "lfm_max_exit_ball_catches" to true,
            "lfm_avg_opp_balls_scored" to true,
            "lfm_max_opp_balls_scored" to true,
            "lfm_climb_percent_success" to true,
            "lfm_climb_all_attempts" to true,
            "lfm_low_rung_successes" to true,
            "lfm_mid_rung_successes" to true,
            "lfm_high_rung_successes" to true,
            "lfm_traversal_rung_successes" to true,
            "lfm_low_avg_time" to true,
            "lfm_mid_avg_time" to true,
            "lfm_high_avg_time" to true,
            "lfm_traversal_success_avg_time" to true
        )

        val CATEGORY_NAMES = listOf("Auto", "Tele", "Endgame", "Fouls", "Other", "Pit Data")


        //String literal translations.
        const val TEAM_NUMBER = "teamNumber"
        const val MATCH_NUMBER = "matchNumber"
        const val BLUE = "blue"
        const val RED = "red"

        //Util.
        const val NULL_PREDICTED_SCORE_CHARACTER = "-"
        const val NULL_CHARACTER = "?"
        const val EMPTY_CHARACTER = ""
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
    enum class ScheduleType {
        ALL_MATCHES,
        OUR_MATCHES,
        STARRED_MATCHES
    }


}
