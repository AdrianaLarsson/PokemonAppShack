package se.appshack.android.refactoring

import com.google.gson.annotations.SerializedName

class NamedResponseModel {
    @JvmField
    @SerializedName("name")
    var name: String? = null
    @JvmField
    @SerializedName("url")
    var url: String? = null

    @SerializedName("sprites")
    var sprites: PokemonSpritesModel? = null


}