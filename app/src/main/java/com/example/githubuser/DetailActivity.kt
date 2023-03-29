package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.api.response.ItemsItem
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        const val USERNAME = "username"
        @StringRes
        private val TAB_TITTLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.extras?.get(USERNAME) as String

        detailViewModel.detailUser.observe(this@DetailActivity) {
            getDetailUser(it)
        }
        detailViewModel.isLoading.observe(this@DetailActivity) {
            showLoading(it)
        }
        detailViewModel.getDetail(data)

        //tab layout
        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        sectionsPagerAdapter.username = data

        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITTLES[position])
        }.attach()


    }

    private fun getDetailUser(itemUser: ItemsItem?) {
        binding.apply {
            tvUsernameDetail.text = itemUser?.login
            tvNamaDetail.text = itemUser?.name
            tvReposDetail.text = itemUser?.publicRepos.toString()
            tvFollowersDetail.text = itemUser?.followers.toString()
            tvFollowingDetail.text = itemUser?.following.toString()
            Glide.with(this@DetailActivity)
                .load(itemUser?.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(ivFotoDetail)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}