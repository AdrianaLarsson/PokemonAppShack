package se.appshack.android.refactoring.ModelClasses

import com.google.gson.annotations.SerializedName
import se.appshack.android.refactoring.NamedResponseModel

internal class PokemonTypeModel {
    @SerializedName("slot")
    var slot = 0
    @SerializedName("type")
    var type: NamedResponseModel? = null
}