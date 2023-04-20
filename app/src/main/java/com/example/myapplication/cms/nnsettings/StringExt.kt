package com.kole.myapplication.cms.nnsettings

import org.json.JSONObject

fun NNSettingsString(key: String, default: String = DefaultValues.DEFAULT_STRING, substitutions: Map<String, String> = emptyMap()): String = NNSettings.delegate.string(key, default, substitutions)
fun NNSettingsString(key: String, substitutions: Map<String, String>): String = NNSettings.delegate.string(key, DefaultValues.DEFAULT_STRING, substitutions)

fun NNSettingsUrl(key: String, default: String = DefaultValues.DEFAULT_URL, substitutions: Map<String, String> = emptyMap()): String = NNSettings.delegate.url(key, default, substitutions)
fun NNSettingsUrl(key: String, substitutions: Map<String, String>): String = NNSettings.delegate.url(key, DefaultValues.DEFAULT_URL, substitutions)

fun NNSettingsInt(key: String, default: Int = DefaultValues.DEFAULT_INT): Int = NNSettings.delegate.integer(key, default)

fun NNSettingsLong(key: String, default: Int = DefaultValues.DEFAULT_INT): Long = NNSettings.delegate.integer(key, default).toLong()

fun NNSettingsColour(key: String, default: String = DefaultValues.DEFAULT_COLOUR): String = NNSettings.delegate.colour(key, default)

fun NNSettingsColourInt(key: String, default: Int = DefaultValues.DEFAULT_COLOUR_INT): Int = NNSettings.delegate.colourInt(key, default)

fun NNSettingsFloat(key: String, default: Float = DefaultValues.DEFAULT_FLOAT): Float = NNSettings.delegate.float(key, default)

fun NNSettingsBool(key: String, default: Boolean = DefaultValues.DEFAULT_BOOL): Boolean = NNSettings.delegate.boolean(key, default)

fun NNSettingsCollection(key: String): NetworkCollection = NNSettings.delegate.apiCollection(key)

fun String?.jsonEncode(): String {
    return this?.takeIf(String::isNotEmpty)?.let { input ->
        // JSONObject escapes relevant characters but also surrounds the string in quotes ("). We use the method to escape the string then use substring to get rid of the
        // surrounding quotes
        JSONObject.quote(input).let { it.substring(1, it.length - 1) }
    }.orEmpty()
}