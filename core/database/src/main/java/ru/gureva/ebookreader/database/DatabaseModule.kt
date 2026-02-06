package ru.gureva.ebookreader.database

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "database"
        ).build()
    }

    single { get<AppDatabase>().bookDao() }

    single { DateConverter() }
}
