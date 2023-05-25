package org.citruscircuits.viewer.data

// Data storage class for each individual match object.
data class Match(var matchNumber: String) {
    var redTeams: ArrayList<String> = ArrayList()
    var blueTeams: ArrayList<String> = ArrayList()
    var redPredictedScore: Float? = null
    var bluePredictedScore: Float? = null
    var redPredictedRPOne: Float? = null
    var bluePredictedRPOne: Float? = null
    var redPredictedRPTwo: Float? = null
    var bluePredictedRPTwo: Float? = null
    var redActualScore: Float? = null
    var blueActualScore: Float? = null
    var redActualRPOne: Float? = null
    var blueActualRPOne: Float? = null
    var redActualRPTwo: Float? = null
    var blueActualRPTwo: Float? = null
}