package com.example.pagerwithrecyclerview.response

import com.google.gson.annotations.SerializedName

class Photo(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("urls")
    var urls: Photo? = null,
    @SerializedName("raw")
    var raw: String? = null,
    @SerializedName("regular")
    var regular: String? = null,
    @SerializedName("small")
    var small: String? = null,
    @SerializedName("thumb")
    var thumb: String? = null,
    @SerializedName("width")
    var width: Int? = null,
    @SerializedName("height")
    var height: Int? = null
)