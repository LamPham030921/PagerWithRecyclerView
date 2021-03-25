package com.example.pagerwithrecyclerview.response

import com.google.gson.annotations.SerializedName

class UserProfileImageResponse (
    @SerializedName("small")
    var small: String? = null,
    @SerializedName("medium")
    var medium: String? = null,
    @SerializedName("large")
    var large: String? = null
)