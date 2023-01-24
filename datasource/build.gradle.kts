apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    "implementation"(project(Modules.domain))

    "implementation"(Ktor.core)
    "implementation"(Ktor.logging)
    "implementation"(Ktor.contentNegotiation)
    "implementation"(Ktor.serialization)
    "implementation"(Ktor.android)
}
