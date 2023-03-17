/*
* getAllianceMatchObjectByKey.kt
* viewer
*
* Created on 3/16/2023
* Copyright 2023 Citrus Circuits. All rights reserved.
*/

package org.citruscircuits.viewer

import kotlinx.serialization.json.jsonPrimitive

fun getPredictedAlliancesDataByKey(
    allianceNumber: String,
    field: String
): String? {
    return StartupActivity.databaseReference?.predictedAlliances?.get(allianceNumber)?.get(field)?.jsonPrimitive?.content
}