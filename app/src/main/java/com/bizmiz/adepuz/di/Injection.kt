package com.bizmiz.adepuz.di

import com.bizmiz.adepuz.helper.retrofit.ApiClient
import com.bizmiz.adepuz.helper.retrofit.NetworkHelper
import com.bizmiz.adepuz.ui.home.GetDataViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { ApiClient.getClient() }
    single { NetworkHelper(get()) }
}
val viewModelModule = module {
    viewModel {GetDataViewModel(get())}
}