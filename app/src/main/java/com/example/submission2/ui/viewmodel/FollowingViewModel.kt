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

class FollowingViewModel: ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<UserItem>>()

    fun setFollowing (username: String){
        RetrofitClient.apiInstance
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<UserItem>> {
                override fun onResponse(
                    call: Call<ArrayList<UserItem>>,
                    response: Response<ArrayList<UserItem>>
                ) {
                    if(response.isSuccessful){
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserItem>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getFollowing(): LiveData<ArrayList<UserItem>>{
        return listFollowing
    }
}