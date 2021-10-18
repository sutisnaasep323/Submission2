package com.example.submission2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission2.R
import com.example.submission2.adapter.ViewPagerAdapter
import com.example.submission2.databinding.ActivityDetailUserBinding
import com.example.submission2.ui.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        showLoading(true)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        if (username != null) {
            showLoading(false)
            viewModel.setDetailUser(username)
        }

        configDetailViewModel()

        val viewPagerAdapter = ViewPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = findViewById(R.id.viewpager)
        val tabs: TabLayout = findViewById(R.id.tabs)

        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabs,viewPager) {tab, position ->
            tab.text = resources.getString(viewPagerAdapter.tabTitles[position])
        }.attach()

    }

    private fun configDetailViewModel() {
        viewModel.getDetailUser().observe(this, {
            if (it != null) {
                binding.apply {
                    tvUsername.text = it.login
                    tvName.text = it.name
                    tvLocation.text = it.location
                    tvCompany.text = it.company
                    tvFollowersData.text = it.followers.toString()
                    tvFollowingData.text = it.following.toString()
                    tvRepositoryData.text = it.public_repos.toString()
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .apply(RequestOptions())
                        .into(avatarProfile)
                }
            }
        })
    }

    private fun showLoading(condition: Boolean) {
        binding.progressBar.visibility = if (condition) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

}