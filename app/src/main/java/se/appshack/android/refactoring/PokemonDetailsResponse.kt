package se.appshack.android.refactoring

import com.google.gson.annotations.SerializedName

internal class PokemonDetailsResponse {
    @SerializedName("id")
    var id = 0
    @SerializedName("name")
    var name: String? = null
    @SerializedName("height")
    var height = 0
    @SerializedName("weight")
    var weight = 0
    @SerializedName("species")
    var species: NamedResponseModel? = null
    @SerializedName("types")
    var types: List<PokemonTypeModel>? = null
    @SerializedName("sprites")
    var sprites: PokemonSpritesModel? = null

}