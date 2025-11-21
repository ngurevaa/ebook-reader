package ru.gureva.ebookreader.core.network

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import org.koin.dsl.module

val networkModule = module {
    single<SupabaseClient> {
        createSupabaseClient(
            supabaseUrl = "https://wmrcebjhomxvievmvljp.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndtcmNlYmpob214dmlldm12bGpwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjM2NjkyODMsImV4cCI6MjA3OTI0NTI4M30.ASRjgCmKPMwiSn1RgBeoqGuIS8lBVO3wxmoMdOpLjYs"
        ) {
            install(Storage)
        }
    }

    single<Storage> {
        get<SupabaseClient>().storage
    }
}
