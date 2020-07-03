package se.appshack.android.refactoring;

import com.google.gson.annotations.SerializedName;

class GenusResponseModel {

    @SerializedName("genus")
    public String genus;

    @SerializedName("language")
    public NamedResponseModel language;
}
