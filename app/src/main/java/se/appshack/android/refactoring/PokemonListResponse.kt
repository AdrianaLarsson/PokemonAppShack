package se.appshack.android.refactoring

import com.google.gson.annotations.SerializedName

internal class PokemonListResponse {
    @SerializedName("count")
    var count = 0
    @SerializedName("next")
    var next: String? = null
    @SerializedName("previous")
    var previous: String? = null
    @SerializedName("results")
    var results: List<NamedResponseModel>? = null
}