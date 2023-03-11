package org.citruscircuits.viewer.constants

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
            "predicted_rp1" to "Pred. Charge RP",
            "predicted_rp2" to "Pred. Link RP",
            "predicted_score" to "Pred. Score",
            "current_rps" to "# Current RPs",
            "current_avg_rps" to "Current Avg RPs",
            "drivetrain_motor_type" to "Motor Type",
            "drivetrain_motors" to "# Drivetrain Motors",
            "drivetrain" to "Drivetrain",
            "mobility_successes" to "# Auto Lines",
            "current_rank" to "Current Rank",
            "predicted_rps" to "# Pred. RPs",
            "predicted_rank" to "Pred. Rank",
            "avg_incap_time" to "Avg Incap Time",
            "incap" to "Incap Time",
            "max_incap" to "Max Incap",
            "total_incap" to "Total Incap",
            "median_nonzero_incap" to "Median Nonzero Incap",
            "actual_score" to "Actual Score",
            "actual_rp1" to "Actual Charge RP",
            "actual_rp2" to "Actual Link RP",
            "team_number" to "Team Number",
            "has_vision" to "Has Vision",
            "match_number" to "Match Number",
            "first_pickability" to "First Pickability",
            "overall_second_pickability" to "Second Pickability",
            "offensive_second_pickability" to "Offensive 2nd Pickability",
            "defensive_second_pickability" to "Defensive 2nd Pickability",
            "matches_played" to "Matches Played",
            "matches_incap" to "# Matches Incap",
            "lfm_matches_incap" to "Matches Incap",
            "lfm_max_incap" to "Max Incap",
            "lfm_avg_incap_time" to "Avg Incap Time",
            "lfm_median_nonzero_incap" to "Median Nonzero Incap",
            "mode_start_position" to "Mode Start Position",
            "lfm_mode_start_position" to "Mode Start Position",
            "confidence_rating" to "Confidence Rating",
            "start_position" to "Start Position",
            "position_zero_starts" to "No Show",
            "position_one_starts" to "# Start Pos 1",
            "position_two_starts" to "# Start Pos 2",
            "position_three_starts" to "# Start Pos 3",
            "position_four_starts" to "# Start Pos 4",
            "quickness_score" to "Quickness Score",
            "field_awareness_score" to "Field Aware Score",
            "See Matches" to "See Matches " + "\u2192".toCharArray()[0].toString(),
            "win_chance" to "Win Chance",
            "matches_played_defense" to "Matches Played Defense",
            "foul_cc" to "Foul OPR",

            // 2023
            "auto_avg_cone_high" to "Avg Cones High",
            "auto_avg_cone_low" to "Avg Cones Low",
            "auto_avg_cone_mid" to "Avg Cones Mid",
            "auto_avg_cone_total" to "Avg Cones Total",
            "auto_avg_cube_high" to "Avg Cubes High",
            "auto_avg_cube_low" to "Avg Cubes Low",
            "auto_avg_cube_mid" to "Avg Cubes Mid",
            "auto_avg_cube_total" to "Avg Cubes Total",
            "auto_avg_gamepieces" to "Avg Game Pieces",
            "auto_avg_gamepieces_low" to "Avg All Low",
            "auto_charge_attempt" to "Auto Charge Attempts",
            "auto_charge_attempts" to "Docking/Engaging Attempts",
            "auto_charge_level" to "Auto Charge Level",
            "auto_cone_high" to "# Cones High",
            "auto_cone_low" to "# Cones Low",
            "auto_cone_mid" to "# Cones Mid",
            "auto_cube_high" to "# Cubes High",
            "auto_cube_low" to "# Cubes Low",
            "auto_cube_mid" to "# Cubes Mid",
            "auto_dock_successes" to "Docking Success %",
            "auto_engage_successes" to "Engaging Success %",
            "auto_max_cones" to "Max Cones",
            "auto_max_cubes" to "Max Cubes",
            "auto_max_gamepieces" to "Max Game Pieces",
            "auto_sd_gamepieces" to "SD Game Pieces",
            "auto_total_cones" to "Auto Total Cones",
            "auto_total_cubes" to "Auto Total Cubes",
            "auto_total_gamepieces" to "Auto Total Gamepieces",
            "avg_charge_points" to "Avg Charge Points",
            "avg_total_intakes" to "Avg Total Intakes",
            "charge_percent_success" to "Charge Success %",
            "charging_rp" to "Charging RP",
            "has_communication_device" to "Communication Device on Robot",
            "intakes_ground" to "Ground Intakes",
            "intakes_station" to "Station Intakes",
            "length" to "Length (in.)",
            "lfm_auto_avg_cone_high" to "Avg Cones High",
            "lfm_auto_avg_cone_low" to "Avg Cones Low",
            "lfm_auto_avg_cone_mid" to "Avg Cones Mid",
            "lfm_auto_avg_cone_total" to "Avg Cones Total",
            "lfm_auto_avg_cube_high" to "Avg Cubes High",
            "lfm_auto_avg_cube_low" to "Avg Cubes Low",
            "lfm_auto_avg_cube_mid" to "Avg Cubes Mid",
            "lfm_auto_avg_cube_total" to "Avg Cubes Total",
            "lfm_auto_avg_gamepieces" to "Avg Game Pieces",
            "lfm_auto_avg_gamepieces_low" to "Avg All Low",
            "lfm_auto_charge_attempts" to "Docking/Engaging Attempts",
            "lfm_auto_dock_successes" to "Docking Success %",
            "lfm_auto_engage_successes" to "Engaging Success %",
            "lfm_auto_max_cones" to "Max Cones",
            "lfm_auto_max_cubes" to "Max Cubes",
            "lfm_auto_max_gamepieces" to "Max Game Pieces",
            "lfm_auto_sd_gamepieces" to "SD Game Pieces",
            "lfm_charge_percent_success" to "Charge Success %",
            "lfm_mode_start_position" to "Mode Start Position",
            "lfm_position_four_starts" to "# Start Pos 4",
            "lfm_position_one_starts" to "# Start Pos 1",
            "lfm_position_three_starts" to "# Start Pos 3",
            "lfm_position_two_starts" to "# Start Pos 2",
            "lfm_position_zero_starts" to "No Show",
            "lfm_tele_avg_cone_high" to "Avg Cones High",
            "lfm_tele_avg_cone_low" to "Avg Cones Low",
            "lfm_tele_avg_cone_mid" to "Avg Cones Mid",
            "lfm_tele_avg_cone_total" to "Avg Cones Total",
            "lfm_tele_avg_cube_high" to "Avg Cubes High",
            "lfm_tele_avg_cube_low" to "Avg Cubes Low",
            "lfm_tele_avg_cube_mid" to "Avg Cubes Mid",
            "lfm_tele_avg_cube_total" to "Avg Cubes Total",
            "lfm_tele_avg_gamepieces" to "Avg Game Pieces",
            "lfm_tele_avg_gamepieces_low" to "Avg All Low",
            "lfm_tele_charge_attempts" to "Docking/Engaging Attempts",
            "lfm_tele_dock_successes" to "Docking Success %",
            "lfm_tele_engage_successes" to "Engaging Success %",
            "lfm_tele_max_cones" to "Max Cones",
            "lfm_tele_max_cubes" to "Max Cubes",
            "lfm_tele_max_gamepieces" to "Max Game Pieces",
            "lfm_tele_park" to "Parking",
            "lfm_tele_sd_gamepieces" to "SD Game Pieces",
            "mobility" to "Mobility",
            "mobility_successes" to "# Mobility",
            "preloaded_gamepiece" to "Preloaded Piece",
            "sustainability_rp" to "Sustainability RP",
            "tele_avg_cone_high" to "Avg Cones High",
            "tele_avg_cone_low" to "Avg Cones Low",
            "tele_avg_cone_mid" to "Avg Cones Mid",
            "tele_avg_cone_total" to "Avg Cones Total",
            "tele_avg_cube_high" to "Avg Cubes High",
            "tele_avg_cube_low" to "Avg Cubes Low",
            "tele_avg_cube_mid" to "Avg Cubes Mid",
            "tele_avg_cube_total" to "Avg Cubes Total",
            "tele_avg_gamepieces" to "Avg Game Pieces",
            "tele_avg_gamepieces_low" to "Avg All Low",
            "tele_charge_attempts" to "Docking/Engaging Attempts",
            "tele_charge_level" to "Tele Charge Level",
            "tele_charge_attempt" to "Tele Charge Attempts",
            "tele_cone_high" to "# Cones High",
            "tele_cone_low" to "# Cones Low",
            "tele_cone_mid" to "# Cones Mid",
            "tele_cube_high" to "# Cubes High",
            "tele_cube_low" to "# Cubes Low",
            "tele_cube_mid" to "# Cubes Mid",
            "tele_dock_successes" to "Docking Success %",
            "tele_engage_successes" to "Engaging Success %",
            "tele_max_cones" to "Max Cones",
            "tele_max_cubes" to "Max Cubes",
            "tele_max_gamepieces" to "Max Game Pieces",
            "tele_park" to "Parking",
            "tele_sd_gamepieces" to "SD Game Pieces",
            "tele_total_cones" to "# Cones Total",
            "tele_total_cubes" to "# Cubes Total",
            "tele_total_gamepieces" to "Tele Total Gamepieces",
            "tele_total_gamepieces_low" to "# Gamepieces Low Total",
            "weight" to "Weight (lbs.)",
            "width" to "Width (in.)",
            "tele_park_successes" to "Park Successes",
            "lfm_tele_park_successes" to "Park Successes",
            "matches_scored_coop" to "Matches Scored Coop",
            "tele_avg_charge_points" to "Avg Charge Points",
            "auto_avg_charge_points" to "Avg Charge Points",
            "played_defense" to "Matches Played Defense",
            "lfm_avg_total_intakes" to "Avg Total Intakes",

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