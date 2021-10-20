package com.example.submission2.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.submission2.data.FavoriteUser
import com.example.submission2.data.FavoriteUserDao
import com.example.submission2.data.UserDatabase
import com.example.submission2.model.DetailUserResponse

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: FavoriteUserDao? = null
    private var userDb: UserDatabase? = null

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>?{
        return userDao?.getFavoriteUser()
    }
}
