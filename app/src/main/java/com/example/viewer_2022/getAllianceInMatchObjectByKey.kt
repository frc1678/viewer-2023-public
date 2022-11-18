/*
* getAllianceMatchObjectByKey.kt
* viewer
*
* Created on 2/18/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2022

import kotlinx.serialization.json.jsonPrimitive

// Returns a string value of any alliance object in the database as long as you provide it with
// the match number and the alliance of the color (and the requested field).
// 'path' is for whichever branch of alliances you want (ex. predicted_aim).

// It WILL iterate through every object in the given path until it finds the correct one which is a
// bit heavy, yet there's no obvious better way to do it given the structure of our database.

// If the value cannot be found, then it returns null
fun getAllianceInMatchObjectByKey(
    allianceColor: String,
    matchNumber: String,
    field: String
): String? {
    return if (allianceColor == "red")
        StartupActivity.databaseReference?.aim?.get(matchNumber)?.red?.get(field)?.jsonPrimitive?.content
    else
        StartupActivity.databaseReference?.aim?.get(matchNumber)?.blue?.get(field)?.jsonPrimitive?.content
}