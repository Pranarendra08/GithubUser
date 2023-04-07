package com.example.githubuser.api

import com.example.githubuser.api.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q") query: String
    ) : Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<ItemsItem>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}