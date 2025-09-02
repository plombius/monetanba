package cz.moneta.monetanba.common.localdata

import cz.moneta.monetanba.common.http.dto.TeamDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TeamCache {
    val cachedTeams: MutableList<TeamDTO> = mutableListOf()

    fun addTeamToCache(teamDTO: TeamDTO?) {
        teamDTO?.let {
            cachedTeams.add(teamDTO)
        }
    }

    fun getCachedTeam(id: Int): TeamDTO?{
        return cachedTeams.find { it.id == id }
    }
}