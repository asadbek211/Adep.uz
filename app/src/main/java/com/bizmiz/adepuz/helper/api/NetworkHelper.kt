package com.bizmiz.adepuz.helper.api

import com.bizmiz.adepuz.helper.db.PostsDatabase
import com.bizmiz.adepuz.model.ArticlesData
import com.bizmiz.adepuz.model.NewsData
import com.bizmiz.adepuz.model.UsefulData
import com.bizmiz.adepuz.model.location_model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class NetworkHelper(private val apiClient: Retrofit, private val postsDatabase: PostsDatabase) {
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
                        val article = response.body()
                        if (article != null) {
                            onSuccess.invoke(article)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<ArticlesData>>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
        CoroutineScope(Dispatchers.IO).launch {
            val article = postsDatabase.postsDao().getArticles()
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
                        val news = response.body()
                        if (news != null) {
                            onSuccess.invoke(news)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<NewsData>>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }
        })
        CoroutineScope(Dispatchers.IO).launch {
            val news = postsDatabase.postsDao().getNews()
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
                        val useful = response.body()
                        if (useful != null) {
                            onSuccess.invoke(useful)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<UsefulData>>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
        CoroutineScope(Dispatchers.IO).launch {
            val useful = postsDatabase.postsDao().getUseful()
            withContext(Dispatchers.Main) {
                onSuccess.invoke(useful)
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
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }
}