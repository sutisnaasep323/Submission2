package com.example.submission2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission2.R
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

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        if (username != null) {
            viewModel.setDetailUser(username)
        }
        viewModel.getDetailUser().observe(this, {
            if (it != null) {
                binding.apply {
                    tvUsername.text = it.login
                    tvName.text = it.name
                    tvFollowers.text = resources.getString(R.string.followers, it.followers)
                    tvFollowing.text = resources.getString(R.string.following, it.following)
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
}