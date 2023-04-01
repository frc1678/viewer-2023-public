package org.citruscircuits.viewer.data

import kotlinx.serialization.Serializable

@Suppress("PropertyName")
@Serializable
data class AutoPath(
    val match_numbers: String,
    val auto_charge_level: String,
    val auto_charge_successes: Int,
    val intake_1_piece: String?,
    val intake_1_position: String?,
    val intake_2_piece: String?,
    val intake_2_position: String?,
    val preloaded_gamepiece: String,
    val score_1_piece: String?,
    val score_1_piece_successes: Int,
    val score_1_max_piece_successes: Int,
    val score_1_position: String?,
    val score_2_piece: String?,
    val score_2_piece_successes: Int,
    val score_2_max_piece_successes: Int,
    val score_2_position: String?,
    val score_3_piece: String?,
    val score_3_piece_successes: Int,
    val score_3_max_piece_successes: Int,
    val score_3_position: String?,
    val matches_ran: Int,
    val mobility: Boolean
)
