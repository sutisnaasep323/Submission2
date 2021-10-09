package com.example.submission2

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.submission2.adapter.OnItemClickCallback
import com.example.submission2.adapter.SearchAdapter
import com.example.submission2.databinding.ActivityMainBinding
import com.example.submission2.model.GitItem

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        const val STATE_TRUE = "stateTrue"
        const val STATE_FALSE = "stateFalse"
    }

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : SearchAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showSearch(true)
        mainViewModel = MainViewModel()
        setMainModel(savedInstanceState)
        setRecyclerView()
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
                Log.d(TAG, searchView.toString())
                showLoading(true)
                showSearch(false)
//                Toast.makeText(this@MainActivity, "Show Search", Toast.LENGTH_LONG).show()
                mainViewModel.setUser(query, this@MainActivity)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_TRUE, true)
        outState.putBoolean(STATE_FALSE, false)
    }

    private fun setMainModel(savedInstanceState: Bundle?) {
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.getUser().observe(this, Observer {
            if (it != null) {
                adapter.setData(it)
                if (savedInstanceState != null) {
                    showLoading(savedInstanceState.getBoolean(STATE_FALSE))
                    showSearch(savedInstanceState.getBoolean(STATE_FALSE))
                } else {
                    showLoading(false)
                }
            }
            if (it == null) {
                if (savedInstanceState != null) {
                    showSearch(savedInstanceState.getBoolean(STATE_TRUE))
                } else {
                    showSearch(true)
                }
            }
        })

    }

    private fun setRecyclerView() {
        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

        adapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onIemClicked(userItems: GitItem) {
                Toast.makeText(this@MainActivity, "Klik RecylerView", Toast.LENGTH_LONG).show()
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