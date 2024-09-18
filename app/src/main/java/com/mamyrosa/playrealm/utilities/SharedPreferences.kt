package com.mamyrosa.playrealm.utilities

import android.content.SharedPreferences


/**
 * edit preferences
 */
private inline fun SharedPreferences.edit(operation: Closure<SharedPreferences.Editor>) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}

/**
 * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
 */
operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
    }
}

/**
 * finds value on given key.
 * [T] is the type of value
 * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
 */
inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? =
    when (T::class) {
        java.lang.String::class -> getString(key, defaultValue as? String) as T?
        java.lang.Integer::class -> getInt(key, defaultValue as? Int ?: -1) as T?
        java.lang.Boolean::class -> getBoolean(key, defaultValue as? Boolean == true) as T?
        java.lang.Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
        java.lang.Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?

        else -> throw UnsupportedOperationException("Not yet implemented")
    }