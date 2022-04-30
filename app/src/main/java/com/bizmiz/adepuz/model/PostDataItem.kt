package com.bizmiz.adepuz.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticlesData(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val category_id: String,
    val created_at: String,
    val description: String,
    val image: String,
    val published_at: String,
    val slug: String,
    val title: String,
    val updated_at: String,
    val user_id: String,
    val views: String
)
@Entity(tableName = "news")
data class NewsData(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val category_id: String,
    val created_at: String,
    val description: String,
    val image: String,
    val published_at: String,
    val slug: String,
    val title: String,
    val updated_at: String,
    val user_id: String,
    val views: String
)
@Entity(tableName = "useful")
data class UsefulData(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val category_id: String,
    val created_at: String,
    val description: String,
    val image: String,
    val published_at: String,
    val slug: String,
    val title: String,
    val updated_at: String,
    val user_id: String,
    val views: String
)
@Entity(tableName = "posts")
data class PostsData(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val category_id: String,
    val created_at: String,
    val description: String,
    val image: String,
    val published_at: String,
    val slug: String,
    val title: String,
    val updated_at: String,
    val user_id: String,
    val views: String
)