package com.example.submission2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission2.adapter.ViewPagerAdapter
import com.example.submission2.databinding.ActivityDetailUserBinding
import com.example.submission2.ui.viewmodel.DetailViewModel

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

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
        viewModel.getDetailUser().observe(this, {
            if (it != null) {
                binding.apply {
                    tvUsername.text = it.login
                    tvName.text = it.name
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

        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewpager.adapter = viewPagerAdapter
            tabs.setupWithViewPager(viewpager)
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