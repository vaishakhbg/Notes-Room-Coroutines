package com.personal.roomcoroutinessample.viewmodel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VMFactory(application: Application) : ViewModelProvider.NewInstanceFactory() {

    val _application: Application=application

    @NonNull
    override fun <T : ViewModel?> create(@NonNull modelClass: Class<T>): T {
            return  MainViewModel(_application) as T
    }
}