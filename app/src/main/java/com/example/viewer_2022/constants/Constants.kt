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
        //Game specific data.
        const val EVENT_KEY = "2022mttd"
        const val MONGO_ATLAS = "mongodb-atlas"
        const val MY_TEAM_NUMBER = "1678"
        const val USE_TEST_DATA = true
        const val CARDINAL_KEY = "a7f9d746a13f8ea86bd3fa9931c64ab8af8f2694"
        const val REFRESH_INTERVAL = 120

        //Util.
        const val NULL_PREDICTED_SCORE_CHARACTER = "-"
        const val NULL_CHARACTER = "?"
        const val EMPTY_CHARACTER = ""
        const val PREDICTED_RANKING_POINT_QUALIFICATION = 0.65
        const val VERSION_NUM = "5.0.0"

        val FIELDS_TO_BE_DISPLAYED: List<String> = listOf(
            "processed",
            "raw"
        )

        val FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS_PLAYED: List<String> = listOf(
            "Auto",
            "start_position",
            "auto_cube_low",
            "auto_cube_mid",
            "auto_cube_high",
            "auto_cone_low",
            "auto_cone_mid",
            "auto_cone_high",
            "auto_dock",
            "auto_engage",
            "auto_charge_attempt",
            "mobility",
            "Tele",
            "tele_cube_low",
            "tele_cube_mid",
            "tele_cube_high",
            "tele_cone_low",
            "tele_cone_mid",
            "tele_cone_high",
            "sustainability_rp",
            "charging_rp",
            "played_defense",
            "field_awareness_score",
            "quickness_score",
            "Endgame",
            "tele_dock",
            "tele_park",
            "tele_engage",
            "tele_charge_time",
            "Other",
            "intake_other",
            "intake_ground",
            "intake_low_row",
            "incap"
        )

        val FIELDS_TO_BE_DISPLAYED_TEAM_DETAILS: List<String> = listOf(
            "See Matches",
            "Notes",
            "matches_played",
            "current_rps",
            "current_rank",
            "current_avg_rps",
            "predicted_rps",
            "predicted_rank",
            "first_pickability",
            "second_pickability",
            "Auto",
            "mode_start_position",
            "position_zero_starts",
            "position_one_starts",
            "position_two_starts",
            "position_three_starts",
            "position_four_starts",
            "same_auto",
            "auto_avg_cube_low",
            "auto_max_cube_low",
            "auto_sd_cube_low",
            "auto_avg_cube_mid",
            "auto_max_cube_mid",
            "auto_sd_cube_mid",
            "auto_avg_cube_high",
            "auto_max_cube_high",
            "auto_sd_cube_high",
            "auto_avg_cube_total",
            "auto_avg_cone_low",
            "auto_max_cone_low",
            "auto_sd_cone_low",
            "auto_avg_cone_mid",
            "auto_max_cone_mid",
            "auto_sd_cone_mid",
            "auto_avg_cone_high",
            "auto_max_cone_high",
            "auto_sd_cone_high",
            "auto_avg_cone_total",
            "Tele",
            "tele_avg_cube_low",
            "tele_max_cube_low",
            "tele_sd_cube_low",
            "tele_avg_cube_mid",
            "tele_max_cube_mid",
            "tele_sd_cube_mid",
            "tele_avg_cube_high",
            "tele_max_cube_high",
            "tele_sd_cube_high",
            "tele_avg_cube_total",
            "tele_avg_cone_low",
            "tele_max_cone_low",
            "tele_sd_cone_low",
            "tele_avg_cone_mid",
            "tele_max_cone_mid",
            "tele_sd_cone_mid",
            "tele_avg_cone_high",
            "tele_max_cone_high",
            "tele_sd_cone_high",
            "tele_avg_cone_total",
            "avg_incap_time",
            "max_incap",
            "matches_incap",
            "driver_ability",
            "driver_field_awareness",
            "driver_quickness",
            "matches_played_defense",
            "Endgame",
            "tele_charge_attempts",
            "tele_dock",
            "tele_park",
            "tele_engage",
            "tele_charge_time",
            "Pit Data",
            "has_communication_device",
            "has_vision",
            "weight",
            "length",
            "width",
            "drivetrain",
            "drivetrain_motor_type",
            "drivetrain_motors"
        )

        val FIELDS_TO_BE_DISPLAYED_LFM: List<String> = listOf(
            "See Matches",
            "L4M Auto",
            "lfm_position_zero_starts",
            "lfm_position_one_starts",
            "lfm_position_two_starts",
            "lfm_position_three_starts",
            "lfm_position_four_starts",
            "lfm_auto_avg_cube_low",
            "lfm_auto_avg_cube_mid",
            "lfm_auto_avg_cube_high",
            "lfm_auto_avg_cube_total",
            "lfm_auto_avg_cone_low",
            "lfm_auto_avg_cone_mid",
            "lfm_auto_avg_cone_high",
            "lfm_auto_avg_cone_total",
            "lfm_auto_charge_attempts",
            "lfm_auto_dock",
            "lfm_auto_engage",
            "L4M Tele",
            "lfm_tele_avg_cube_low",
            "lfm_tele_avg_cube_mid",
            "lfm_tele_avg_cube_high",
            "lfm_tele_avg_cube_total",
            "lfm_tele_avg_cone_low",
            "lfm_tele_avg_cone_mid",
            "lfm_tele_avg_cone_high",
            "lfm_tele_avg_cone_total",
            "lfm_avg_incap_time",
            "lfm_max_incap",
            "lfm_matches_incap",
            "L4M Endgame",
            "lfm_tele_charge_attempts",
            "lfm_tele_dock",
            "lfm_tele_engage",
            "lfm_tele_park"
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
            "predicted_rp2",
            "win_chance"
        )

        val FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS_HEADER_PLAYED: List<String> = listOf(
            "actual_score",
            "actual_rp1",
            "actual_rp2",
            "win_chance"
        )

        val GRAPHABLE: List<String> = listOf(
            "avg_incap_time",
            "matches_incap",
            "matches_incap",
            "mode_start_position",
            "position_zero_starts",
            "position_one_starts",
            "position_two_starts",
            "position_three_starts",
            "position_four_starts",
            "matches_played_defense"
        )

        val GRAPHABLE_BOOL: List<String> = listOf(
            "matches_played_defense"
        )

        val GRAPHABLE_CLIMB_TIMES: List<String> = listOf(
        )

        val DRIVER_DATA: List<String> = listOf(
            "driver_quickness",
            "driver_field_awareness",
            "driver_ability"
        )

        val PERCENT_DATA: List<String> = listOf(
        )

        val PIT_DATA: List<String> = listOf(
            "has_communication_device",
            "has_vision",
            "weight",
            "length",
            "width",
            "drivetrain",
            "drivetrain_motor_type",
            "drivetrain_motors"
        )

        //List of rankable fields
        //The boolean is if it should be sorted descending
        //Not if it is rankable or not
        //True means descending and false means ascending
        //All items in the list are rankable no matter what
        val RANKABLE_FIELDS = mapOf<String, Boolean>(
            "current_rps" to true,
            "current_rank" to false,
            "predicted_rank" to false,
            "current_avg_rps" to true,
            "predicted_rps" to true,
            "first_pickability" to true,
            "second_pickability" to true,
            "auto_line_successes" to true, //?????
            "driver_ability" to true,
            "driver_field_awareness" to true,
            "driver_quickness" to true,
            "max_incap" to false,
            "avg_incap_time" to false,
            "matches_incap" to false,
            "lfm_max_incap" to false,
            "lfm_avg_incap_time" to false,
            "lfm_matches_incap" to false,
            "avg_intakes" to true,
            "drivetrain" to false,
            "drivetrain_motors" to false,
            "drivetrain_motor_type" to false,
            "has_vision" to false,
            "matches_played_defense" to true,
        )

        val RANK_BY_PIT = mapOf<String, Int>(
            "swerve" to 1,
            "tank" to 2,
            "mecanum" to 3,
            "minicim" to 1,
            "cim" to 2,
            "neo" to 3,
            "falcon" to 4,
            "other" to 5,
            "true" to 1,
            "false" to 2
        )

        val CATEGORY_NAMES = listOf(
            "Auto",
            "Tele",
            "Endgame",
            "Fouls",
            "Other",
            "Pit Data",
            "L4M Auto",
            "L4M Tele",
            "L4M Endgame"
        )


        //String literal translations.
        const val TEAM_NUMBER = "teamNumber"
        const val MATCH_NUMBER = "matchNumber"
        const val BLUE = "blue"
        const val RED = "red"

    }

    enum class ScheduleType {
        ALL_MATCHES,
        OUR_MATCHES,
        STARRED_MATCHES
    }


}
