package ru.gureva.ebookreader

import android.app.Application
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import ru.gureva.ebookreader.core.network.networkModule
import ru.gureva.ebookreader.core.util.utilModule
import ru.gureva.ebookreader.database.databaseModule
import ru.gureva.ebookreader.feature.auth.authModule
import ru.gureva.ebookreader.feature.booklist.bookListModule
import ru.gureva.ebookreader.feature.bookupload.bookUploadModule
import ru.gureva.ebookreader.feature.profile.profileModule
import ru.gureva.ebookreader.feature.reader.readerModule

class MainApplication : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()

        PDFBoxResourceLoader.init(applicationContext)

        startKoin {
            androidContext(this@MainApplication)
            workManagerFactory()
            modules(
                authModule,
                utilModule,
                bookUploadModule,
                networkModule,
                bookListModule,
                readerModule,
                profileModule,
                databaseModule
            )
        }
    }
}
