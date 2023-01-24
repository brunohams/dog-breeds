plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildTools

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures.dataBinding = true
}

dependencies {
    implementation(project(Modules.presentation))
    implementation(project(Modules.domain))
    implementation(project(Modules.datasource))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.lifecycleVmKtx)
    implementation(AndroidX.lifecycleRuntimeKtx)

    implementation(Navigation.fragment)
    implementation(Navigation.ui)
    implementation(Navigation.support)

    implementation(Picasso.core)

    implementation(Ktor.core)
    implementation(Ktor.contentNegotiation)
    implementation(Ktor.serialization)
    implementation(Ktor.android)

    implementation(OkHttp.core)

    implementation(Google.material)

    implementation(Hilt.android)
    kapt(Hilt.compiler)

    implementation(Room.runtime)
    implementation(Room.ktx)
    kapt(Room.compiler)

    testImplementation(Kotlin.test)
    testImplementation(Coroutines.test)

    testImplementation(MockK.core)

    androidTestImplementation(HiltTest.hiltAndroidTesting)
    kaptAndroidTest(Hilt.compiler)

    androidTestImplementation(Junit.junit4)
    androidTestImplementation(AndroidXTest.runner)
}
