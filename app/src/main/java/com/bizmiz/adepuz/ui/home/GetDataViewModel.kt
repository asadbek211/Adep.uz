package com.bizmiz.adepuz.ui.home

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.adepuz.helper.api.NetworkHelper
import com.bizmiz.adepuz.model.*
import com.bizmiz.adepuz.model.location_model.Result
import com.bizmiz.adepuz.utils.Resource
import retrofit2.Response

class GetDataViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val setDistrict: MutableLiveData<Resource<Response<Result>?>> = MutableLiveData()
    val district: LiveData<Resource<Response<Result>?>>
        get() = setDistrict
    private val getArticles: MutableLiveData<Resource<List<ArticlesData>?>> = MutableLiveData()
    val articles: LiveData<Resource<List<ArticlesData>?>>
        get() = getArticles
    private val getNews: MutableLiveData<Resource<List<NewsData>?>> = MutableLiveData()
    val news: LiveData<Resource<List<NewsData>?>>
        get() = getNews
    private val getUsefulPosts: MutableLiveData<Resource<List<UsefulData>?>> = MutableLiveData()
    val usefulPosts: LiveData<Resource<List<UsefulData>?>>
        get() = getUsefulPosts
    private val getPosts: MutableLiveData<Resource<List<PostsData>?>> = MutableLiveData()
    val posts: LiveData<Resource<List<PostsData>?>>
        get() = getPosts
    private val updateResult: MutableLiveData<Resource<String>> = MutableLiveData()
    val updateView: LiveData<Resource<String>>
        get() = updateResult

    fun getArticles() {
        networkHelper.getArticle("articles", {
            getArticles.value = Resource.success(it)
        }, {
            getArticles.value = Resource.error(it)
        })
    }

    fun getNews() {
        networkHelper.getNews("news", {
            getNews.value = Resource.success(it)
        }, {
            getNews.value = Resource.error(it)
        })
    }

    fun getUsefulPosts() {
        networkHelper.getUseful("paydali", {
            getUsefulPosts.value = Resource.success(it)
        }, {
            getUsefulPosts.value = Resource.error(it)
        })
    }
    fun getPosts(
        prefs: SharedPreferences
    ) {
        networkHelper.getPosts("posts",prefs, {
            getPosts.value = Resource.success(it)
        }, {
            getPosts.value = Resource.error(it)
        })
    }
    fun getDistrict(url: String) {
        networkHelper.getDistrict(url, {
            setDistrict.value = Resource.success(it)
        }, {
            setDistrict.value = Resource.error(it)
        })
    }
    fun updateView(id: Int,views: String) {
        networkHelper.updateView(id,views, {
            updateResult.value = Resource.success(it)
        }, {
            updateResult.value = Resource.error(it)
        })
    }
}