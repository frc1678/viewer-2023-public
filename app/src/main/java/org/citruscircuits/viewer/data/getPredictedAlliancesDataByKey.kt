/*
* getAllianceMatchObjectByKey.kt
* viewer
*
* Created on 3/16/2023
* Copyright 2023 Citrus Circuits. All rights reserved.
*/

package org.citruscircuits.viewer.data

import kotlinx.serialization.json.jsonPrimitive
import org.citruscircuits.viewer.StartupActivity.Companion.databaseReference

fun getPredictedAlliancesDataByKey(
    allianceNumber: String,
    field: String
): String? {
    return databaseReference?.predictedAlliances?.get(allianceNumber)?.get(field)?.jsonPrimitive?.content
}