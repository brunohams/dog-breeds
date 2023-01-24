object Build {
    private const val androidBuildToolsVersion = "7.1.2"
    private const val ktlintVersion = "10.3.0"
    private const val crashlyticsVersion = "2.9.1"
    private const val serializationVersion = "1.6.10"

    const val androidBuildTools = "com.android.tools.build:gradle:$androidBuildToolsVersion"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
    const val hiltAndroid = "com.google.dagger:hilt-android-gradle-plugin:${Hilt.hiltVersion}"

    const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Navigation.version}"

    const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:$ktlintVersion"

    const val serialization = "org.jetbrains.kotlin:kotlin-serialization:$serializationVersion"
}