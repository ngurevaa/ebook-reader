package ru.gureva.ebookreader

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.gureva.ebookreader.core.network.networkModule
import ru.gureva.ebookreader.core.util.utilModule
import ru.gureva.ebookreader.feature.auth.authModule
import ru.gureva.ebookreader.feature.bookupload.bookUploadModule

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(
                authModule,
                utilModule,
                bookUploadModule,
                networkModule
            )
        }
    }
}
