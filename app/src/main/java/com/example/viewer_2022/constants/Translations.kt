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
//            "predicted_rp1" to "Pred. cargo RP", Add back when we know which rp is which
//            "predicted_rp2" to "Pred. climb RP",
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
//            "actual_rp1" to "Actual Cargo RP", Add back when we know which rp is which
//            "actual_rp2" to "Actual Climb RP",
            "team_number" to "Team Number",
            "has_vision" to "Has vision",
            "can_cheesecake" to "Cheesecake",
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
            "played_defense" to "Played Defense",
            "quickness_score" to "Quickness Score",
            "field_awareness_score" to "Field Aware Score",
            "matches_played_defense" to "Matches Played D",
            "See Matches" to "See Matches " + "\u2192".toCharArray()[0].toString(),
            "win_chance" to "Win Chance"
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