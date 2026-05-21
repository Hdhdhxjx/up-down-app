package com.updown.app.util

import android.content.Context
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.updown.app.R

object ThemeManager {
    private const val PREFS = "up_down_prefs"
    private const val KEY_ACTIVE_ACCENT = "active_accent_color"

    val accentPalette = listOf(
        R.color.color_accent,
        R.color.color_accent_blue,
        R.color.color_accent_green,
        R.color.color_accent_purple,
        R.color.color_accent_red
    )

    @ColorInt
    fun activeAccent(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val fallback = ContextCompat.getColor(context, R.color.color_accent)
        return prefs.getInt(KEY_ACTIVE_ACCENT, fallback)
    }

    fun saveAccent(context: Context, @ColorInt color: Int) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_ACTIVE_ACCENT, color)
            .apply()
    }
}
