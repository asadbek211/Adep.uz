package com.bizmiz.adepuz.helper

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bizmiz.adepuz.model.ArticlesData
import com.bizmiz.adepuz.model.NewsData
import com.bizmiz.adepuz.model.PostsData
import com.bizmiz.adepuz.model.UsefulData

@Dao
interface PostsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun getArticleInsert(articlesList: List<ArticlesData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun getNewsInsert(newsList: List<NewsData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun getUsefulInsert(usefulList: List<UsefulData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun getPostsInsert(posts: List<PostsData>)

    @Query("select * from articles")
    fun getArticles(): List<ArticlesData>

    @Query("select * from news")
    fun getNews(): List<NewsData>

    @Query("select * from useful")
    fun getUseful(): List<UsefulData>

    @Query("select * from posts")
    fun getPosts(): List<PostsData>
}