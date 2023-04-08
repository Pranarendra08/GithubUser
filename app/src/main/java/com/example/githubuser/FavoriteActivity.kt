package com.example.githubuser

import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.FavoriteAdapter
import com.example.githubuser.api.response.ItemsItem
import com.example.githubuser.database.Favorite
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.helper.ViewModelFactory
import com.example.githubuser.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "Favorite User"

        val favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getAllFavorites().observe(this) {
            setListFavoriteData(it)

        }
    }

    private fun setListFavoriteData(dataUser: List<Favorite>) {
        val items = arrayListOf<Favorite>()
        dataUser.map {
            val item = Favorite(it.username ,it.avatarUrl)
            items.add(item)
        }
        val adapter = FavoriteAdapter(items)
        binding?.rvFavoriteUser?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavoriteUser?.setHasFixedSize(true)
        binding?.rvFavoriteUser?.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Favorite) {
                startActivity(
                    Intent(this@FavoriteActivity, DetailActivity::class.java)
                        .putExtra(DetailActivity.USERNAME, data.username)
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