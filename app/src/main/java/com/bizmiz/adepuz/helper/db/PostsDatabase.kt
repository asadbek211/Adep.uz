package com.bizmiz.adepuz.helper.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bizmiz.adepuz.helper.PostsDao
import com.bizmiz.adepuz.model.ArticlesData
import com.bizmiz.adepuz.model.NewsData
import com.bizmiz.adepuz.model.UsefulData

@Database(entities = [ArticlesData::class, NewsData::class, UsefulData::class], version = 1)
abstract class PostsDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao

    companion object {
        private var INSTANCE: PostsDatabase? = null
        fun initDatabase(context: Context): PostsDatabase {
            synchronized(PostsDatabase::class) {
                INSTANCE =
                    Room.databaseBuilder(context, PostsDatabase::class.java, "postsDb").build()
            }
            return INSTANCE!!
        }
    }
}