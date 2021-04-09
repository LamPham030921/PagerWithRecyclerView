package com.example.pagerwithrecyclerview.unsplash

import com.example.pagerwithrecyclerview.response.PhotoResponse
import com.example.pagerwithrecyclerview.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {

    companion object {
        const val ID = "id"
        const val PAGE = "page"
        const val PER_PAGE = "per_page"
        const val USERNAME = "username"
    }

    @GET("photos/{id}")
    fun getPhoto(@Path(ID) id: Int): PhotoResponse

    @GET("photos")
    fun getPhotos(
        @Header("Authorization") author: String,
        @Query(PAGE) page: Int?,
        @Query(PER_PAGE) perPage: Int?
    ): Call<MutableList<PhotoResponse>>

    @GET("users/{username}")
    fun getUserInfo(
        @Header("Authorization") author: String,
        @Path(USERNAME) username: String
    ): Call<UserResponse>

    @GET("users/{username}/photos")
    fun getUserPhotos(
        @Header("Authorization") author: String,
        @Path(USERNAME) username: String,
        @Query(PAGE) page: Int?,
        @Query(PER_PAGE) perPage: Int?
    ): Call<MutableList<PhotoResponse>>
}