package com.example.submission2.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.R
import com.example.submission2.adapter.UserAdapter
import com.example.submission2.databinding.ActivityMainBinding
import com.example.submission2.model.UserItem
import com.example.submission2.ui.viewmodel.UserViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : UserAdapter
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        showRecyclerView()
        setMainModel()
        showSearch(true)
    }

    private fun setMainModel() {
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        viewModel.showImage.observe(this, {
            showSearch(it)
        })
        viewModel.getSearchUser().observe(this,{
            if (it != null) {
                adapter.setUser(it)
                showLoading(false)
                showSearch(false)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (query.isEmpty()){

                    setMainModel()
                    showSearch(true)
                } else {
                    showLoading(true)
                    setMainModel()
                    viewModel.setUser(query)
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun showRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(userItems: UserItem) {
                Intent(this@MainActivity,DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, userItems.login)
                    startActivity(it)
                }
            }
        })
    }

    private fun showSearch(state: Boolean) {
        if (state) {
            binding.imgSearchUser.visibility = View.VISIBLE
            binding.tvSearchUser.visibility = View.VISIBLE
        } else {
            binding.imgSearchUser.visibility = View.GONE
            binding.tvSearchUser.visibility = View.GONE
        }
    }

    private fun showLoading(condition: Boolean) {
        if (condition) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}