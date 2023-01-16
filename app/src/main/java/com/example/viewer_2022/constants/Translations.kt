package com.example.viewer_2022.constants

/**
 * Class that contains translations from datapoint names to human-readable names.
 */
class Translations {
    companion object {
        val ACTUAL_TO_HUMAN_READABLE: Map<String, String> = mapOf(
            "team_name" to "Team Name",
            "driver_quickness" to "Driver Quickness",
            "driver_field_awareness" to "Driver Field Awareness",
            "driver_ability" to "Driver Ability",
            "predicted_rp1" to "Pred. Link RP",
            "predicted_rp2" to "Pred. Charge RP",
            "predicted_score" to "Pred. Score",
            "current_rps" to "# Current RPs",
            "current_avg_rps" to "Current Avg RPs",
            "drivetrain_motor_type" to "Motor Type",
            "drivetrain_motors" to "# Drivetrain Motors",
            "drivetrain" to "Drivetrain",
            "auto_line_successes" to "# Auto Lines",
            "current_rank" to "Current Rank",
            "predicted_rps" to "# Pred. RPs",
            "predicted_rank" to "Pred. Rank",
            "avg_incap_time" to "Avg Incap Time",
            "incap" to "Incap Time",
            "max_incap" to "Max Incap",
            "actual_score" to "Actual Score",
            "actual_rp1" to "Actual Link RP",
            "actual_rp2" to "Actual Charge RP",
            "team_number" to "Team Number",
            "has_vision" to "Has Vision",
            "match_number" to "Match Number",
            "first_pickability" to "First Pickability",
            "second_pickability" to "Second Pickability",
            "matches_played" to "Matches Played",
            "matches_incap" to "# Matches Incap",
            "lfm_matches_incap" to "Matches Incap",
            "lfm_max_incap" to "Max Incap",
            "lfm_avg_incap_time" to "Incap Avg Time",
            "mode_start_position" to "Mode Start Position",
            "lfm_mode_start_position" to "Mode Start Position",
            "confidence_rating" to "Confidence Rating",
            "start_position" to "Start Position",
            "position_zero_starts" to "No Show",
            "position_one_starts" to "# Start Pos 1",
            "position_two_starts" to "# Start Pos 2",
            "position_three_starts" to "# Start Pos 3",
            "position_four_starts" to "# Start Pos 4",
            "played_defense" to "Played Defense",
            "quickness_score" to "Quickness Score",
            "field_awareness_score" to "Field Aware Score",
            "matches_played_defense" to "Matches Played D",
            "See Matches" to "See Matches " + "\u2192".toCharArray()[0].toString(),
            "win_chance" to "Win Chance",

            // 2023
            "auto_cube_low" to "# Cubes Low",
            "auto_avg_cube_low" to "Avg Cubes Low",
            "auto_max_cube_low" to "Max Cubes Low",
            "auto_sd_cube_low" to "SD Cubes Low",
            "auto_cube_mid" to "# Cubes Mid",
            "auto_avg_cube_mid" to "Avg Cubes Mid",
            "auto_max_cube_mid" to "Max Cubes Mid",
            "auto_sd_cube_mid" to "SD Cubes Mid",
            "auto_cube_high" to "# Cubes High",
            "auto_avg_cube_high" to "Avg Cubes High",
            "auto_max_cube_high" to "Max Cubes High",
            "auto_sd_cube_high" to "SD Cubes High",
            "auto_avg_cube_total" to "Avg Cubes Total",
            "auto_cone_low" to "# Cones Low",
            "auto_avg_cone_low" to "Avg Cones Low",
            "auto_max_cone_low" to "Max Cones Low",
            "auto_sd_cone_low" to "SD Cones Low",
            "auto_cone_mid" to "# Cones Mid",
            "auto_avg_cone_mid" to "Avg Cones Mid",
            "auto_max_cone_mid" to "Max Cones Mid",
            "auto_sd_cone_mid" to "SD Cones Mid",
            "auto_cone_high" to "# Cones High",
            "auto_avg_cone_high" to "Avg Cones High",
            "auto_max_cone_high" to "Max Cones High",
            "auto_sd_cone_high" to "SD Cones High",
            "auto_avg_cone_total" to "Avg Cones Total",
            "auto_dock" to "Docking Auto",
            "auto_engage" to "Engaging Auto",
            "auto_charge_attempt" to "Docking/Engaging Attempt",
            "same_auto" to "Same Auto Red/Blue",
            "mobility" to "Mobility",
            "tele_cube_low" to "# Cubes Low",
            "tele_avg_cube_low" to "Avg Cubes Low",
            "tele_max_cube_low" to "Max Cubes Low",
            "tele_sd_cube_low" to "SD Cubes Low",
            "tele_cube_mid" to "# Cubes Mid",
            "tele_avg_cube_mid" to "Avg Cubes Mid",
            "tele_max_cube_mid" to "Max Cubes Mid",
            "tele_sd_cube_mid" to "SD Cubes Mid",
            "tele_cube_high" to "# Cubes High",
            "tele_avg_cube_high" to "Avg Cubes High",
            "tele_max_cube_high" to "Max Cubes High",
            "tele_sd_cube_high" to "SD Cubes High",
            "tele_avg_cube_total" to "Avg Cubes Total",
            "tele_cone_low" to "# Cones Low",
            "tele_avg_cone_low" to "Avg Cones Low",
            "tele_max_cone_low" to "Max Cones Low",
            "tele_sd_cone_low" to "SD Cones Low",
            "tele_cone_mid" to "# Cones Mid",
            "tele_avg_cone_mid" to "Avg Cones Mid",
            "tele_max_cone_mid" to "Max Cones Mid",
            "tele_sd_cone_mid" to "SD Cones Mid",
            "tele_cone_high" to "# Cones High",
            "tele_avg_cone_high" to "Avg Cones High",
            "tele_max_cone_high" to "Max Cones High",
            "tele_sd_cone_high" to "SD Cones High",
            "tele_avg_cone_total" to "Avg Cones Total",
            "sustainability_rp" to "Sustainability RP",
            "charging_rp" to "Charging RP",
            "intake_other" to "All Other Intakes",
            "intake_ground" to "Ground Intake",
            "intake_low_row" to "Intake on Low Row",
            "tele_charge_attempts" to "Docking/Engaging Attempts",
            "tele_dock" to "Docking Teleop",
            "tele_park" to "Parking",
            "tele_engage" to "Engaging Teleop",
            "tele_charge_time" to "Time to Engage/Dock",
            "has_communication_device" to "Communication Device on Robot",
            "lfm_position_zero_starts" to "No Show",
            "lfm_position_one_starts" to "# Start Pos 1",
            "lfm_position_two_starts" to "# Start Pos 2",
            "lfm_position_three_starts" to "# Start Pos 3",
            "lfm_position_four_starts" to "# Start Pos 4",
            "lfm_auto_avg_cube_low" to "Avg Cubes Low",
            "lfm_auto_avg_cube_mid" to "Avg Cubes Mid",
            "lfm_auto_avg_cube_high" to "Avg Cubes High",
            "lfm_auto_avg_cube_total" to "Avg Cubes Total",
            "lfm_auto_avg_cone_low" to "Avg Cones Low",
            "lfm_auto_avg_cone_mid" to "Avg Cones Mid",
            "lfm_auto_avg_cone_high" to "Avg Cones High",
            "lfm_auto_avg_cone_total" to "Avg Cones Total",
            "lfm_auto_charge_attempts" to "Docking/Engaging Attempts",
            "lfm_auto_dock" to "Docking Auto",
            "lfm_auto_engage" to "Engaging Auto",
            "lfm_tele_avg_cube_low" to "Avg Cubes Low",
            "lfm_tele_avg_cube_mid" to "Avg Cubes Mid",
            "lfm_tele_avg_cube_high" to "Avg Cubes High",
            "lfm_tele_avg_cube_total" to "Avg Cubes Total",
            "lfm_tele_avg_cone_low" to "Avg Cones Low",
            "lfm_tele_avg_cone_mid" to "Avg Cones Mid",
            "lfm_tele_avg_cone_high" to "Avg Cones High",
            "lfm_tele_avg_cone_total" to "Avg Cones Total",
            "lfm_tele_charge_attempts" to "Docking/Engaging Attempts",
            "lfm_tele_dock" to "Docking Teleop",
            "lfm_tele_engage" to "Engaging Teleop",
            "lfm_tele_park" to "Parking",
            "weight" to "Weight",
            "length" to "Length",
            "width" to "Width"
        )

        val DRIVETRAIN: Map<String, String> = mapOf(
            "0" to "tank",
            "1" to "mecanum",
            "2" to "swerve",
            "3" to "other"
        )

        val DRIVETRAIN_MOTOR_TYPE: Map<String, String> = mapOf(
            "0" to "minicim",
            "1" to "cim",
            "2" to "neo",
            "3" to "falcon",
            "4" to "other"
        )

        val TIM_FROM_TEAM: Map<String, String> = mapOf(
            "avg_incap_time" to "incap",
            "matches_incap" to "incap",
            "mode_start_position" to "start_position",
            "position_zero_starts" to "start_position",
            "position_one_starts" to "start_position",
            "position_two_starts" to "start_position",
            "position_three_starts" to "start_position",
            "position_four_starts" to "start_position",
            "matches_played_defense" to "played_defense"
        )

        val TIM_TO_HUMAN_READABLE: Map<String, String> = mapOf(
            "avg_incap_time" to "Time Incap",
            "matches_incap" to "Matches Incap",
            "start_position" to "Start Pos",
            "position_zero_starts" to "No Show?",
            "position_one_starts" to "Start Pos 1?",
            "position_two_starts" to "Start Pos 2?",
            "position_three_starts" to "Start Pos 3?",
            "position_four_starts" to "Start Pos 4?",
            "mode_start_position" to "Start Position",
            "matches_played_defense" to "Played Def?"
        )
    }
}