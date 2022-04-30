package com.bizmiz.adepuz.helper.api

import com.bizmiz.adepuz.model.*
import com.bizmiz.adepuz.model.location_model.Result
import retrofit2.Call
import retrofit2.http.*

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

    @GET("api/{pathName}")
    fun getPosts(
        @Path("pathName") pathName: String
    ): Call<List<PostsData>>

    @GET
    fun getDistrict(@Url url: String): Call<Result>


    @FormUrlEncoded
    @PUT("api/post/update/{id}")
    fun updateView(
        @Path("id") id: Int,
        @Field("views") views: String
    ): Call<PutResponse>
}