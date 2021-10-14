package com.example.submission2.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.api.RetrofitClient
import com.example.submission2.model.UserItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class FollowersViewModel : ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<UserItem>>()

    fun setFollowers(username: String) {
        RetrofitClient.apiInstance
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<UserItem>> {
                override fun onResponse(
                    call: Call<ArrayList<UserItem>>,
                    response: Response<ArrayList<UserItem>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserItem>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getFollowers(): LiveData<ArrayList<UserItem>> {
        return listFollowers
    }
}