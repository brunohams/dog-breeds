apply {
    from("$rootDir/android-library-build.gradle")
}

apply(plugin = "org.jetbrains.kotlin.android")
apply(plugin = "androidx.navigation.safeargs.kotlin")

dependencies {
    "implementation"(project(Modules.domain))

    "implementation"(Picasso.core)
    "implementation"(Lottie.core)
}
