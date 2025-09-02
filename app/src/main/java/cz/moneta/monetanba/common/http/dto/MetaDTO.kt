package cz.moneta.monetanba.common.http.dto

import kotlinx.serialization.Serializable

// Pagination metadata
@Serializable
data class MetaDTO(
    val next_cursor: Int?,
    val per_page: Int?
)