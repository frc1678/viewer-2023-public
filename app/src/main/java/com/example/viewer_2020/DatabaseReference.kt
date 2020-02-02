/*
* DatabaseReference.kt
* viewer
*
* Created on 2/2/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2020

import org.bson.types.ObjectId

//Database reference class to make a database object from MongoDB.
class DatabaseReference {
    data class CompetitionObject (
        val _id: ObjectId,
        val year: Int,
        val week_num: Int,
        val tba_event_key: String,
        val raw: Raw,
        val tba_cache: Array<String>,
        val processed: Processed
    )

    data class Raw (
        val qr_obj: Array<String>,
        val qr_subj: Array<String>,
        val pit: Array<String>
    )

    data class Processed (
        val replay_outdated_qr: Array<String>,
        val unconsolodiated_obj_tim: Array<String>,
        val consolidated_obj_tim: Array<String>,
        val calc_subj_aim: Array<String>,
        val calc_team: Array<String>,
        val calc_match: Array<String>,
        val calc_obj_tim: Array<String>
    )
}
