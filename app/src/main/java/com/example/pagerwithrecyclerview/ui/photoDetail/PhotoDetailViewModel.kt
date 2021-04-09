package com.example.pagerwithrecyclerview.ui.photoDetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pagerwithrecyclerview.response.PhotoResponse
import com.example.pagerwithrecyclerview.response.UserResponse
import com.example.pagerwithrecyclerview.unsplash.Config
import com.example.pagerwithrecyclerview.unsplash.UnsplashApi
import com.example.pagerwithrecyclerview.unsplash.UnsplashClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoDetailViewModel : ViewModel() {
    private var dataService: UnsplashApi =
        UnsplashClient.getUnsplashClient()!!.create(UnsplashApi::class.java)
    val userResponse by lazy { MutableLiveData<UserResponse>() }
    val listPhoto by lazy { MutableLiveData<MutableList<PhotoResponse>>() }

    fun getUserInfo(userId: String) {
        dataService.getUserInfo(Config.unsplash_access_key, userId)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    userResponse.postValue(response.body())
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.wtf("Error", "${t.message}")
                }

            })
    }

    fun getListPhoto(userName: String, page: Int, perPage: Int) {
        dataService.getUserPhotos(Config.unsplash_access_key, userName, page, perPage)
            .enqueue(object : Callback<MutableList<PhotoResponse>> {
                override fun onResponse(
                    call: Call<MutableList<PhotoResponse>>,
                    response: Response<MutableList<PhotoResponse>>
                ) {
                    listPhoto.postValue(response.body())
                    Log.wtf("body body", "${response.body()}")
                }

                override fun onFailure(call: Call<MutableList<PhotoResponse>>, t: Throwable) {
                    Log.wtf("error", "${t.message}")
                }

            })
    }
}