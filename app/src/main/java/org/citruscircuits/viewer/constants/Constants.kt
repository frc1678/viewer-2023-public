/*
* Constants.kt
* viewer
*
* Created on 1/26/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package org.citruscircuits.viewer.constants

import java.io.File

//Class that contains a collection of Constant values, or final values that never change
class Constants {
    companion object {
        //Game specific data.
        const val EVENT_KEY = "2023caph"
        const val SCHEDULE_KEY = "2023caph"
        const val MY_TEAM_NUMBER = "1678"
        const val USE_TEST_DATA = false
        const val REFRESH_INTERVAL = 120

        //Util.
        const val NULL_PREDICTED_SCORE_CHARACTER = "-"
        const val NULL_CHARACTER = "?"
        const val EMPTY_CHARACTER = ""
        const val PREDICTED_RANKING_POINT_QUALIFICATION = 0.65
        const val VERSION_NUM = "5.0.0"

//        lateinit var STORAGE_FOLDER: File
        lateinit var DOWNLOADS_FOLDER: File

        val FIELDS_TO_BE_DISPLAYED: List<String> = listOf(
            "processed",
            "raw"
        )

        val FIELDS_TO_BE_DISPLAYED_MATCH_DETAILS_PLAYED: List<String> = listOf(
            "current_avg_rps",
            "Auto",
            "start_position",
            "preloaded_gamepiece",
            "auto_cube_low",
            "auto_cube_mid",
            "auto_cube_high",
            "auto_cone_low",
            "auto_cone_mid",
            "auto_cone_high",
            "mobility",
            "auto_charge_level",
            "Tele",
            "tele_cube_low",
            "tele_cube_mid",
            "tele_cube_high",
            "tele_cone_low",
            "tele_cone_mid",
            "tele_cone_high",
            "driver_ability",
            "Endgame",
            "tele_charge_level",
            "Other",
            "intakes_station",
            "intakes_ground",
            "incap"
        )

        val ACTUAL_TO_PREDICTED_MATCH_DETAILS = mapOf(
            "start_position" to "mode_start_position",
            "preloaded_gamepiece" to "mode_preloaded_gamepiece",
            "auto_cube_low" to "auto_avg_cube_low",
            "auto_cube_mid" to "auto_avg_cube_mid",
            "auto_cube_high" to "auto_avg_cube_high",
            "auto_cone_low" to "auto_avg_cone_low",
            "auto_cone_mid" to "auto_avg_cone_mid",
            "auto_cone_high" to "auto_avg_cone_high",
            "mobility" to "mobility_successes",
            "auto_charge_level" to "auto_avg_charge_points",
            "tele_cube_low" to "tele_avg_cube_low",
            "tele_cube_mid" to "tele_avg_cube_mid",
            "tele_cube_high" to "tele_avg_cube_high",
            "tele_cone_low" to "tele_avg_cone_low",
            "tele_cone_mid" to "tele_avg_cone_mid",
            "tele_cone_high" to "tele_avg_cone_high",
            "tele_charge_level" to "tele_avg_charge_points",
            "intakes_station" to "avg_intakes_station",
            "intakes_ground" to "avg_intakes_ground",
            "incap" to "avg_incap_time"
        )

        val FIELDS_TO_BE_DISPLAYED_TEAM_DETAILS: List<String> = listOf(
            "See Matches",
            "Notes Label",
            "Notes",
            "matches_played",
            "current_rps",
            "predicted_rps",
            "current_avg_rps",
            "current_rank",
            "predicted_rank",
            "first_pickability",
            "overall_second_pickability",
            "offensive_second_pickability",
            "defensive_second_pickability",
            "Auto",
            "mode_start_position",
            "position_zero_starts",
            "position_one_starts",
            "position_two_starts",
            "position_three_starts",
            "position_four_starts",
            "auto_avg_gamepieces_low",
            "auto_avg_cube_low",
            "auto_avg_cube_mid",
            "auto_avg_cube_high",
            "auto_avg_cube_total",
            "auto_max_cubes",
            "auto_avg_cone_low",
            "auto_avg_cone_mid",
            "auto_avg_cone_high",
            "auto_avg_cone_total",
            "auto_max_cones",
            "auto_avg_gamepieces",
            "auto_sd_gamepieces",
            "auto_max_gamepieces",
            "mobility_successes",
            "auto_charge_attempts",
            "auto_dock_percent_success",
            "auto_engage_percent_success",
            "auto_avg_charge_points",
            "Tele",
            "tele_avg_gamepieces_low",
            "tele_avg_cube_low",
            "tele_avg_cube_mid",
            "tele_avg_cube_high",
            "tele_avg_cube_total",
            "tele_max_cubes",
            "tele_avg_cone_low",
            "tele_avg_cone_mid",
            "tele_avg_cone_high",
            "tele_avg_cone_total",
            "tele_max_cones",
            "matches_scored_coop",
            "tele_avg_gamepieces",
            "tele_sd_gamepieces",
            "tele_max_gamepieces",
            "avg_total_intakes",
            "avg_incap_time",
            "max_incap",
            "total_incap",
            "matches_incap",
            "median_nonzero_incap",
            "matches_tippy",
            "matches_played_defense",
            "driver_ability",
            "driver_field_awareness",
            "driver_quickness",
            "foul_cc",
            "Endgame",
            "tele_park_successes",
            "tele_charge_attempts",
            "tele_dock_percent_success",
            "tele_engage_percent_success",
            "charge_percent_success",
            "tele_avg_charge_points",
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
            "Notes Label",
            "Notes",
            "matches_played",
            "current_rps",
            "predicted_rps",
            "current_avg_rps",
            "current_rank",
            "predicted_rank",
            "first_pickability",
            "overall_second_pickability",
            "offensive_second_pickability",
            "defensive_second_pickability",
            "L4M Auto",
            "lfm_mode_start_position",
            "lfm_position_zero_starts",
            "lfm_position_one_starts",
            "lfm_position_two_starts",
            "lfm_position_three_starts",
            "lfm_position_four_starts",
            "lfm_auto_avg_gamepieces_low",
            "lfm_auto_avg_cube_low",
            "lfm_auto_avg_cube_mid",
            "lfm_auto_avg_cube_high",
            "lfm_auto_avg_cube_total",
            "lfm_auto_max_cubes",
            "lfm_auto_avg_cone_low",
            "lfm_auto_avg_cone_mid",
            "lfm_auto_avg_cone_high",
            "lfm_auto_avg_cone_total",
            "lfm_auto_max_cones",
            "lfm_auto_avg_gamepieces",
            "lfm_auto_sd_gamepieces",
            "lfm_auto_max_gamepieces",
            "lfm_mobility_successes",
            "lfm_auto_charge_attempts",
            "lfm_auto_dock_percent_success",
            "lfm_auto_engage_percent_success",
            "lfm_auto_avg_charge_points",
            "L4M Tele",
            "lfm_tele_avg_gamepieces_low",
            "lfm_tele_avg_cube_low",
            "lfm_tele_avg_cube_mid",
            "lfm_tele_avg_cube_high",
            "lfm_tele_avg_cube_total",
            "lfm_tele_max_cubes",
            "lfm_tele_avg_cone_low",
            "lfm_tele_avg_cone_mid",
            "lfm_tele_avg_cone_high",
            "lfm_tele_avg_cone_total",
            "lfm_tele_max_cones",
            "lfm_matches_scored_coop",
            "lfm_tele_avg_gamepieces",
            "lfm_tele_sd_gamepieces",
            "lfm_tele_max_gamepieces",
            "lfm_avg_total_intakes",
            "lfm_avg_incap_time",
            "lfm_max_incap",
            "lfm_total_incap",
            "lfm_matches_incap",
            "lfm_median_nonzero_incap",
            "lfm_matches_tippy",
            "lfm_matches_played_defense",
            "driver_ability",
            "driver_field_awareness",
            "driver_quickness",
            "lfm_foul_cc",
            "L4M Endgame",
            "lfm_tele_park_successes",
            "lfm_tele_charge_attempts",
            "lfm_tele_dock_percent_success",
            "lfm_tele_engage_percent_success",
            "lfm_charge_percent_success",
            "lfm_tele_avg_charge_points",
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

        val GRAPHABLE = mapOf(
            "auto_avg_cube_low" to "auto_cube_low",
            "auto_avg_cube_mid" to "auto_cube_mid",
            "auto_avg_cube_high" to "auto_cube_high",
            "auto_avg_cube_total" to "auto_total_cubes",
            "auto_max_cubes" to "auto_total_cubes",
            "auto_avg_cone_low" to "auto_cone_low",
            "auto_avg_cone_mid" to "auto_cone_mid",
            "auto_avg_cone_high" to "auto_cone_high",
            "auto_max_cones" to "auto_total_cones",
            "auto_avg_cone_total" to "auto_total_cones",
            "tele_avg_cube_low" to "tele_cube_low",
            "tele_avg_cube_mid" to "tele_cube_mid",
            "tele_avg_cube_high" to "tele_cube_high",
            "tele_avg_cube_total" to "tele_total_cubes",
            "tele_max_cubes" to "tele_total_cubes",
            "tele_avg_cone_low" to "tele_cone_low",
            "tele_avg_cone_mid" to "tele_cone_mid",
            "tele_avg_cone_high" to "tele_cone_high",
            "tele_avg_cone_total" to "tele_total_cones",
            "tele_max_cones" to "tele_total_cones",
            "avg_intakes_ground" to "intakes_ground",
            "avg_intakes_station" to "intakes_station",
            "matches_incap" to "incap",
            "max_incap" to "incap",
            "median_nonzero_incap" to "incap",
            "mode_start_position" to "start_position",
            "auto_max_gamepieces" to "auto_total_gamepieces",
            "auto_avg_gamepieces" to "auto_total_gamepieces",
            "tele_max_gamepieces" to "tele_total_gamepieces",
            "tele_avg_gamepieces" to "tele_total_gamepieces",
            "tele_avg_gamepieces_low" to "tele_total_gamepieces_low",
            "matches_tippy" to "tippy",
            "matches_played_defense" to "played_defense",
            "auto_dock_percent_success" to "auto_charge_level",
            "tele_dock_percent_success" to "tele_charge_level",
            "auto_engage_percent_success" to "auto_charge_level",
            "tele_engage_percent_success" to "tele_charge_level",
            "auto_charge_attempts" to "auto_charge_attempt",
            "tele_charge_attempts" to "tele_charge_attempt",

//            "lfm_auto_avg_cube_low" to "lfm_auto_cube_low",
//            "lfm_auto_avg_cube_mid" to "lfm_auto_cube_mid",
//            "lfm_auto_avg_cube_high" to "lfm_auto_cube_high",
//            "lfm_auto_avg_cube_total" to "lfm_auto_total_cubes",
//            "lfm_auto_max_cubes" to "lfm_auto_total_cubes",
//            "lfm_auto_avg_cone_low" to "lfm_auto_cone_low",
//            "lfm_auto_avg_cone_mid" to "lfm_auto_cone_mid",
//            "lfm_auto_avg_cone_high" to "lfm_auto_cone_high",
//            "lfm_auto_max_cones" to "lfm_auto_total_cones",
//            "lfm_auto_avg_cone_total" to "lfm_auto_total_cones",
//            "lfm_tele_avg_cube_low" to "lfm_tele_cube_low",
//            "lfm_tele_avg_cube_mid" to "lfm_tele_cube_mid",
//            "lfm_tele_avg_cube_high" to "lfm_tele_cube_high",
//            "lfm_tele_avg_cube_total" to "lfm_tele_total_cubes",
//            "lfm_tele_max_cubes" to "lfm_tele_total_cubes",
//            "lfm_tele_avg_cone_low" to "lfm_tele_cone_low",
//            "lfm_tele_avg_cone_mid" to "lfm_tele_cone_mid",
//            "lfm_tele_avg_cone_high" to "lfm_tele_cone_high",
//            "lfm_tele_avg_cone_total" to "lfm_tele_total_cones",
//            "lfm_tele_max_cones" to "lfm_tele_total_cones",
//            "lfm_avg_intakes_ground" to "lfm_intakes_ground",
//            "lfm_avg_intakes_station" to "lfm_intakes_station",
//            "lfm_matches_incap" to "lfm_incap",
//            "lfm_max_incap" to "lfm_incap",
//            "lfm_median_nonzero_incap" to "lfm_incap",
//            "lfm_mode_start_position" to "lfm_start_position",
//            "lfm_auto_max_gamepieces" to "lfm_auto_total_gamepieces",
//            "lfm_auto_avg_gamepieces" to "lfm_auto_total_gamepieces",
//            "lfm_tele_max_gamepieces" to "lfm_tele_total_gamepieces",
//            "lfm_tele_avg_gamepieces" to "lfm_tele_total_gamepieces",
//            "lfm_tele_avg_gamepieces_low" to "lfm_tele_total_gamepieces_low",
//            "lfm_matches_tippy" to "lfm_tippy",
//            "lfm_matches_played_defense" to "lfm_played_defense",
//            "lfm_auto_dock_percent_success" to "lfm_auto_charge_level",
//            "lfm_tele_dock_percent_success" to "lfm_tele_charge_level",
//            "lfm_auto_engage_percent_success" to "lfm_auto_charge_level",
//            "lfm_tele_engage_percent_success" to "lfm_tele_charge_level",
//            "lfm_auto_charge_attempts" to "lfm_auto_charge_attempt",
//            "lfm_tele_charge_attempts" to "lfm_tele_charge_attempt"


        //[auto + tele ] avg low
        //translation for avg cones mid, high total
        //max + avg cycles
        //



        )

        val CHARGE_LEVELS = listOf(
            "N",
            "F",
            "P",
            "D",
            "E"
        )

        val DRIVER_DATA: List<String> = listOf(
            "driver_quickness",
            "driver_field_awareness",
            "driver_ability"
        )

        val PERCENT_DATA: List<String> = listOf(
            "charge_percent_success",
            "lfm_charge_percent_success",
            "auto_dock_percent_success",
            "auto_engage_percent_success",
            "tele_dock_percent_success",
            "tele_engage_percent_success",
            "lfm_auto_dock_percent_success",
            "lfm_auto_engage_percent_success",
            "lfm_tele_dock_percent_success",
            "lfm_tele_engage_percent_success",
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
            "overall_second_pickability" to true,
            "offensive_second_pickability" to true,
            "defensive_second_pickability" to true,
            "driver_ability" to true,
            "driver_field_awareness" to true,
            "driver_quickness" to true,
            "max_incap" to false,
            "total_incap" to false,
            "avg_incap_time" to false,
            "matches_incap" to false,
            "median_nonzero_incap" to false,
            "lfm_max_incap" to false,
            "lfm_avg_incap_time" to false,
            "lfm_matches_incap" to false,
            "avg_total_intakes" to true,
            "drivetrain" to false,
            "drivetrain_motors" to true,
            "drivetrain_motor_type" to false,
            "has_vision" to false,
            "matches_played_defense" to true,
            "auto_avg_gamepieces_low" to true,
            "auto_avg_cube_low" to true,
            "auto_avg_cube_mid" to true,
            "auto_avg_cube_high" to true,
            "auto_avg_cube_total" to true,
            "auto_avg_cone_low" to true,
            "auto_avg_cone_mid" to true,
            "auto_avg_cone_high" to true,
            "auto_avg_cone_total" to true,
            "auto_max_gamepieces" to true,
            "auto_avg_gamepieces" to true,
            "auto_sd_gamepieces" to true,
            "tele_avg_gamepieces_low" to true,
            "tele_avg_cube_low" to true,
            "tele_avg_cube_mid" to true,
            "tele_avg_cube_high" to true,
            "tele_avg_cube_total" to true,
            "tele_avg_cone_low" to true,
            "tele_avg_cone_mid" to true,
            "tele_avg_cone_high" to true,
            "tele_avg_cone_total" to true,
            "lfm_tele_avg_gamepieces_low" to true,
            "lfm_tele_avg_cube_low" to true,
            "lfm_tele_avg_cube_mid" to true,
            "lfm_tele_avg_cube_high" to true,
            "lfm_tele_avg_cube_total" to true,
            "lfm_tele_avg_cone_low" to true,
            "lfm_tele_avg_cone_mid" to true,
            "lfm_tele_avg_cone_high" to true,
            "lfm_tele_avg_cone_total" to true,
            "lfm_auto_avg_gamepieces_low" to true,
            "lfm_auto_avg_cube_low" to true,
            "lfm_auto_avg_cube_mid" to true,
            "lfm_auto_avg_cube_high" to true,
            "lfm_auto_avg_cube_total" to true,
            "lfm_auto_avg_cone_low" to true,
            "lfm_auto_avg_cone_mid" to true,
            "lfm_auto_avg_cone_high" to true,
            "lfm_auto_avg_cone_total" to true,
            "lfm_auto_charge_attempts" to true,
            "lfm_tele_charge_attempts" to true,
            "tele_charge_attempts" to true,
            "auto_charge_attempts" to true,
            "lfm_auto_charge_attempts" to true,
            "auto_dock_percent_success" to true,
            "auto_engage_percent_success" to true,
            "tele_dock_percent_success" to true,
            "tele_engage_percent_success" to true,
            "tele_park" to true,
            "avg_charge_points" to true,
            "mobility_successes" to true,
            "lfm_tele_dock_percent_success" to true,
            "lfm_tele_engage_percent_success" to true,
            "lfm_tele_park" to true,
            "lfm_auto_dock_percent_success" to true,
            "lfm_auto_engage_percent_success" to true,
            "charge_percent_success" to true,
            "lfm_charge_percent_success" to true,
            "lfm_tele_max_cubes" to true,
            "lfm_tele_max_cones" to true,
            "tele_max_cubes" to true,
            "tele_max_cones" to true,
            "lfm_auto_max_cubes" to true,
            "lfm_auto_max_cones" to true,
            "auto_max_cubes" to true,
            "auto_max_cones" to true,
            "tele_max_gamepieces" to true,
            "tele_avg_gamepieces" to true,
            "tele_sd_gamepieces" to true,
            "lfm_tele_max_gamepieces" to true,
            "lfm_tele_avg_gamepieces" to true,
            "lfm_auto_max_gamepieces" to true,
            "lfm_auto_avg_gamepieces" to true,
            "matches_scored_coop" to true,
            "lfm_tele_park_successes" to true,
            "tele_park_successes" to true,
            "tele_avg_charge_points" to true,
            "auto_avg_charge_points" to true,
            "lfm_avg_incap_time" to false,
            "lfm_median_nonzero_incap" to false,
            "matches_played_defense" to true,
            "foul_cc" to false,
            "width" to true,
            "length" to true,
            "weight" to true,
            "has_communication_device" to false,
            "lfm_avg_total_intakes" to true,
            "lfm_foul_cc" to false,
            "lfm_auto_sd_gamepieces" to true,
            "lfm_mobility_successes" to true,
            "lfm_auto_avg_charge_points" to true,
            "lfm_matches_scored_coop" to true,
            "lfm_tele_sd_gamepieces" to true,
            "lfm_total_incap" to false,
            "lfm_matches_played_defense" to true,
            "lfm_tele_avg_charge_points" to true,
            "matches_tippy" to false,
            "lfm_matches_tippy" to false
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
            "L4M Endgame",
            "See Matches",
            "Notes",
            "Notes Label"
        )

        val TEAM_AND_LFM_SHARED_DATAPOINTS = listOf(
            "matches_played",
            "current_rps",
            "predicted_rps",
            "current_avg_rps",
            "current_rank",
            "predicted_rank",
            "first_pickability",
            "overall_second_pickability",
            "offensive_second_pickability",
            "defensive_second_pickability",
            "driver_ability",
            "driver_field_awareness",
            "driver_quickness",
            "has_communication_device",
            "has_vision",
            "weight",
            "length",
            "width",
            "drivetrain",
            "drivetrain_motor_type",
            "drivetrain_motors"
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
