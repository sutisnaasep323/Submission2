package com.example.submission2

import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.model.GitItem
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel : ViewModel() {
    companion object{
        private val TAG = MainViewModel::class.java.simpleName
    }

    val listUsers = MutableLiveData<ArrayList<GitItem>>()

    fun setUser(query: String, context: Context) {

        val listItems = ArrayList<GitItem>()

        val url = "https://api.github.com/search/users?q=$query"

        _isLoading.value = true
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                _isLoading.value = false
                val result = String(responseBody)
                Log.d(TAG, result)
                try {

                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("items")

                    for (i in 0 until list.length()) {
                        val users = list.getJSONObject(i)
                        Log.d(TAG, users.toString())
                        val userItems = GitItem()

                        userItems.username = users.getString("login")
                        userItems.url = users.getString("url")
                        userItems.avatar = users.getString("avatar_url")

                        listItems.add(userItems)

                    }

                    listUsers.postValue(listItems)

                } catch (e: Exception) {
                    Toast.makeText(context, "Unable to Connect", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()

            }
        })

    }

    fun getUser(): LiveData<ArrayList<GitItem>> {
        return listUsers
    }

}