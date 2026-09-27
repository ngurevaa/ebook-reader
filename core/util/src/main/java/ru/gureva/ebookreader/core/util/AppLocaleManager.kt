package ru.gureva.ebookreader.core.util

import android.app.LocaleManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

class AppLocaleManager(
    private val context: Context,
) {

    private companion object {
        const val PREFS_NAME = "app_locale"
        const val LANGUAGE_KEY = "language"
    }

    private val preferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setLocale(language: AppLanguage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val localeManager = context.getSystemService(LocaleManager::class.java)

            localeManager.applicationLocales =
                LocaleList.forLanguageTags(language.languageTag)
        } else {
            preferences.edit()
                .putString(LANGUAGE_KEY, language.languageTag)
                .apply()
        }
    }

    fun getCurrentLanguage(): AppLanguage {
        val languageTag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val localeManager = context.getSystemService(LocaleManager::class.java)

            localeManager.applicationLocales
                .get(0)
                ?.language
        } else {
            preferences.getString(LANGUAGE_KEY, null)
                ?: Locale.getDefault().language
        }

        return AppLanguage.entries.firstOrNull {
            it.languageTag == languageTag
        } ?: AppLanguage.ENGLISH
    }

    fun wrap(context: Context): Context {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return context
        }

        val languageTag = preferences.getString(LANGUAGE_KEY, null)
            ?: return context

        val locale = Locale.forLanguageTag(languageTag)

        val configuration = Configuration(context.resources.configuration).apply {
            setLocale(locale)
        }

        return context.createConfigurationContext(configuration)
    }
}