package com.example.githubuser

import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.FavoriteAdapter
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.api.response.ItemsFavorite
import com.example.githubuser.api.response.ItemsItem
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.helper.ViewModelFactory
import com.example.githubuser.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding

    private lateinit var adapter: FavoriteAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "Favorite User"

//        mainViewModel.listGithubUser.observe(this) {
//            setListUserData(it)
//        }

        val favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getAllFavorites().observe(this) {
            val items = arrayListOf<ItemsFavorite>() //ItemsItem
            it.map {
                val item = ItemsFavorite(login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            val adapter = FavoriteAdapter(items)
            binding?.rvFavoriteUser?.layoutManager = LinearLayoutManager(this)
            binding?.rvFavoriteUser?.setHasFixedSize(true)
            binding?.rvFavoriteUser?.adapter = adapter
        }

//        favoriteViewModel = obtainViewModel(this@FavoriteActivity)
//        favoriteViewModel.getAllFavorites().observe(this) { githubUserList ->
//            if (githubUserList != null) {
//                adapter.getAllFavorites(githubUserList)
//
//            }
//        }
//        adapter = FavoriteAdapter()
//        binding?.rvFavoriteUser?.layoutManager = LinearLayoutManager(this)
//        binding?.rvFavoriteUser?.setHasFixedSize(false)
//        binding?.rvFavoriteUser?.adapter = adapter

    }

    private fun setListUserData(dataUser: List<ItemsItem>) {
        val adapter = UserAdapter(dataUser)
        binding?.apply {
            rvFavoriteUser?.setHasFixedSize(true)
            rvFavoriteUser?.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavoriteUser?.adapter = adapter
        }
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                startActivity(
                    Intent(this@FavoriteActivity, DetailActivity::class.java)
                        .putExtra(DetailActivity.USERNAME, data.login)
                        .putExtra(DetailActivity.AVATAR, data.avatarUrl)
                )
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
}