package com.bizmiz.adepuz.helper.api

import com.bizmiz.adepuz.utils.Constant
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        private lateinit var retrofit: Retrofit
        private lateinit var gson: Gson
        fun getClient(): Retrofit {
            if (!Companion::gson.isInitialized) {
              gson = GsonBuilder()
                  .setLenient()
                  .create()
            }
            if (!Companion::retrofit.isInitialized){
                val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                }
                val client: OkHttpClient = OkHttpClient.Builder().apply {
                    this.addInterceptor(interceptor)
                }.build()
                retrofit =Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit
        }
    }
}