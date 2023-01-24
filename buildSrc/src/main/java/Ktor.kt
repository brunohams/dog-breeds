object Ktor {
    private const val ktorVersion = "2.0.0"
    const val core = "io.ktor:ktor-client-core:$ktorVersion"
    const val android = "io.ktor:ktor-client-android:$ktorVersion"
    const val logging = "io.ktor:ktor-client-logging:$ktorVersion"

    const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:$ktorVersion"
    const val serialization = "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion"

    const val ktorClientMock = "io.ktor:ktor-client-mock:$ktorVersion"
}