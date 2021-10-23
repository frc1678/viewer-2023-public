package com.example.viewer_2020

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import java.net.URL
import com.example.viewer_2020.MongoDatabaseStartupActivity.Companion.databaseReference
import com.example.viewer_2020.data.DatabaseReference
import com.google.gson.Gson
import java.io.*
import java.net.HttpURLConnection
import org.json.JSONObject
import java.time.LocalDateTime
import java.util.*

class GetDataFromWebsite(var context: Context, val onError: () -> Unit = {}): AsyncTask<String, String, String>() {

    override fun doInBackground(vararg p0: String?): String {
        try {
            //Sets the name of the collections on the website
            var listOfCollectionNames: List<String> =
                listOf(
                    "raw_obj_pit",
                    "subj_pit_collection",
                    "calc_obj_tim",
                    "calc_obj_team",
                    "calc_subj_team",
                    "calc_predicted_aim",
                    "calc_predicted_team",
                    "calc_tba_team",
                    "calc_pickability"
                )

            //For each of the collections (make sure to change this number if the number of collections change),
            //pull the data from the website and then add it to the databaseReference variable
            for (x in 0..8) {
                var result = StringBuilder()
                var url =
                    URL("https://cardinal.citruscircuits.org/cardinal/api/collection/${listOfCollectionNames[x]}/?format=json&test")
                var urlConnection = url.openConnection() as HttpURLConnection

                try {
                    val `in`: InputStream = BufferedInputStream(urlConnection.inputStream)

                    var reader = BufferedReader(InputStreamReader(`in`))
                    5
                    var line = reader.readText()
                    result.append(line)
                } catch (e: Exception) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }

                when (x) {
                    0 -> databaseReference?.raw_obj_pit = Gson().fromJson(
                        result.toString(),
                        Array<DatabaseReference.ObjectivePit>::class.java
                    ).toMutableList()
                    1 -> databaseReference?.raw_subj_pit = Gson().fromJson(
                        result.toString(),
                        Array<DatabaseReference.SubjectivePit>::class.java
                    ).toMutableList()
                    2 -> databaseReference?.obj_tim = Gson().fromJson(
                        result.toString(),
                        Array<DatabaseReference.CalculatedObjectiveTeamInMatch>::class.java
                    ).toMutableList()
                    3 -> databaseReference?.obj_team = Gson().fromJson(
                        result.toString(),
                        Array<DatabaseReference.CalculatedObjectiveTeam>::class.java
                    ).toMutableList()
                    4 -> databaseReference?.subj_team = Gson().fromJson(
                        result.toString(),
                        Array<DatabaseReference.CalculatedSubjectiveTeam>::class.java
                    ).toMutableList()
                    5 -> databaseReference?.predicted_aim = Gson().fromJson(
                        result.toString(),
                        Array<DatabaseReference.CalculatedPredictedAllianceInMatch>::class.java
                    ).toMutableList()
                    6 -> databaseReference?.predicted_team = Gson().fromJson(
                        result.toString(),
                        Array<DatabaseReference.CalculatedPredictedTeam>::class.java
                    ).toMutableList()
                    7 -> databaseReference?.tba_team = Gson().fromJson(
                        result.toString(),
                        Array<DatabaseReference.CalculatedTBATeam>::class.java
                    ).toMutableList()
                    8 -> databaseReference?.pickability = Gson().fromJson(
                        result.toString(),
                        Array<DatabaseReference.CalculatedPickAbilityTeam>::class.java
                    ).toMutableList()
                }
            }

            lastUpdated = Calendar.getInstance().time
            startActivity(context, Intent(context, MainViewerActivity::class.java), null)

            return ("finished")
        } catch (e: Throwable){
            onError()
            return ("error")
        }
    }

    override fun onPostExecute(result: String){
    }
}