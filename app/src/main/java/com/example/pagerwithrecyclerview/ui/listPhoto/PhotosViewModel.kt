package com.example.pagerwithrecyclerview.ui.listPhoto

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pagerwithrecyclerview.response.Photo
import com.example.pagerwithrecyclerview.unsplash.Config
import com.example.pagerwithrecyclerview.unsplash.UnsplashApi
import com.example.pagerwithrecyclerview.unsplash.UnsplashClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosViewModel : ViewModel() {
    private var dataService: UnsplashApi =
        UnsplashClient.getUnsplashClient()!!.create(UnsplashApi::class.java)
    private var page = 1
    private var perPage = 10
    val listPhoto by lazy { MutableLiveData<MutableList<Photo>>() }

    init {
        listPhoto.value = mutableListOf()
    }

    fun getListPhoto() {
        dataService.getPhotos(Config.unsplash_access_key, null, null).enqueue(object : Callback<MutableList<Photo>> {
            override fun onResponse(
                call: Call<MutableList<Photo>>,
                response: Response<MutableList<Photo>>
            ) {
                listPhoto.postValue(response.body())
            }

            override fun onFailure(call: Call<MutableList<Photo>>, t: Throwable) {
                Log.wtf("error", "${t.message}")
            }

        })
    }
}