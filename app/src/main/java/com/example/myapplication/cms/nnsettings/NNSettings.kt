package com.kole.myapplication.cms.nnsettings

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.type.TypeFactory

object NNSettings {
    var delegate: SettingsInterface = SettingsInterfaceImpl()
}

internal class SettingsInterfaceImpl : SettingsInterface {

    override fun string(key: String, default: String, subs: Map<String, String>): String = SettingsManager.instance.getString(key, default, subs)

    override fun url(key: String, default: String, subs: Map<String, String>): String = SettingsManager.instance.getUrl(key, default, subs)

    override fun integer(key: String, default: Int): Int = SettingsManager.instance.getInteger(key, default)

    override fun long(key: String, default: Int): Long = integer(key, default).toLong()

    override fun float(key: String, default: Float): Float = SettingsManager.instance.getFloat(key, default)

    override fun boolean(key: String, default: Boolean): Boolean = SettingsManager.instance.getBoolean(key, default)

    override fun colour(key: String, default: String): String = SettingsManager.instance.getColour(key, default)

    override fun colourInt(key: String, default: Int): Int = SettingsManager.instance.getColourInt(key, default)

    override fun plural(key: String, count: Int): String {
        val actualKey = if (count > 1) key.plus("Plural") else key
        return string(actualKey, subs = mapOf("{COUNT}" to count.toString()))
    }

    override fun apiCollection(apiKey: String): NetworkCollection {
        val shouldUseBackup = boolean("${apiKey}ShouldUseBackupCollection")
        val backupCollection = if (shouldUseBackup && url("${apiKey}BackupCollectionURL").isNotEmpty()) {
            apiCollection("${apiKey}BackupCollection")
        } else {
            null
        }
        return NetworkCollection(
            apiKey,
            url("${apiKey}URL"),
            url("${apiKey}ParserURL"),
            string("${apiKey}Payload").ifEmpty { string("${apiKey}URLParameters") },
            string("${apiKey}CutOffStartTag"),
            string("${apiKey}CutOffEndTag"),
            string("${apiKey}FEStringCheck"),
            Method.fromString(string("${apiKey}HTTPMethod")),
            long("${apiKey}CacheTime"),
            map("${apiKey}HTTPHeaders", keyClass = String::class.java, valueClass = String::class.java),
            backupCollection,
            shouldUseBackup,
            boolean("${apiKey}DisableFailedCallTracking")
        )
    }

    override fun <K, V> map(key: String, subs: Map<String, String>, default: Map<K, V>, keyClass: Class<K>, valueClass: Class<V>): Map<K, V> {
        val encodedSubs = subs.map { it.key to it.value.jsonEncode() }.toMap()
        val jsonString = string(key, subs = encodedSubs).takeUnless(String::isBlank) ?: return default

        val javaType = TypeFactory.defaultInstance().constructMapType(Map::class.java, keyClass, valueClass)

        return try {
            Mapper.jackson.readValue(jsonString, javaType)
        } catch (e: JsonProcessingException) {
            e.clearLocation()
            default
        }
    }

    override fun <T> list(key: String, default: List<T>, typeClass: Class<T>): List<T> {
        val javaType = Mapper.jackson.typeFactory.constructCollectionType(List::class.java, typeClass)
        return runCatching {
            Mapper.jackson.readValue<List<T>>(string(key), javaType)
        }.getOrDefault(default)
    }

    override fun <T> obj(key: String, defaultJsonString: String, typeClass: Class<T>): T? = Mapper.`object`(string(key, defaultJsonString), typeClass)
}