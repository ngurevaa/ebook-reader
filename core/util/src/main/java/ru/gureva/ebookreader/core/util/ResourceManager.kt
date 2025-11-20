package ru.gureva.ebookreader.core.util

import android.content.Context
import androidx.annotation.StringRes

class ResourceManager(
    private val context: Context
) {
    fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }

    fun getString(@StringRes id: Int, vararg args: Any): String {
        return context.getString(id, *args)
    }
}
