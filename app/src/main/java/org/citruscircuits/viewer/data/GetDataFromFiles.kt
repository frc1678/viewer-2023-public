package org.citruscircuits.viewer.data

import android.content.res.Resources
import android.util.Log
import org.citruscircuits.viewer.MainViewerActivity
import org.citruscircuits.viewer.R
import org.citruscircuits.viewer.StartupActivity.Companion.databaseReference
import org.citruscircuits.viewer.lastUpdated
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.*
import java.util.*


/**
 * Async task to get data from test data files
 */

suspend fun loadTestData(resources: Resources) {
    //For each of the collections (make sure to change this number if the number of collections change),
    //pull the data from the website and then add it to the databaseReference variable
    databaseReference =
        Json.decodeFromString<DataApi.ViewerData>(readFile(resources.openRawResource(R.raw.test_data)))
    val rawMatchSchedule = Json.decodeFromString<MutableMap<String, MatchScheduleMatch>>(
        readFile(
            resources.openRawResource(R.raw.test_match_schedule)
        )
    )

    for (i in rawMatchSchedule) {
        val match = Match(i.key)
        for (j in i.value.teams) {
            when (j.color) {
                "red" -> {
                    match.redTeams.add(j.number.toString())
                }
                "blue" -> {
                    match.blueTeams.add(j.number.toString())
                }
            }
        }

        Log.e("parsedmap", match.toString())
        MainViewerActivity.matchCache[i.key] = match
    }
    MainViewerActivity.matchCache =
        MainViewerActivity.matchCache.toList().sortedBy { (k, v) -> v.matchNumber.toInt() }
            .toMap().toMutableMap()

    MainViewerActivity.teamList =
        Json.decodeFromString<List<Int>>(readFile(resources.openRawResource(R.raw.test_team_list)))
            .map { it.toString() }

    lastUpdated = Calendar.getInstance().time

}

private fun readFile(file: InputStream): String {
    val `is`: InputStream = file
    val writer: Writer = StringWriter()
    val buffer = CharArray(1024)
    try {
        val reader: Reader = BufferedReader(InputStreamReader(`is`, "UTF-8"))
        var n: Int
        while (reader.read(buffer).also { n = it } != -1) {
            writer.write(buffer, 0, n)
        }
    } finally {
        `is`.close()
    }
    return writer.toString()
}