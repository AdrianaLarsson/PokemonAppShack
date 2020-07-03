package se.appshack.android.refactoring;

import com.google.gson.annotations.SerializedName;

class PokemonTypeModel {

    @SerializedName("slot")
    public int slot;

    @SerializedName("type")
    public NamedResponseModel type;
}
