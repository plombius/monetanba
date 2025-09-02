package cz.moneta.monetanba.common.http.dto

import kotlinx.serialization.Serializable

// Response wrapper
@Serializable
data class TeamResponseDTO(
    val data: TeamDTO
)