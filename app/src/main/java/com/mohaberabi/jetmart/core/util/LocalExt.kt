package com.mohaberabi.jetmart.core.util

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale


//fun Context.changeAppLocal(localeTag: String) {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//        getSystemService(LocaleManager::class.java).applicationLocales =
//            LocaleList.forLanguageTags(localeTag)
//    } else {
//        AppCompatDelegate.setApplicationLocales(
//            LocaleListCompat.forLanguageTags(localeTag)
//        )
//    }
//}


val Context.currentLocal: Locale
    get() {
        val currentAppLocales: LocaleListCompat =
            LocaleListCompat.getDefault()
        return if (currentAppLocales.isEmpty) Locale.getDefault() else currentAppLocales[0]
            ?: Locale.getDefault()
    }

//@Suppress("DEPRECATION")
//private val Context.currentLocal: Locale
//    get() {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            val currentAppLocales: LocaleList =
//                applicationContext.getSystemService(LocaleManager::class.java)
//                    .getApplicationLocales(packageName)
//            if (currentAppLocales.isEmpty) Locale("en") else currentAppLocales[0]
//        } else {
//            resources.configuration.locale
//        }
//
//    }


val Context.currentLanguage: String
    get() = currentLocal.language