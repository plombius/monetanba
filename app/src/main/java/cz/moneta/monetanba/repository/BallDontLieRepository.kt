package cz.moneta.monetanba.repository

import cz.moneta.monetanba.common.RepositoryCallResponse
import cz.moneta.monetanba.common.http.api.BallDontLieApi
import cz.moneta.monetanba.common.http.dto.PlayersResponseDTO
import cz.moneta.monetanba.common.http.dto.TeamDTO
import cz.moneta.monetanba.common.http.dto.TeamResponseDTO
import cz.moneta.monetanba.common.http.performNetworkCall
import cz.moneta.monetanba.common.localdata.TeamCache
import kotlinx.coroutines.delay

class BallDontLieRepository(
    val ballDontLieApi: BallDontLieApi,
    val teamCache: TeamCache
) {
    suspend fun getPlayers(
        perPage: Int,
        cursor: Int
    ): RepositoryCallResponse<PlayersResponseDTO>{
        return performNetworkCall {
            ballDontLieApi.getPlayers(perPage = perPage, cursor = cursor)
        }
    }

    // --- Repository: getTeam (cache-first) ---
    suspend fun getTeam(
        id: Int
    ): RepositoryCallResponse<TeamResponseDTO> {
        val cached = teamCache.getCachedTeam(id)

        return if(cached != null)
            RepositoryCallResponse(dto = TeamResponseDTO(cached))
        else {
            val result = performNetworkCall {
                ballDontLieApi.getTeamById(teamId = id)
            }
            teamCache.addTeamToCache(result.dto?.data)
            result
        }
    }
}