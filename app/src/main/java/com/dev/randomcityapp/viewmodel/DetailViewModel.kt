package com.dev.randomcityapp.viewmodel


import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.dev.randomcityapp.R
import com.dev.randomcityapp.data.local.AppDatabase
import com.dev.randomcityapp.data.model.Emission
import com.dev.randomcityapp.data.repository.EmissionRepository
import com.dev.randomcityapp.worker.WelcomeWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = EmissionRepository(AppDatabase.getInstance(application).emissionDao())
    private val workManager = WorkManager.getInstance(application)
    private val _emission = MutableLiveData<Emission?>()
    val emission: LiveData<Emission?> = _emission

    fun loadEmission(id: Long) {
        viewModelScope.launch {
            _emission.value = repository.getEmissionById(id)
        }
    }

    fun enqueueWelcomeWorker(city: String) {
        val context = getApplication<Application>().applicationContext
        val request = OneTimeWorkRequestBuilder<WelcomeWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setInputData(workDataOf(context.getString(R.string.city) to city))
            .build()

        workManager.enqueue(request)
    }
}
