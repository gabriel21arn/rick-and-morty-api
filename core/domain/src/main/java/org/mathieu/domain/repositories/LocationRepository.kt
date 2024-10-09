package org.mathieu.domain.repositories

import kotlinx.coroutines.flow.Flow
import org.mathieu.domain.models.location.Location

interface LocationRepository {
    /**
     * Fetches the location based on the provided ID.
     *
     * @param id The unique identifier of the location to be fetched.
     * @return The location.
     */
    suspend fun getLocation(id: Int): Location
}