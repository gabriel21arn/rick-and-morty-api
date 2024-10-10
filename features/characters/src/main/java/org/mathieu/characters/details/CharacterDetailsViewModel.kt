package org.mathieu.characters.character

import android.app.Application
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import org.koin.core.component.inject
import org.mathieu.characters.list.CharactersAction
import org.mathieu.domain.models.character.Character
import org.mathieu.domain.models.location.Location
import org.mathieu.ui.Destination

sealed interface LocationAction {
    data class SelectedLocation(val location: Location):
        LocationAction
}

class CharacterDetailsViewModel(application: Application) : org.mathieu.ui.ViewModel<CharacterDetailsState>(
    CharacterDetailsState(), application) {

    private val characterRepository: org.mathieu.domain.repositories.CharacterRepository by inject()

    fun init(characterId: Int) {
        fetchData(
            source = { characterRepository.getCharacter(id = characterId) }
        ) {

            onSuccess {
                updateState { copy(avatarUrl = it.avatarUrl, name = it.name, error = null) }
            }

            onFailure {
                updateState { copy(error = it.toString()) }
            }

            updateState { copy(isLoading = false) }
        }
    }

    fun handleAction(action: LocationAction) {
        when(action) {
            is LocationAction.SelectedLocation -> selectedLocation(action.location)
        }
    }


    private fun selectedLocation(location: Location) =
        sendEvent(Destination.CharacterDetails(location.id.toString()))

}



data class CharacterDetailsState(
    val isLoading: Boolean = true,
    val avatarUrl: String = "",
    val name: String = "",
    val error: String? = null
)