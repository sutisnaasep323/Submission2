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
import com.example.submission2.effect.BlurTransformation
import com.example.submission2.ui.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR_URL)
        val url = intent.getStringExtra(EXTRA_URL)
        val id = intent.getIntExtra(EXTRA_ID, 0)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        showLoading(true)

        viewModel = ViewModelProvider(
            this
        ).get(DetailViewModel::class.java)
        if (username != null) {
            showLoading(false)
            viewModel.setDetailUser(username, this)
        }

        configDetailViewModel()

        val viewPagerAdapter = ViewPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = findViewById(R.id.viewpager)
        val tabs: TabLayout = findViewById(R.id.tabs)

        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(viewPagerAdapter.tabTitles[position])
        }.attach()

        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleFavorite.isChecked = true
                        isChecked = true
                    } else {
                        binding.toggleFavorite.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.toggleFavorite.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                if (username != null && url != null && avatarUrl != null) {
                    viewModel.addToFavorite(username, url, avatarUrl, id)

                }
            } else {
                viewModel.removeFromFavorite(id)
            }
            binding.toggleFavorite.isChecked = isChecked
        }

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
                        .placeholder(R.drawable.ic_load_image)
                        .error(R.drawable.ic_broken_image)
                        .into(avatarProfile)
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .placeholder(R.drawable.ic_load_image)
                        .error(R.drawable.ic_broken_image)
                        .transform(BlurTransformation(this@DetailUserActivity))
                        .into(backdrop)
                }
            }
        })
    }

    private fun showLoading(condition: Boolean) {
        binding.progressBar.visibility = if (condition) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_URL = "extra_url"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR_URL = "extra_avatar_url"
    }

}