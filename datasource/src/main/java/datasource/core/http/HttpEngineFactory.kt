package datasource.core.http

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.AndroidClientEngine
import io.ktor.client.engine.android.AndroidEngineConfig

class HttpEngineFactory(
    private val config: AndroidEngineConfig
) {
    fun build(): HttpClientEngine {
        return AndroidClientEngine(config)
    }
}
