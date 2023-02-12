package org.citruscircuits.viewer

/**
 * @param teamsList The list of teams to be converted to a map of current rankings
 */
fun convertToFilteredTeamsList(teamsList: List<String>): List<String> {
    val unsortedMap = HashMap<String, Double?>()
    for (team in teamsList) {
        unsortedMap[team] =
            if (getTeamObjectByKey(team, "current_rank") != null)
                getTeamObjectByKey(team, "current_rank")?.toDouble()
            else 1000.0
    }
    return unsortedMap.toList().sortedBy { (_, value) -> value }.toMap().keys.toList()
}