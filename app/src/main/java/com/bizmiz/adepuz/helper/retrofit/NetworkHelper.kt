package com.bizmiz.adepuz.helper.retrofit

import com.bizmiz.adepuz.model.PostData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class NetworkHelper(private val apiClient: Retrofit) {
    fun getPosts(
        pathName: String,
        onSuccess: (posts: Response<PostData>?) -> Unit,
        onFailure: (msg: String?) -> Unit
    ) {
        val call = apiClient.create(ApiInterface::class.java).getPosts(pathName)
        call.enqueue(object : Callback<PostData> {
            override fun onResponse(call: Call<PostData>?, response: Response<PostData>?) {
                onSuccess.invoke(response)
            }

            override fun onFailure(call: Call<PostData>?, t: Throwable?) {
                onFailure.invoke(t?.localizedMessage)
            }

        })
    }
}