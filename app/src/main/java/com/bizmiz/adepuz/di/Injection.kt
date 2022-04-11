package com.bizmiz.adepuz.di

import com.bizmiz.adepuz.helper.api.ApiClient
import com.bizmiz.adepuz.helper.api.NetworkHelper
import com.bizmiz.adepuz.helper.db.PostsDatabase
import com.bizmiz.adepuz.ui.home.GetDataViewModel
import com.bizmiz.adepuz.ui.home.NamazTimeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { ApiClient.getClient() }
    single { PostsDatabase.initDatabase(get()) }
    single { NetworkHelper(get(),get()) }
}
val viewModelModule = module {
    viewModel {GetDataViewModel(get())}
    viewModel {NamazTimeViewModel()}
}