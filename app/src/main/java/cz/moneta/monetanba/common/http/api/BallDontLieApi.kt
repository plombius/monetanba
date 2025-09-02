package cz.moneta.monetanba.common.http.api

import cz.moneta.monetanba.common.http.dto.PlayerDTO
import cz.moneta.monetanba.common.http.dto.PlayersResponseDTO
import cz.moneta.monetanba.common.http.dto.TeamDTO
import cz.moneta.monetanba.common.http.dto.TeamResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface BallDontLieApi {

    @GET("players")
    suspend fun getPlayers(
        @Query("cursor") cursor: Int? = null,
        @Query("per_page") perPage: Int? = null,
        @Query("search") search: String? = null,
        @Query("first_name") firstName: String? = null,
        @Query("last_name") lastName: String? = null,
        @Query("team_ids") teamIds: List<Int>? = null,
        @Query("player_ids") playerIds: List<Int>? = null
    ): Response<PlayersResponseDTO>

    @GET("teams/{id}")
    suspend fun getTeamById(
        @retrofit2.http.Path("id") teamId: Int
    ): Response<TeamResponseDTO>
}
