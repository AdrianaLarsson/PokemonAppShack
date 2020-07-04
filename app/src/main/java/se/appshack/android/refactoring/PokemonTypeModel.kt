package se.appshack.android.refactoring

import com.google.gson.annotations.SerializedName

internal class PokemonTypeModel {
    @SerializedName("slot")
    var slot = 0
    @SerializedName("type")
    var type: NamedResponseModel? = null
}