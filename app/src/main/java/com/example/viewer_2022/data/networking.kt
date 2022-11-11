package com.example.viewer_2022

import android.util.Log
import android.util.Log.DEBUG
import com.example.viewer_2022.BuildConfig.DEBUG
import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.data.MatchScheduleMatch
import com.example.viewer_2022.fragments.offline_picklist.PicklistData
import io.ktor.client.*
import io.ktor.client.call.*

import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

const val grosbeakURL = "https://grosbeak.citruscircuits.org"

// Creates a client for the http request
val client = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json()
    }
    // Sets the timeout to be 30 seconds
    install(HttpTimeout) {
        requestTimeoutMillis = 30*1000
        connectTimeoutMillis = 30*1000
        socketTimeoutMillis = 30*1000
    }
    defaultRequest {
        header("Authorization", "02ae3a526cf54db9b563928b0ec05a77")
    }
}

// Gets the live picklist data from grosbeak and updates live picklist
object PicklistApi {
    suspend fun getPicklist(eventKey: String? = null): PicklistData = client.get("$grosbeakURL/picklist/rest/list") {
        if (eventKey != null) {
            parameter("event_key", eventKey)
        }
    }.body()
    // Sets the data in grosbeak to the new live picklist data
    suspend fun setPicklist(picklist: PicklistData, password: String, eventKey: String? = null): PicklistSetResponse = client.put("$grosbeakURL/picklist/rest/list") {
        parameter("password", password)
        if (eventKey != null) {
            parameter("event_key", eventKey)
        }
        contentType(ContentType.Application.Json)
        setBody(picklist)
    }.body()

    @Serializable(with = PicklistSetSerializer::class)
    sealed class PicklistSetResponse {
        @Serializable
        data class Success(val deleted: Int) : PicklistSetResponse()

        @Serializable
        data class Error(val error: String) : PicklistSetResponse()

    }
    object PicklistSetSerializer : JsonContentPolymorphicSerializer<PicklistSetResponse>(PicklistSetResponse::class) {
        override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out PicklistSetResponse> = when {
            element.jsonObject.containsKey("error") -> PicklistSetResponse.Error.serializer()
            element.jsonObject.containsKey("deleted") -> PicklistSetResponse.Success.serializer()
            else -> throw IllegalArgumentException("Unknown response type")
        }
    }

}

object DataApi {
    // Gets the data from grosbeak from a specific data collection
    // Note that we do not use this function currently as we are using the newly structured grosbeak database
    suspend fun getCollection(collectionName: String, eventKey: String?): JsonArray = client.get("$grosbeakURL/api/collection/$collectionName") {
        if (eventKey != null) {
            parameter("event_key", eventKey)
        }
    }.body()

    // Returns the Team List from grosbeak as a List
    suspend fun getTeamList(eventKey: String): List<Int> = client.get("$grosbeakURL/api/team-list/$eventKey").body()

    // Returns the Match Schedule from grosbeak as a Mutable Map
    suspend fun getMatchSchedule(eventKey: String): MutableMap<String, MatchScheduleMatch> = client.get("$grosbeakURL/api/match-schedule/$eventKey").body()

    suspend fun getViewerData(eventKey: String?): ViewerData = client.get("$grosbeakURL/api/viewer") {
        if (eventKey != null) {
            parameter("event_key", eventKey)
        }
            parameter("use_strings", true)
    }.body()

    @Serializable
    data class ViewerData (
        val team: Map<String, JsonObject>,
        val tim: Map<String, Map<String, JsonObject>>,
        val aim: Map<String, AimData>
    )

    @Serializable
    data class AimData (
        val red: JsonObject? = null,
        val blue: JsonObject? = null
    )
}

object NotesApi {
    @Serializable
    data class NotesData(val team_number: String, val notes: String)

    @Serializable
    data class GetNotesData(val success: Boolean, val notes: String)

    suspend fun getAllNotes(eventKey: String?): List<NotesData> = client.get("https://cardinal.citruscircuits.org/cardinal/api/notes/all/").body()

    suspend fun setNote(data: NotesData) {
        client.post("https://cardinal.citruscircuits.org/cardinal/api/notes/") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
    }

    suspend fun getNote(team_number: String): GetNotesData {
        return client.get("https://cardinal.citruscircuits.org/cardinal/api/notes/$team_number/").body()
    }

}
