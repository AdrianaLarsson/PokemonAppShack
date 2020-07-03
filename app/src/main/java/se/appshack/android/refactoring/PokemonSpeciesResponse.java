package se.appshack.android.refactoring;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class PokemonSpeciesResponse {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("genera")
    public List<GenusResponseModel> genera;
}
