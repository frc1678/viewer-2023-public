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
            "predicted_rp1" to "Pred. link RP",
            "predicted_rp2" to "Pred. charge RP",
            "predicted_score" to "Pred. score",
            "current_rps" to "# Current RPs",
            "current_avg_rps" to "Current Avg RPs",
            "drivetrain_motor_type" to "Motor Type",
            "drivetrain_motors" to "# Drivetrain Motors",
            "drivetrain" to "Drivetrain",
            "has_ground_intake" to "Ground intake",
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
            "has_vision" to "Has vision",
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
            "auto_cube_low" to "Score Cubes in Low",
            "auto_cube_mid" to "Score Cubes in Mid",
            "auto_cube_high" to "Score Cubes in High",
            "auto_cone_low" to "Score Cones in Low",
            "auto_cone_mid" to "Score Cones in Mid",
            "auto_cone_high" to "Score Cones High",
            "auto_dock" to "Docking Auto",
            "auto_engage" to "Engaging Auto",
            "auto_charge_attempt" to "Charge Attempt",
            "same_auto" to "Same auto for red alliance and blue alliance",
            "mobility" to "Mobility",
            "tele_cube_low" to "Score Cubes in Low",
            "tele_cube_mid" to "Score Cubes in Mid",
            "tele_cube_high" to "Score Cubes in High",
            "tele_cone_low" to "Score Cones in Low",
            "tele_cone_mid" to "Score Cones in Mid",
            "tele_cone_high" to "Score Cones in High",
            "sustainability_rp" to "Sustainability RP",
            "charging_rp" to "Charging RP",
            "intake_other" to "All Other Intakes",
            "inake_ground" to "Ground Intake",
            "intake_low_row" to "Intake on Low Row",
            "tele_charge_attempt" to "Docking/Engaging attempts",
            "tele_dock" to "Docking Teleop",
            "tele_park" to "Parking in Community",
            "tele_engage" to "Engaging Teleop",
            "tele_charge_time" to "Time that it takes for them to engage/dock",
            "has_communication_device" to "Communication device on robot",
            "weight" to "Weight",
            "dimensions" to "Dimensions",
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