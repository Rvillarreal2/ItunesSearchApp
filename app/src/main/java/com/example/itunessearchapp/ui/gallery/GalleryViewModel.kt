package com.example.itunessearchapp.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itunessearchapp.data.ItunesList
import com.example.itunessearchapp.network.ItunesApi
import com.example.itunessearchapp.network.ItunesApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryViewModel : ViewModel() {

    var liveDataList: MutableLiveData<ItunesList> = MutableLiveData()

    fun getLiveData(): MutableLiveData<ItunesList> {
        return liveDataList
    }

    //update live data with query
    fun getSearchResults(term: String) {
        val apiInstance = ItunesApi.getItunesApi()
        val apiService = apiInstance.create(ItunesApiService::class.java)
        val call = apiService.searchMusic(term, "music")
        call.enqueue(object : Callback<ItunesList> {
            override fun onResponse(call: Call<ItunesList>, response: Response<ItunesList>) {
                liveDataList.postValue(response.body())
            }

            override fun onFailure(call: Call<ItunesList>, t: Throwable) {
                liveDataList.postValue(null)
            }
        })
    }

}
