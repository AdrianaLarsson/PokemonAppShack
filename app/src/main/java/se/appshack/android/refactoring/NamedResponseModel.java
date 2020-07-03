package se.appshack.android.refactoring;

import com.google.gson.annotations.SerializedName;

class NamedResponseModel {

    @SerializedName("name")
    public String name;

    @SerializedName("url")
    public String url;
}
