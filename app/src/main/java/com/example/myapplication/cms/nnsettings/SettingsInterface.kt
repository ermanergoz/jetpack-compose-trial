package com.kole.myapplication.cms.nnsettings

import androidx.annotation.ColorInt

interface SettingsInterface {
    fun string(key: String, default: String = DefaultValues.DEFAULT_STRING, subs: Map<String, String> = emptyMap()): String
    fun url(key: String, default: String = DefaultValues.DEFAULT_URL, subs: Map<String, String> = emptyMap()): String
    fun integer(key: String, default: Int = DefaultValues.DEFAULT_INT): Int
    fun long(key: String, default: Int = DefaultValues.DEFAULT_INT): Long
    fun float(key: String, default: Float = DefaultValues.DEFAULT_FLOAT): Float
    fun boolean(key: String, default: Boolean = DefaultValues.DEFAULT_BOOL): Boolean
    fun colour(key: String, default: String = DefaultValues.DEFAULT_COLOUR): String

    @ColorInt
    fun colourInt(key: String, default: Int = DefaultValues.DEFAULT_COLOUR_INT): Int
    fun plural(key: String, count: Int): String
    fun apiCollection(apiKey: String): NetworkCollection
    fun <K, V> map(key: String, subs: Map<String, String> = emptyMap(), default: Map<K, V> = emptyMap(), keyClass: Class<K>, valueClass: Class<V>): Map<K, V>
    fun <T> list(key: String, default: List<T> = emptyList(), typeClass: Class<T>): List<T>
    fun <T> obj(key: String, defaultJsonString: String = "{}", typeClass: Class<T>): T?
}