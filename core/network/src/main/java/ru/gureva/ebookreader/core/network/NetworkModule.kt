package ru.gureva.ebookreader.core.network

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import org.koin.dsl.module
import kotlin.time.Duration.Companion.seconds

val networkModule = module {
    single<SupabaseClient> {
        createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_KEY
        ) {
            requestTimeout = 20.seconds
            install(Storage)
        }
    }
}
