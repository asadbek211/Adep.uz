package com.bizmiz.adepuz.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.adepuz.model.PrayTimeModel

class NamazTimeViewModel : ViewModel() {

    private var namazTime: MutableLiveData<PrayTimeModel> = MutableLiveData()
    val namazTimes: LiveData<PrayTimeModel> get() = namazTime

    fun getTimes(timezone: Int, latitude: Double, longitude: Double) {
        namazTime.value = NamazTime.getPrayTime(latitude, longitude, timezone)
    }
}