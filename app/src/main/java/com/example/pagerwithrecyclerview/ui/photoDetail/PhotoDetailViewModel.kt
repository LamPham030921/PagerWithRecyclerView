package com.example.pagerwithrecyclerview.ui.photoDetail

import android.util.Log
import android.widget.Toast
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
    private var page = 1
    val userResponse by lazy { MutableLiveData<UserResponse>() }

    fun getUserInfo(userId : String) {
        dataService.getUserInfo(Config.unsplash_access_key, userId).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                userResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.wtf("Error", "${t.message}")
            }

        })
    }
}