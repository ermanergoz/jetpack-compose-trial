package com.kole.myapplication.cms.nnsettings

import android.graphics.Color
import com.kole.myapplication.cms.CMSMock

class SettingsManager {
    fun getString(key: String, default: String, subs: Map<String, String>): String {
        val settingString = (CMSMock.data[key] as? String)?: default
        return replaceSubstitutionsInUrlOrString(settingString, subs)
    }

    fun getUrl(key: String, default: String, subs: Map<String, String>): String {
        val settingString = (CMSMock.data[key] as? String)?: default
        return replaceSubstitutionsInUrlOrString(settingString, subs)
    }

    fun getInteger(key: String, default: Int): Int {
        return try {
            (CMSMock.data[key] as? Int)?: default
        } catch (e: java.lang.NumberFormatException) {
            default
        }
    }

    fun getFloat(key: String, default: Float): Float {
        return try {
            (CMSMock.data[key] as? Float)?: default
        } catch (e: java.lang.NumberFormatException) {
            default
        }
    }

    fun getBoolean(key: String, default: Boolean): Boolean {
        return (CMSMock.data[key] as? Boolean)?: default
    }

    fun getColour(key: String, default: String): String {
        return (CMSMock.data[key] as? String)?: default
    }

    fun getColourInt(key: String, default: Int): Int {
        return try {
            Color.parseColor("#${getColour(key, DefaultValues.DEFAULT_COLOUR)}")
        } catch (e: java.lang.NumberFormatException) {
            default
        }
    }

    private fun replaceSubstitutionsInUrlOrString(urlOrString: String, substitutions: Map<String, String>): String {
        var result = urlOrString
        if (urlOrString.isNotEmpty() && substitutions.isNotEmpty()) {
            substitutions.entries.forEach { entry ->
                result = result.replace(entry.key, entry.value)
            }
        }

        return result
    }

    companion object {
        val instance = SettingsManager()
    }
}