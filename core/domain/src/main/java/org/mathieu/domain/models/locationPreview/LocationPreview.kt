package org.mathieu.domain.models.character

/**
 * Represents a Location Preview.
 *
 * @property id The unique identifier for the location preview.
 * @property name The name of the location preview.
 * @property type The location preview type.
 * @property dimension The dimension of location preview.
 */
data class LocationPreview(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)
