package se.appshack.android.refactoring;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class PokemonDetailsResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("height")
    public int height;

    @SerializedName("weight")
    public int weight;

    @SerializedName("species")
    public NamedResponseModel species;

    @SerializedName("types")
    public List<PokemonTypeModel> types;

    @SerializedName("sprites")
    public PokemonSpritesModel sprites;
}
