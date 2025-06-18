package com.dev.randomcityapp.worker


import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.work.*
import com.dev.randomcityapp.R

class WelcomeWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val city = inputData.getString(applicationContext.getString(R.string.city)) ?: return Result.failure()
        val message = applicationContext.getString(R.string.welcome_message, city)
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
        }
        return Result.success()
    }
}

