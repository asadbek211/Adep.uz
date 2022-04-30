package com.bizmiz.adepuz.helper.api

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.util.Log
import com.bizmiz.adepuz.helper.db.PostsDatabase
import com.bizmiz.adepuz.model.*
import com.bizmiz.adepuz.model.location_model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class NetworkHelper(
    private val apiClient: Retrofit,
    private val postsDatabase: PostsDatabase,
    private val context: Context
) {
    fun getArticle(
        pathName: String,
        onSuccess: (posts: List<ArticlesData>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getArticles(pathName)
        call.enqueue(object : Callback<List<ArticlesData>> {
            override fun onResponse(
                call: Call<List<ArticlesData>>,
                response: Response<List<ArticlesData>>
            ) {
                CoroutineScope(Dispatchers.IO).launch {
                    response.body()?.let { postsDatabase.postsDao().getArticleInsert(it) }
                    withContext(Dispatchers.Main) {
                        val article = response.body()?.sortedByDescending {
                            it.created_at
                        }
                        if (article != null) {
                            onSuccess.invoke(article)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<ArticlesData>>?, t: Throwable?) {
                if (networkCheck()) {
                    onFailure.invoke(t?.localizedMessage)
                }
            }

        })
        CoroutineScope(Dispatchers.IO).launch {
            val article = postsDatabase.postsDao().getArticles().sortedByDescending {
                it.created_at
            }
            withContext(Dispatchers.Main) {
                onSuccess.invoke(article)
            }
        }
    }

    fun getNews(
        pathName: String,
        onSuccess: (posts: List<NewsData>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getNews(pathName)
        call.enqueue(object : Callback<List<NewsData>> {
            override fun onResponse(
                call: Call<List<NewsData>>,
                response: Response<List<NewsData>>
            ) {
                CoroutineScope(Dispatchers.IO).launch {
                    response.body()?.let { postsDatabase.postsDao().getNewsInsert(it) }
                    withContext(Dispatchers.Main) {
                        val news = response.body()?.sortedByDescending {
                            it.created_at
                        }
                        if (news != null) {
                            onSuccess.invoke(news)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<NewsData>>?, t: Throwable?) {
                if (networkCheck()) {
                    onFailure.invoke(t?.localizedMessage)
                }
            }
        })
        CoroutineScope(Dispatchers.IO).launch {
            val news = postsDatabase.postsDao().getNews().sortedByDescending {
                it.created_at
            }
            withContext(Dispatchers.Main) {
                onSuccess.invoke(news)
            }
        }
    }

    fun getUseful(
        pathName: String,
        onSuccess: (posts: List<UsefulData>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getUseful(pathName)
        call.enqueue(object : Callback<List<UsefulData>> {
            override fun onResponse(
                call: Call<List<UsefulData>>,
                response: Response<List<UsefulData>>
            ) {
                CoroutineScope(Dispatchers.IO).launch {
                    response.body()?.let { postsDatabase.postsDao().getUsefulInsert(it) }
                    withContext(Dispatchers.Main) {
                        val useful = response.body()?.sortedByDescending {
                            it.created_at
                        }
                        if (useful != null) {
                            onSuccess.invoke(useful)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<UsefulData>>?, t: Throwable?) {
                if (networkCheck()) {
                    onFailure.invoke(t?.localizedMessage)
                }
            }

        })
        CoroutineScope(Dispatchers.IO).launch {
            val useful = postsDatabase.postsDao().getUseful().sortedByDescending {
                it.created_at
            }
            withContext(Dispatchers.Main) {
                onSuccess.invoke(useful)
            }
        }
    }

    fun getPosts(
        pathName: String,
        prefs: SharedPreferences,
        onSuccess: (posts: List<PostsData>) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getPosts(pathName)
        call.enqueue(object : Callback<List<PostsData>> {
            override fun onResponse(
                call: Call<List<PostsData>>,
                response: Response<List<PostsData>>
            ) {
                CoroutineScope(Dispatchers.IO).launch {
                    response.body()?.let { postsDatabase.postsDao().getPostsInsert(it) }
                    withContext(Dispatchers.Main) {
                        val posts: ArrayList<PostsData> = arrayListOf()
                        response.body()?.forEach {
                            if (prefs.contains(it.id.toString())) {
                                posts.add(it)
                            }
                        }
                        val list = posts.sortedByDescending {
                            it.created_at
                        }
                        Log.d("post", posts.toString())
                        if (list != null) {
                            onSuccess.invoke(list)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<PostsData>>?, t: Throwable?) {
                if (networkCheck()) {
                    onFailure.invoke(t?.localizedMessage)
                }
            }

        })
        CoroutineScope(Dispatchers.IO).launch {
            val posts: ArrayList<PostsData> = arrayListOf()
            postsDatabase.postsDao().getPosts().forEach {
                if (prefs.contains(it.id.toString())) {
                    posts.add(it)
                }
            }

            val list = posts.sortedByDescending {
                it.created_at
            }
            withContext(Dispatchers.Main) {
                onSuccess.invoke(list)
            }
        }
    }

    fun getDistrict(
        url: String, onSuccess: (district: Response<Result>?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getDistrict(url)
        call.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>?, response: Response<Result>?) {
                onSuccess.invoke(response)
            }

            override fun onFailure(call: Call<Result>?, t: Throwable?) {
                if (networkCheck()) {
                    onFailure.invoke(t?.localizedMessage)
                }
            }

        })
    }

    fun updateView(
        id: Int,
        views: String,
        onSuccess: (result: String) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).updateView(id,views)
        call.enqueue(object : Callback<PutResponse> {
            override fun onResponse(call: Call<PutResponse>?, response: Response<PutResponse>?) {
                Log.d("results",response.toString())
                if (response != null) {
                    Log.d("results",response.body().toString())
                }
                if (response != null) {
                    response.body()?.let { onSuccess.invoke(it.result) }
                }
            }

            override fun onFailure(call: Call<PutResponse>?, t: Throwable?) {
                if (networkCheck()) {
                    onFailure.invoke(t?.localizedMessage)
                }
            }

        })
    }

    private fun networkCheck(): Boolean {
        val conManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val internetInfo = conManager.activeNetworkInfo
        return internetInfo != null && internetInfo.isConnected
    }
}