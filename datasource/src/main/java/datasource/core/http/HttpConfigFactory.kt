package datasource.core.http

import io.ktor.client.engine.android.AndroidEngineConfig

class HttpConfigFactory {
    fun build(): AndroidEngineConfig {
        return AndroidEngineConfig().apply {
            connectTimeout = 60000
            // Set timeout, SSL, etc
        }
    }
}
