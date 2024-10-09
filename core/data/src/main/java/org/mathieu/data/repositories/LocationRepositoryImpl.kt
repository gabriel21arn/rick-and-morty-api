package org.mathieu.data.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.mathieu.data.local.CharacterLocal
import org.mathieu.data.local.objects.CharacterObject
import org.mathieu.data.local.objects.toModel
import org.mathieu.data.local.objects.toRealmObject
import org.mathieu.data.remote.CharacterApi
import org.mathieu.data.remote.LocationApi
import org.mathieu.data.remote.responses.CharacterResponse
import org.mathieu.domain.repositories.CharacterRepository
import org.mathieu.domain.models.character.Character
import org.mathieu.domain.models.location.Location
import org.mathieu.domain.repositories.LocationRepository

private const val CHARACTER_PREFS = "character_repository_preferences"
private val nextPage = intPreferencesKey("next_characters_page_to_load")

private val Context.dataStore by preferencesDataStore(
    name = CHARACTER_PREFS
)

internal class LocationRepositoryImpl(
    private val context: Context,
    private val locationApi: LocationApi,
) : LocationRepository {

    /**
     * Retrieves the character with the specified ID.
     *
     * The function follows these steps:
     * 1. Tries to fetch the character from the local storage.
     * 2. If not found locally, it fetches the character from the API.
     * 3. Upon successful API retrieval, it saves the character to local storage.
     * 4. If the character is still not found, it throws an exception.
     *
     * @param id The unique identifier of the character to retrieve.
     * @return The [Character] object representing the character details.
     * @throws Exception If the character cannot be found both locally and via the API.
     */
    override suspend fun getLocation(id: Int): Location =
        locationApi.getLocation(id)?.toModel()
            ?: locationApi.getLocation(id = id)?.let { response ->
                val obj = response.toRealmObject()
                characterLocal.insert(obj)
                obj.toModel()
            }
            ?: throw Exception("Character not found.")


}


fun <T> tryOrNull(block: () -> T) = try {
    block()
} catch (_: Exception) {
    null
}

inline fun <T, R> Flow<List<T>>.mapElement(crossinline transform: suspend (value: T) -> R): Flow<List<R>> =
    this.map { list ->
        list.map { element -> transform(element) }
    }