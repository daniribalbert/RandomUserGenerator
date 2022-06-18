package com.randomuser

import android.app.Application
import com.randomuser.data.database.ObjectBox
import com.randomuser.di.apiModule
import com.randomuser.di.randomUserModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


class RandomUserApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@RandomUserApplication)
            modules(apiModule, randomUserModule)
        }
    }
}
