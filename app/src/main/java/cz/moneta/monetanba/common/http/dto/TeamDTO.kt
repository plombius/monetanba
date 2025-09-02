package cz.moneta.monetanba.common.http.dto

import kotlinx.serialization.Serializable

// Team nested object
@Serializable
data class TeamDTO(
    val id: Int,
    val conference: String,
    val division: String,
    val city: String,
    val name: String,
    val full_name: String,
    val abbreviation: String
)