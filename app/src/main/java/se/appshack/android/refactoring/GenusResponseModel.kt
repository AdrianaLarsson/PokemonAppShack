package se.appshack.android.refactoring

import com.google.gson.annotations.SerializedName

internal class GenusResponseModel {
    @JvmField
    @SerializedName("genus")
    var genus: String? = null
    @JvmField
    @SerializedName("language")
    var language: NamedResponseModel? = null
}