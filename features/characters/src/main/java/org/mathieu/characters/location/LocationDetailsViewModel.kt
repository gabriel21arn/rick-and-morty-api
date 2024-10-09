package org.mathieu.Location.location

import android.app.Application
import org.koin.core.component.inject
import org.mathieu.domain.models.character.Character



class LocationDetailsViewModel(application: Application) : org.mathieu.ui.ViewModel<LocationDetailsState>(
    LocationDetailsState(), application) {

    private val locationRepository: org.mathieu.domain.repositories.CharacterRepository by inject()

    fun init(characterId: Int) {
        fetchData(
            source = { locationRepository.getCharacter(id = characterId) }
        ) {

            onSuccess {
                updateState { copy(name = "name Location", type = "Type Location", residents = listOf(Character(
                    id = 1,
                    name = "name resident",
                    status = null,
                    species = "species resident",
                    type = "type resident",
                    gender = null,
                    origin = null,
                    location = null,
                    avatarUrl = "url",
                    locationPreviews = null
                )), error = null) }
            }

            onFailure {
                updateState { copy(error = it.toString()) }
            }

            updateState { copy(isLoading = false) }
        }
    }


}


data class LocationDetailsState(
    val isLoading: Boolean = true,
    val type: String = "",
    val name: String = "",
    val residents: List<Character> = listOf(),
    val error: String? = null
)