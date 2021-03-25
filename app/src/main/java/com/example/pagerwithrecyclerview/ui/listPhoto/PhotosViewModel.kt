package com.example.pagerwithrecyclerview.ui.listPhoto

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pagerwithrecyclerview.response.PhotoResponse
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
    val listPhoto by lazy { MutableLiveData<MutableList<PhotoResponse>>() }
    val loadMoreListPhoto by lazy { MutableLiveData<MutableList<PhotoResponse>>() }

    init {
        listPhoto.value = mutableListOf()
    }

    fun getListPhoto() {
        dataService.getPhotos(Config.unsplash_access_key, null, null).enqueue(object : Callback<MutableList<PhotoResponse>> {
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

    fun loadMoreImage(){
        page++
        dataService.getPhotos(Config.unsplash_access_key, page, null).enqueue(object : Callback<MutableList<PhotoResponse>> {
            override fun onResponse(
                call: Call<MutableList<PhotoResponse>>,
                response: Response<MutableList<PhotoResponse>>
            ) {
                loadMoreListPhoto.postValue(response.body())
            }

            override fun onFailure(call: Call<MutableList<PhotoResponse>>, t: Throwable) {
                Log.wtf("error", "${t.message}")
            }

        })
    }
}