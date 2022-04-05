package com.bizmiz.adepuz.helper.retrofit

import com.bizmiz.adepuz.model.PostData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("api/{pathName}")
    fun getPosts(
        @Path("pathName") pathName: String
    ): Call<PostData>
}