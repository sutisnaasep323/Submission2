package com.example.submission2.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.R
import com.example.submission2.adapter.UserAdapter
import com.example.submission2.databinding.ActivityMainBinding
import com.example.submission2.model.UserItem
import com.example.submission2.ui.viewmodel.UserViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()

        showRecyclerView()
        setMainModel()
        showSearch(true)
    }

    private fun setMainModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)

        viewModel.showLoading.observe(this, {
            showLoading(it)
        })

        viewModel.getSearchUser().observe(this, {
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
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (query.isEmpty()){
                    showSearch(true)
                    binding.recyclerView.visibility = View.GONE
                } else {
                    showSearch(false)
                    showLoading(true)
                    setMainModel()
                    viewModel.setUser(query, this@MainActivity)
                    binding.recyclerView.visibility = View.VISIBLE
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun showRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserItem) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, user.login)
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
        binding.progressBar.visibility = if (condition) View.VISIBLE else View.GONE
    }
}