package com.kole.myapplication.cms.nnsettings

data class NetworkCollection(
    val key: String,
    val url: String,
    val parserUrl: String,
    val postString: String,
    val scrapeStartTag: String,
    val scrapeEndTag: String,
    val frontEndScrapeCheck: String,
    val method: Method,
    val cacheTime: Long,
    val headers: Map<String, String>,
    val backupCollection: NetworkCollection? = null,
    val shouldUseBackupCollection: Boolean = false,
    val disableFailedCallTracking: Boolean = false,
)

enum class Method {
    GET, POST, PATCH, PUT, DELETE, HEAD, OPTIONS, TRACE;

    companion object {
        fun fromString(method: String): Method = runCatching {
            valueOf(method.uppercase())
        }.getOrNull() ?: GET
    }
}