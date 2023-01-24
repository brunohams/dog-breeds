package datasource.dto

import kotlinx.serialization.Serializable

@Serializable
data class JsonResponseDTO<T>(
    val status: String,
    val message: T,
)
