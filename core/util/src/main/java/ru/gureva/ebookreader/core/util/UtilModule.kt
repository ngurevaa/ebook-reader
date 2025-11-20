package ru.gureva.ebookreader.core.util

import org.koin.dsl.module

val utilModule = module {
    single { FieldValidator() }
    single { ResourceManager(get()) }
}
