/*
* Constants.kt
* viewer
*
* Created on 1/26/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2020

//Class that contains a collection of Constant values, or final values that never change
class Constants {
    companion object {
        const val TBA_EVENT_KEY = "2020cloudtest"
        const val DATABASE_NAME = "scouting_system_cloud"
        const val COLLECTION_NAME = "competitions"
        const val MONGO_ATLAS = "mongodb-atlas"

        val FIELDS_TO_BE_DISPLAYED: List<String> = listOf(
            "year"
        )
    }
}