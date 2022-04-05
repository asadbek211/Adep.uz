package com.bizmiz.adepuz.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.adepuz.helper.retrofit.NetworkHelper
import com.bizmiz.adepuz.model.PostData
import com.bizmiz.adepuz.utils.Resource
import retrofit2.Response

class GetDataViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val getArticles: MutableLiveData<Resource<Response<PostData>?>> = MutableLiveData()
    val articles: LiveData<Resource<Response<PostData>?>>
        get() = getArticles
    private val getNews: MutableLiveData<Resource<Response<PostData>?>> = MutableLiveData()
    val news: LiveData<Resource<Response<PostData>?>>
        get() = getNews
    fun getArticles() {
        networkHelper.getPosts("articles",{
            getArticles.value = Resource.success(it)
        }, {
            getArticles.value = Resource.error(it)
        })
    }
    fun getNews() {
        networkHelper.getPosts("news",{
            getNews.value = Resource.success(it)
        }, {
            getNews.value = Resource.error(it)
        })
    }
}