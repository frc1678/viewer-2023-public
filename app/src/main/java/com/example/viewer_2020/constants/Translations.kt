package com.example.viewer_2020.constants

class Translations {
    companion object {
        val ACTUAL_TO_HUMAN_READABLE: Map<String, String> = mapOf(
            "team_name" to "Team Name",
            "driver_agility" to "Driver Agility",
            "driver_speed" to "Driver Speed",
            "driver_ability" to "Driver Ability",
            "auto_avg_balls_low" to "Auto Low",
            "auto_avg_balls_high" to "Auto High",
            "tele_avg_balls_low" to "Tele Low",
            "tele_avg_balls_high" to "Tele High",
            "climb_all_attempts" to "Climb Attempts",
            "current_seed" to "Current Rank",
            "predicted_rank" to "Pred. Rank",
            "predicted_rps" to "Pred. RPs",
            "predicted_rp1" to "Pred. climb RP",
            "predicted_rp2" to "Pred. 3rd stage RP",
            "predicted_score" to "Pred. score",
            "current_rps" to "RPs",
            "current_avg_rps" to "Avg RPs",
            "tele_cp_rotation_successes" to "# Rotations",
            "tele_cp_position_successes" to "# Positions",
            "climb_percent_success" to "Climb Success %",
            "park_successes" to "Parks",
            "climb_all_success_avg_time" to "Avg Climb Time",
            "drivetrain_motor_type" to "Motor Type",
            "drivetrain_motors" to "Drivetrain Motors",
            "drivetrain" to "Drivetrain",
            "can_cross_trench" to "Can cross trench?",
            "has_ground_intake" to "Has ground intake?",
            "climber_strap_installation_notes" to "Strap Install Notes",
            "climber_strap_installation_difficulty" to "Strap Install Diff",
            "climb_all_successes" to "# Climbs",
            "climb_level_successes" to "# Level Climbs",
            "auto_line_successes" to "# Auto Lines"
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
    }
}