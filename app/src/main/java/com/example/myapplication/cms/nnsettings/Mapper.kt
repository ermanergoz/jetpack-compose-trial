package com.kole.myapplication.cms.nnsettings

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

object Mapper {

    @JvmStatic
    val jackson = ObjectMapper().also {
        // Ignore any unknown properties when deserializing
        it.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        it.registerKotlinModule()
    }

    @Deprecated("Access using variable getter", replaceWith = ReplaceWith("Mapper.jackson"))
    @JvmStatic
    fun get(): ObjectMapper = jackson

    //region Serialize
    /**
     * Returns JSON representation of [data], or null if it cannot be represented by JSON
     */
    @JvmStatic
    fun string(data: Any?): String? = runCatching {
        jackson.writeValueAsString(data)
    }.getOrNull()

    /**
     * Write [data] to [file] as a JSON string. Returns true is successful
     */
    @JvmStatic
    fun writeToDisk(data: Any?, file: File): Boolean = runCatching {
        jackson.writeValue(file, data)
    }.isSuccess
    //endregion

    //region Deserialize
    /**
     * Deserialize given JSON [data] into the provided [type] class
     *
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     * @throws com.fasterxml.jackson.core.JsonParseException
     */
    @JvmStatic
    fun <T> objectOrThrow(data: String, type: Class<T>): T = jackson.readValue(data, type)

    /**
     * Deserialize the given JSON [file] into the provided [type] class
     *
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     * @throws com.fasterxml.jackson.core.JsonParseException
     */
    @JvmStatic
    fun <T> objectOrThrow(file: File, type: Class<T>): T = jackson.readValue(file, type)

    /**
     * Deserialize given JSON [data] into the provided [type] class. Returns null if unable to deserialize
     */
    @JvmStatic
    fun <T> `object`(data: String, type: Class<T>): T? = runCatching {
        objectOrThrow(data, type)
    }.getOrNull()

    /**
     * Deserialize JSON from [file] into the provided [type] class. Returns null if unable to deserialize
     */
    @JvmStatic
    fun <T> readFromDisk(file: File, type: Class<T>): T? = runCatching {
        jackson.readValue(file, type)
    }.getOrNull()
    //endregion
}