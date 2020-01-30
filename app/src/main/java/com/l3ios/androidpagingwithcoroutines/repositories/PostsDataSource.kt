package com.l3ios.androidpagingwithcoroutines.repositories

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.l3ios.androidpagingwithcoroutines.api.ApiClient
import com.l3ios.androidpagingwithcoroutines.api.ApiService
import com.l3ios.androidpagingwithcoroutines.models.RedditPost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class PostsDataSource(coroutineContext: CoroutineContext) :
    PageKeyedDataSource<String, RedditPost>() {

    private val apiService = ApiClient.getClient().create(ApiService::class.java)

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, RedditPost>
    ) {
        scope.launch {
            try {
                val response = apiService.fetchPosts(loadSize = params.requestedLoadSize)
                when {
                    response.isSuccessful -> {
                        val listing = response.body()?.data
                        val redditPosts = listing?.children?.map { it.data }
                        callback.onResult(redditPosts ?: listOf(), listing?.before, listing?.after)
                    }
                }
            } catch (e: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data: ${e.localizedMessage}")
            }
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, RedditPost>) {
        scope.launch {
            try {
                val response =
                    apiService.fetchPosts(loadSize = params.requestedLoadSize, after = params.key)
                when {
                    response.isSuccessful -> {
                        val listing = response.body()?.data
                        val redditPosts = listing?.children?.map { it.data }
                        callback.onResult(redditPosts ?: listOf(), listing?.after)
                    }
                }
            } catch (e: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data: ${e.localizedMessage}")
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, RedditPost>
    ) {
        scope.launch {
            try {
                val response =
                    apiService.fetchPosts(loadSize = params.requestedLoadSize, before = params.key)
                when {
                    response.isSuccessful -> {
                        val listing = response.body()?.data
                        val redditPosts = listing?.children?.map { it.data }
                        callback.onResult(redditPosts ?: listOf(), listing?.before)
                    }
                }
            } catch (e: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data: ${e.localizedMessage}")
            }
        }
    }

}