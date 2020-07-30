package se.appshack.android.refactoring.ModelClasses

import com.google.gson.annotations.SerializedName

data class UserClass(



        @field:SerializedName("firstName")
        val firstName: String? = null,

        @field:SerializedName("lastName")
        val lastName: String? = null,

        @field:SerializedName("email")
        val email: String? = null,

        @field:SerializedName("userName")
        val userName: String? = null






)