package cz.moneta.monetanba.common.http.dto

import kotlinx.serialization.Serializable

// Response wrapper
@Serializable
data class PlayersResponseDTO(
    val data: List<PlayerDTO>,
    val meta: MetaDTO
)