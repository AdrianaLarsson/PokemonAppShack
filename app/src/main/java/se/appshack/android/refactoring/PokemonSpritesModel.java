package se.appshack.android.refactoring;

import com.google.gson.annotations.SerializedName;

class PokemonSpritesModel {

    @SerializedName("front_default")
    public String urlFront;

    @SerializedName("back_default")
    public String urlBack;
}
