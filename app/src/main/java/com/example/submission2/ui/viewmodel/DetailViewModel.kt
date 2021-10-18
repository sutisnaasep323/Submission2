package com.example.submission2.ui.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.api.RetrofitClient
import com.example.submission2.model.DetailUserResponse
import com.example.submission2.ui.DetailUserActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val user = MutableLiveData<DetailUserResponse>()

    fun setDetailUser(username: String) {
        RetrofitClient.apiInstance
            .getDetailUser(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
//                    Toast.makeText(this,"Api Failure: ${t.message}",Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun getDetailUser(): LiveData<DetailUserResponse> {
        return user
    }

}