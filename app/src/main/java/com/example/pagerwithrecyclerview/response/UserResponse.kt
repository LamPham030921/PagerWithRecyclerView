package com.example.pagerwithrecyclerview.response

import com.google.gson.annotations.SerializedName

class UserResponse(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("total_likes")
    var totalLike: Int? = null,
    @SerializedName("total_photos")
    var totalPhoto: Int? = null,
    @SerializedName("profile_image")
    var profile_image: UserResponse? = null,
    @SerializedName("large")
    var large: String? = null,
    @SerializedName("photos")
    var photos: MutableList<PhotoResponse>? = null,
)