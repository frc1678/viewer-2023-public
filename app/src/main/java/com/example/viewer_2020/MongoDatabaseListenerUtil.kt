/*
* MongoDatabaseListenerUtil.kt
* viewer
*
* Created on 1/26/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2020

import android.util.Log
import com.example.viewer_2020.constants.Constants
import com.example.viewer_2020.data.DatabaseReference
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.gson.Gson
import com.mongodb.BasicDBObject
import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Projections
import com.mongodb.stitch.android.core.Stitch
import com.mongodb.stitch.android.core.StitchAppClient
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential
import com.mongodb.stitch.core.services.mongodb.remote.RemoteFindOptions
import org.bson.Document
import java.lang.Exception
import java.lang.reflect.Field
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

// Class whose usage is to communicate with MongoDB, essentially making it the middle man
// between all database-app communication.
class MongoDatabaseListenerUtil {
    private var stitchClient: StitchAppClient = Stitch.getDefaultAppClient()
    private var mongoClient: RemoteMongoClient = stitchClient.getServiceClient(RemoteMongoClient.factory, Constants.MONGO_ATLAS)

    // Loads the competition document of the given TBA event whose value is set in Constants/TBA_EVENT_KEY.
    // To request authorization, we log in to MongoDB using an anonymous credential.
    // Afterwards, we filter the 'competition' collection to make sure we are in the right database
    // by ensuring that the tba_event_key of the database is the one equal to the tba_event_key
    // set in Constants.

    inline fun <reified T>getDocumentList (collection: RemoteMongoCollection<Document>, list: MutableList<T>): Task<Void> {
        return collection.find().forEach{
            list.add(Gson().fromJson(Document(it).toJson(), T::class.java))
        }.addOnFailureListener{
            Log.e("Failed to pull document","$collection")
        }
    }

    // After accessing the correct competition, we pull every key of data that is listed in
    // Constants.FIELDS_TO_BE_DISPLAYED.
    fun getCompetitionInformation(callback: Callback<DatabaseReference.CompetitionObject>) {
        stitchClient.auth.loginWithCredential(AnonymousCredential()).continueWith {
            Log.e("Log_loginWithCredential", "Login SUCCESS" + it.isSuccessful().toString())
            var database = mongoClient.getDatabase(Constants.DATABASE_NAME)
            var competitionObject = DatabaseReference.CompetitionObject()
            Tasks.whenAll(listOf(
                getDocumentList<DatabaseReference.ObjectivePit>(database.getCollection("raw_obj_pit"), competitionObject.raw_obj_pit),
//                getDocumentList<DatabaseReference.SubjectivePit>(database.getCollection("raw_subj_pit"), competitionObject.raw_subj_pit),
                getDocumentList<DatabaseReference.CalculatedObjectiveTeamInMatch>(database.getCollection("obj_tim"), competitionObject.obj_tim),
                getDocumentList<DatabaseReference.CalculatedObjectiveTeam>(database.getCollection("obj_team"), competitionObject.obj_team),
                getDocumentList<DatabaseReference.CalculatedSubjectiveTeam>(database.getCollection("subj_team"), competitionObject.subj_team),
                getDocumentList<DatabaseReference.CalculatedPredictedAllianceInMatch>(database.getCollection("predicted_aim"), competitionObject.predicted_aim),
                getDocumentList<DatabaseReference.CalculatedPredictedTeam>(database.getCollection("predicted_team"), competitionObject.predicted_team),
                getDocumentList<DatabaseReference.CalculatedTBATeam>(database.getCollection("tba_team"), competitionObject.tba_team),
                getDocumentList<DatabaseReference.CalculatedPickAbilityTeam>(database.getCollection("pickability"), competitionObject.pickability)
            )).addOnSuccessListener{
                callback.execute(competitionObject)
            }.addOnFailureListener{
                Log.e("Failed_to_pull","UNABLE TO PULL COLLECTIONS FROM DB")
            }
        }
    }

    //todo After the restructure of the database, we are unable to re-update to the database. We are restructuring how to locate data
    // in the local version of the database (which should make resync-ing easier so we'll come back to this task)

    // Watches the specified MongoDB collection and listens for any changes made throughout
    // the whole project.

    // The event.operationType can be both UPDATE and REPLACE
    // If the operationType is REPLACE, then the event.updateDescription will return null,
    // meaning you cannot access the updates made to the database when the listener is triggered.

    // The operationType will be REPLACE when the database is manually altered on MongoDB compass,
    // so to have the operationType remain as UPDATE, you must make changes to the database
    // programmatically on the server-side.
//    fun startDatabaseListener() {
//        collection.watch().addOnCompleteListener {
//            it.result.addChangeEventListener { _, event ->
//                event.updateDescription?.updatedFields?.keys!!.forEach { change ->
//                    val key = (change.substring(0, change.indexOf(".")))
//                    val subKey = (change.substring(key.length + 1, change.length)).substring(
//                        0,
//                        (change.substring(key.length + 1, change.length)).indexOf(".")
//                    ).trim()
//                    val index: String =
//                        (change.substring(change.lastIndexOf(".") + 1, change.length))
//                    event.updateDescription?.updatedFields!!["$key.$subKey.$index"]!!.asDocument()!!.keys.forEach { changedField ->
//                        val databaseReference = MainViewerActivity.databaseReference!!
//                        val currentSubKeyReference: Field =
//                            getDirectField(databaseReference, key)::class.java.getDeclaredField(subKey)
//                        currentSubKeyReference.isAccessible = true
//                        val currentDataValueInSubKey =
//                            (currentSubKeyReference.get(databaseReference.processed)!! as Array<*>)[index.toInt()]
//                        val currentDataValueFromSubKeyReference = currentDataValueInSubKey!!::class.java.getDeclaredField(changedField)
//                        currentDataValueFromSubKeyReference.isAccessible = true
//                        try {
//                            currentDataValueFromSubKeyReference[currentDataValueInSubKey] = (event.updateDescription?.updatedFields!!
//                                    ["$key.$subKey.$index"]!!.asDocument().getInt32(changedField).value)
//                        } catch (e: Exception){
//                            try {
//                                currentDataValueFromSubKeyReference[currentDataValueInSubKey] = (event.updateDescription?.updatedFields!!
//                                        ["$key.$subKey.$index"]!!.asDocument().getDouble(changedField).value)
//                            } catch (e: Exception) {
//                                try {
//                                    currentDataValueFromSubKeyReference[currentDataValueInSubKey] = (event.updateDescription?.updatedFields!!
//                                            ["$key.$subKey.$index"]!!.asDocument().getString(changedField).value)
//                                } catch (e: Exception) {
//                                    try {
//                                        currentDataValueFromSubKeyReference[currentDataValueInSubKey] = (event.updateDescription?.updatedFields!!
//                                                ["$key.$subKey.$index"]!!.asDocument().getBoolean(changedField).value)
//                                    } catch (e: Exception) {
//                                        e.printStackTrace()
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

    // Interface to access the CompetitionObject in an asynchronous function.
    // Meant to be overridden on initial class usage.
    interface Callback<T> {
        fun execute(response: T)
    }
}
