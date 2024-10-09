package org.mathieu.characters.details

import android.app.Application
import org.koin.core.component.inject
import org.mathieu.domain.models.character.LocationPreview
import org.mathieu.domain.models.location.Location
import org.mathieu.domain.repositories.CharacterRepository
import org.mathieu.ui.ViewModel


class CharacterDetailsViewModel(application: Application) : org.mathieu.ui.ViewModel<CharacterDetailsState>(
    CharacterDetailsState(), application) {

    private val characterRepository: org.mathieu.domain.repositories.CharacterRepository by inject()

    fun init(characterId: Int) {
        fetchData(
            source = { characterRepository.getCharacter(id = characterId) }
        ) {

            onSuccess {
                updateState { copy(avatarUrl = it.avatarUrl, name = it.name, location = characterRepository., error = null) }
            }

            onFailure {
                updateState { copy(error = it.toString()) }
            }

            updateState { copy(isLoading = false) }
        }
    }


}


data class CharacterDetailsState(
    val isLoading: Boolean = true,
    val avatarUrl: String = "",
    val name: String = "",
    val location: Location = Location(
        id = 0,
        name = "Unknow",
        type = "Unknow",
        dimension = "Unknow",
        residents = listOf()
    ),
    val error: String? = null
)