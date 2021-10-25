package com.example.submission2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.adapter.UserAdapter
import com.example.submission2.data.FavoriteUser
import com.example.submission2.databinding.ActivityFavoriteBinding
import com.example.submission2.model.UserItem
import com.example.submission2.ui.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        viewModel.getFavoriteUser()?.observe(this, {
            if (it != null) {
                val list = mapList(it)
                adapter.setUser(list)
            }
        })

        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserItem) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, user.login)
                    it.putExtra(DetailUserActivity.EXTRA_URL, user.url)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR_URL, user.avatar_url)
                    it.putExtra(DetailUserActivity.EXTRA_ID, user.id)
                    startActivity(it)
                }
            }
        })

    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<UserItem> {
        val listUsers = ArrayList<UserItem>()
        for (user in users) {
            val userMapped = UserItem(
                user.login,
                user.id,
                user.url,
                user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }

}