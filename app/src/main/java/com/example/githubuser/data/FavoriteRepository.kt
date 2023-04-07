package com.example.githubuser.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.database.Favorite
import com.example.githubuser.database.FavoriteDao
import com.example.githubuser.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }
    fun getAllFavoriteUser(): LiveData<List<Favorite>> = mFavoriteDao.getAllFavUser()


    fun getFavoriteUser(username: String): LiveData<List<Favorite>> = mFavoriteDao.getFavoriteUSer(username)

    fun insertFavoriteUser(favorite: Favorite) {
        executorService.execute {
            mFavoriteDao.insert(favorite)
        }
    }

    fun deleteFavoriteUser(favorite: Favorite) {
        executorService.execute {
            mFavoriteDao.delete(favorite)
        }
    }
}