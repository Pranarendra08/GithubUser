package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.adapter.SectionsPagerAdapter
import com.example.githubuser.api.response.ItemsItem
import com.example.githubuser.database.Favorite
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.helper.ViewModelFactory
import com.example.githubuser.viewmodel.DetailViewModel
import com.example.githubuser.viewmodel.FavoriteAddDeleteViewModel
import com.example.githubuser.viewmodel.FavoriteViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private var isFavorite = false

    private lateinit var favoriteAddDeleteViewModel: FavoriteAddDeleteViewModel

    companion object {
        const val USERNAME = "username"
        const val AVATAR = "avatar"

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

        val username = intent.extras?.get(USERNAME) as String
        val avatar = intent.extras?.get(AVATAR) as String

        detailViewModel.detailUser.observe(this@DetailActivity) {
            getDetailUser(it)
        }
        detailViewModel.isLoading.observe(this@DetailActivity) {
            showLoading(it)
        }
        detailViewModel.getDetail(username)

        //tab layout
        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        sectionsPagerAdapter.username = username

        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITTLES[position])
        }.attach()

        //favorite
        favoriteAddDeleteViewModel = obtainViewModel(this@DetailActivity)

        favoriteAddDeleteViewModel.getFavoritedUser(username).observe(this) {
            isFavorite = it.isNotEmpty()
            val favoriteEntity = Favorite(username,  avatar)
            if (it.isEmpty()) {
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(binding.fabFavorite.context, R.drawable.ic_favorite_border))
            } else {
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(binding.fabFavorite.context, R.drawable.ic_favorite))
            }

            binding.fabFavorite.setOnClickListener {
                if (isFavorite) {
                    favoriteAddDeleteViewModel.deleteFavoriteUser(favoriteEntity)
                } else {
                    favoriteAddDeleteViewModel.insertFavoriteUser(favoriteEntity)
                }
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteAddDeleteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteAddDeleteViewModel::class.java)
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