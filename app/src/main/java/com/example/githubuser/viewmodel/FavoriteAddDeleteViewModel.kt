package com.example.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.FavoriteRepository
import com.example.githubuser.database.Favorite

class FavoriteAddDeleteViewModel(application: Application) : ViewModel() {

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getFavoritedUser(username: String) = mFavoriteRepository.getFavoriteUser(username)

    fun insertFavoriteUser(user: Favorite) {
        mFavoriteRepository.insertFavoriteUser(user)
    }

    fun deleteFavoriteUser(user: Favorite) {
        mFavoriteRepository.deleteFavoriteUser(user)
    }
}