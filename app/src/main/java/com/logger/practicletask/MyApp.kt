package com.logger.practicletask

import android.app.Application
import com.logger.practicletask.common.ConnectivityStatusProvider
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {

    @Inject lateinit var connectivityStatusProvider: ConnectivityStatusProvider

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        connectivityStatusProvider.setConsumerApp(this)
    }
}