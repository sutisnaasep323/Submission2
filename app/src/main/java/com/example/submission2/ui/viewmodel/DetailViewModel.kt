package com.example.submission2.ui.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.submission2.api.RetrofitClient
import com.example.submission2.data.FavoriteUser
import com.example.submission2.data.FavoriteUserDao
import com.example.submission2.data.UserDatabase
import com.example.submission2.model.DetailUserResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: FavoriteUserDao? = null
    private var userDb: UserDatabase? = null
    private val user = MutableLiveData<DetailUserResponse>()

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun setDetailUser(username: String, context: Context) {
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
                    Toast.makeText(context, "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun getDetailUser(): LiveData<DetailUserResponse> {
        return user
    }

    fun addToFavorite(username: String, url: String, avatar: String, id: Int){
        viewModelScope.launch {
            val user = FavoriteUser(
                username, url, avatar, id
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int){
        viewModelScope.launch {
            userDao?.removeFromFavorite(id)
        }
    }


}