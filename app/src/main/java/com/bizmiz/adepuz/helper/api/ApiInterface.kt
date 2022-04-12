package com.bizmiz.adepuz.helper.api

import com.bizmiz.adepuz.model.ArticlesData
import com.bizmiz.adepuz.model.NewsData
import com.bizmiz.adepuz.model.UsefulData
import com.bizmiz.adepuz.model.location_model.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiInterface {
    @GET("api/{pathName}")
    fun getArticles(
        @Path("pathName") pathName: String
    ): Call<List<ArticlesData>>

    @GET("api/{pathName}")
    fun getNews(
        @Path("pathName") pathName: String
    ): Call<List<NewsData>>

    @GET("api/{pathName}")
    fun getUseful(
        @Path("pathName") pathName: String
    ): Call<List<UsefulData>>

    @GET
    fun getDistrict(@Url url: String): Call<Result>
}