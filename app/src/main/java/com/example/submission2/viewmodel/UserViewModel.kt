package com.example.submission2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.api.RetrofitClient
import com.example.submission2.model.UserItem
import com.example.submission2.model.UserResponse
import com.example.submission2.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {

    private val _isShowImage = MutableLiveData<Boolean>()
    val showImage: LiveData<Boolean> = _isShowImage

    val listUser = MutableLiveData<ArrayList<UserItem>>()

    fun setUser (query: String){
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object: Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if(response.isSuccessful){
                        listUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getSearchUser(): LiveData<ArrayList<UserItem>>{
        return listUser
    }

}