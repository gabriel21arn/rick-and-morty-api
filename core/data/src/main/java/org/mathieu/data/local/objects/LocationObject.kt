package org.mathieu.data.local.objects

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import org.mathieu.data.remote.responses.CharacterResponse
import org.mathieu.data.remote.responses.LocationResponse
import org.mathieu.domain.repositories.CharacterRepository
import org.mathieu.data.repositories.tryOrNull
import org.mathieu.domain.models.character.*
import org.mathieu.domain.models.location.Location

/**
 * Represents a location entity stored in the SQLite database. This object provides fields
 * necessary to represent all the attributes of a location from the data source.
 * The object is specifically tailored for SQLite storage using Realm.
 *
 * @property id Unique identifier of the character.
 * @property name Name of the character.
 * @property status Current status of the character (e.g. 'Alive', 'Dead').
 * @property species Biological species of the character.
 * @property type The type or subspecies of the character.
 * @property gender Gender of the character (e.g. 'Female', 'Male').
 * @property originName The origin location name.
 * @property originId The origin location id.
 * @property locationName The current location name.
 * @property locationId The current location id.
 * @property image URL pointing to the character's avatar image.
 * @property created Timestamp indicating when the character entity was created in the database.
 */
internal class LocationObject: RealmObject {
    @PrimaryKey
    var id: Int = -1
    var name: String = ""
    var type: String = ""
    var dimension: String = ""
    var residents: MutableList<Int> = listOf(-1).toMutableList()
}


internal fun LocationResponse.toRealmObject() = LocationObject().also { obj ->
    obj.id = id
    obj.name = name
    obj.type = type
    obj.dimension = dimension

    for (resident in residents) {
        obj.residents.add(resident.split("/").last().toInt())
    }
}

internal fun LocationObject.toModel() = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = residents
)
