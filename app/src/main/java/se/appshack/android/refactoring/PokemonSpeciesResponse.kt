package se.appshack.android.refactoring

import com.google.gson.annotations.SerializedName

internal class PokemonSpeciesResponse {
    @SerializedName("id")
    var id = 0
    @SerializedName("name")
    var name: String? = null
    @SerializedName("genera")
    var genera: List<GenusResponseModel>? = null
}