package com.l3ios.androidpagingwithcoroutines.api

import com.l3ios.androidpagingwithcoroutines.models.RedditApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/r/aww/hot.json")
    suspend fun fetchPosts(
        @Query("limit") loadSize: Int = 30,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ) : Response<RedditApiResponse>

}