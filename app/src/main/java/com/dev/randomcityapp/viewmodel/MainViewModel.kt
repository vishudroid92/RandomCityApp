package com.dev.randomcityapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.dev.randomcityapp.data.local.AppDatabase
import com.dev.randomcityapp.data.repository.EmissionRepository
import com.dev.randomcityapp.usecase.EmissionGenerator
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope

class MainViewModel(application: Application) : AndroidViewModel(application), LifecycleEventObserver {

    private val repo = EmissionRepository(AppDatabase.getInstance(application).emissionDao())

    private val emissionGenerator by lazy {
        EmissionGenerator(repo, viewModelScope)
    }

    val emissions = repo.emissions
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onCleared() {
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
        super.onCleared()
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> emissionGenerator.startGenerating()
            Lifecycle.Event.ON_STOP -> emissionGenerator.stopGenerating()
            else -> {}
        }
    }
}

