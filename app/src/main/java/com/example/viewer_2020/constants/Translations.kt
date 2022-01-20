package com.example.viewer_2020.constants

class Translations {
    companion object {
        val ACTUAL_TO_HUMAN_READABLE: Map<String, String> = mapOf(
            "team_name" to "Team Name",
            "driver_quickness" to "Driver Quickness",
            "driver_near_field_awareness" to "Driver Near Awareness",
            "driver_far_field_awareness" to "Driver Far Awareness",
            "driver_ability" to "Driver Ability",
            "auto_avg_balls_low" to "Avg Balls Low",
            "auto_avg_balls_high" to "Avg Balls High",
            "tele_avg_balls_low" to "Avg Balls Low",
            "tele_avg_balls_high" to "Avg Balls High",
            "climb_all_attempts" to "Climb Attempts #",
            "predicted_rp1" to "Pred. cargo RP",
            "predicted_rp2" to "Pred. climb RP",
            "predicted_score" to "Pred. score",
            "current_rps" to "# Current RPs",
            "current_avg_rps" to "Current Avg RPs",
            "climb_percent_success" to "Climb Success %",
            "drivetrain_motor_type" to "Motor Type",
            "drivetrain_motors" to "# Drivetrain Motors",
            "drivetrain" to "Drivetrain",
            "has_ground_intake" to "Ground intake",
            "auto_line_successes" to "# Auto Lines",
            "current_rank" to "Current Rank",
            "predicted_rps" to "# Pred. RPs",
            "predicted_rank" to "Pred. Rank",
            "auto_max_balls_low" to "# Max Balls Low",
            "auto_max_balls_high" to "# Max Balls High",
            "tele_max_balls_low" to "# Tele Max Balls Low",
            "tele_max_balls_high" to "# Tele Max Balls High",
            "avg_incap_time" to "Avg Incap Time",
            "auto_balls_low" to "# Balls Low",
            "auto_balls_high" to "# Balls High",
            "tele_balls_low" to "# Balls Low",
            "tele_balls_high" to "# Balls High",
            "incap" to "Incap (s)",
            "climb_time" to "Climb (s)",
            "max_incap" to "Max Incap",
            "actual_score" to "Actual Score",
            "actual_rp1" to "Actual Cargo RP",
            "actual_rp2" to "Actual Climb RP",
            "team_number" to "Team Number",
            "can_eject_terminal" to "Eject Terminal",
            "has_vision" to "Has vision",
            "can_cheesecake" to "Cheesecake",
            "can_intake_terminal" to "Intake Terminal",
            "can_under_low_rung" to "Under rung",
            "can_climb" to "Can climb",
            "match_number" to "Match Number",
            "auto_line" to "Taxi",
            "first_pickability" to "First Pickability",
            "second_pickability" to "Second Pickability",
            "auto_avg_near_hub_highs" to "Avg Near Hub Highs",
            "auto_avg_far_hub_highs" to "Avg Far Hub Highs",
            "auto_avg_launchpad_highs" to "Avg Launchpad Highs",
            "auto_avg_near_other_highs" to "Avg Near Other Highs",
            "auto_avg_far_other_highs" to "Avg Far Other Highs",
            "auto_avg_near_hub_lows" to "Avg Near Hub Lows",
            "auto_avg_far_hub_lows" to "Avg Far Hub Lows",
            "tele_avg_near_hub_highs" to "Avg Near Hub Highs",
            "tele_avg_far_hub_highs" to "Avg Far Hub Highs",
            "tele_avg_launchpad_highs" to "Avg Launchpad Highs",
            "tele_avg_near_other_highs" to "Avg Near Other Highs",
            "tele_avg_far_other_highs" to "Avg Far Other Highs",
            "tele_avg_near_hub_lows" to "Avg Near Hub Lows",
            "tele_avg_far_hub_lows" to "Avg Far Hub Lows",
            "auto_avg_balls_total" to "Avg Balls Total",
            "tele_avg_balls_total" to "Avg Balls Total",
            "avg_intakes" to "Avg Intakes",
            "avg_exit_ball_catches" to "Avg Balls Caught",
            "avg_opp_balls_scored" to "Avg Opp. Scored",
            "lfm_auto_avg_near_hub_highs" to "Avg Near Hub Highs",
            "lfm_auto_avg_far_hub_highs" to "Avg Far Hub Highs",
            "lfm_auto_avg_launchpad_highs" to "Avg Launchpad Highs",
            "lfm_auto_avg_near_other_highs" to "Avg Near Other Highs",
            "lfm_auto_avg_far_other_highs" to "Avg Far Other Highs",
            "lfm_auto_avg_near_hub_lows" to "Avg Near Hub Lows",
            "lfm_auto_avg_far_hub_lows" to "Avg Far Hub Lows",
            "lfm_tele_avg_near_hub_highs" to "Avg Near Hub Highs",
            "lfm_tele_avg_far_hub_highs" to "Avg Far Hub Highs",
            "lfm_tele_avg_launchpad_highs" to "Avg Launchpad Highs",
            "lfm_tele_avg_near_other_highs" to "Avg Near Other Highs",
            "lfm_tele_avg_far_other_highs" to "Avg Far Other Highs",
            "lfm_tele_avg_near_hub_lows" to "Avg Near Hub Lows",
            "lfm_tele_avg_far_hub_lows" to "Avg Far Hub Lows",
            "lfm_auto_avg_balls_low" to "Avg Balls Low",
            "lfm_auto_avg_balls_high" to "Avg Balls High",
            "lfm_tele_avg_balls_low" to "Avg Balls Low",
            "lfm_tele_avg_balls_high" to "Avg Balls HIgh",
            "lfm_avg_exit_ball_catches" to "Avg Balls Caught",
            "lfm_avg_opp_balls_scored" to "Avg Opp. Scored",
            "auto_sd_balls_low" to "SD Balls Low",
            "auto_sd_balls_high" to "SD Balls High",
            "tele_sd_balls_low" to "SD Balls Low",
            "tele_sd_balls_high" to "SD Balls High",
            "low_rung_successes" to "# Low Climbs",
            "mid_rung_successes" to "# Mid Climbs",
            "high_rung_successes" to "# High Climbs",
            "traversal_rung_successes" to "# Traversal Climbs",
            "matches_played" to "Matches Played",
            "matches_incap" to "# Matches Incap",
            "lfm_climb_all_attempts" to "# Climbs",
            "lfm_low_rung_successes" to "# Low CLimbs",
            "lfm_mid_rung_successes" to "# Mid Climbs",
            "lfm_high_rung_successes" to "# High Climbs",
            "lfm_traversal_rung_successes" to "# Traversal Climbs",
            "lfm_matches_incap" to "Matches Incap",
            "max_exit_ball_catches" to "# Max Balls Caught",
            "max_opp_balls_scored" to "# Max Opp. Balls Scored",
            "max_climb_level" to "Max Climb Level",
            "lfm_auto_max_balls_low" to "# Max Balls Low",
            "lfm_auto_max_balls_high" to "# Max Balls High",
            "lfm_tele_max_balls_low" to "# Max Balls Low",
            "lfm_tele_max_balls_high" to "# Max Balls High",
            "lfm_max_incap" to "Max Incap",
            "lfm_max_exit_ball_catches" to "# Max Balls Caught",
            "lfm_max_opp_balls_scored" to "# Max Opp. Scored",
            "lfm_max_climb_level" to "Max Climb Level",
            "mode_climb_level" to "Mode Climb Levels",
            "mode_start_position" to "Mode Start Position",
            "lfm_mode_start_position" to "Mode Start Position",
            "low_avg_time" to "Low Avg Time",
            "mid_avg_time" to "Mid Avg Time",
            "high_avg_time" to "High Avg Time",
            "traversal_avg_time" to "Traversal Avg Time",
            "lfm_low_avg_time" to "Low Avg Time",
            "lfm_mid_avg_time" to "Mid Avg Time",
            "lfm_high_avg_time" to "High Avg Time",
            "lfm_traversal_success_avg_time" to "Traversal Avg Time",
            "climb_percent_success" to "Climb % Success",
            "lfm_climb_percent_success" to "Climb % Success",
            "confidence_rating" to "Confidence Rating",
            "auto_near_hub_high" to "# Near Hub High",
            "auto_far_hub_highs" to "# Far Hub Highs",
            "auto_launchpad_highs" to "# Launchpad Highs",
            "auto_near_other_highs" to "# Near Other Highs",
            "auto_far_other_highs" to "# Far Other Highs",
            "auto_near_hub_lows" to "# Near Hub Lows" ,
            "auto_far_hub_lows" to "# Far Hub Lows",
            "tele_near_hub_highs" to "# Near Hub Highs",
            "tele_far_hub_highs" to "# Far Hub Highs",
            "tele_launchpad_highs" to "# Launchpad Highs",
            "tele_near_other_highs" to "# Near Other Highs",
            "tele_far_other_highs" to "# Far Other Highs",
            "tele_near_hub_lows" to "# Near Hub Lows",
            "tele_far_hub_lows" to "# Far Hub Lows",
            "intakes" to "# Intakes",
            "exit_ball_catches" to "# Balls Caught",
            "opp_balls_scored" to "# Opp. Balls Scored",
            "climb_level" to "Climb Level",
            "start_position" to "Start Position"
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
            "3" to "falcon"
        )

        val AVG_TO_TIM : Map<String, String> = mapOf(
            "auto_avg_balls_low" to "auto_balls_low",
            "auto_avg_balls_high" to "auto_balls_high",
            "tele_avg_balls_low" to "tele_balls_low",
            "tele_avg_balls_high" to "tele_balls_high",
            "avg_incap_time" to "incap",
            "climb_all_success_avg_time" to "climb_time"
        )
    }
}