package com.example.submission2.ui

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.R
import com.example.submission2.adapter.UserAdapter
import com.example.submission2.databinding.ActivityMainBinding
import com.example.submission2.model.GitItem
import com.example.submission2.model.UserItem
import com.example.submission2.viewmodel.MainViewModel
import com.example.submission2.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : UserAdapter
    private lateinit var viewModel: UserViewModel
    private lateinit var tempArrayList: ArrayList<UserItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        showSearch(true)
        showRecyclerView()
        setMainModel()
    }

    private fun setMainModel() {
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
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
                if (query.isEmpty()) {
                    return true
                } else {
                    showSearch(false)
                    showLoading(true)
                    viewModel.setUser(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun showRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

//        adapter.setOnItemClickCallback(object : OnItemClickCallback {
//            override fun onIemClicked(userItems: UserItem) {
//                Toast.makeText(this@MainActivity, "Klik RecylerView", Toast.LENGTH_LONG).show()
//            }
//        })
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