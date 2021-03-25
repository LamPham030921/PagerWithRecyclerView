package com.example.pagerwithrecyclerview.response

import com.google.gson.annotations.SerializedName

class UserResponse(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("profile_image")
    var profile_image: UserResponse? = null,
    @SerializedName("large")
    var large: String? = null,
    @SerializedName("photos")
    var photos: MutableList<PhotoResponse>? = null,
)