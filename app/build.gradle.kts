plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose)

}

android {
    namespace = "com.rasel.baseappcompose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.rasel.baseappcompose"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += setOf("/META-INF/{AL2.0,LGPL2.1}", "rome-utils-1.18.0.jar")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.accompanist.adaptive.v0262beta)
    implementation(libs.kotlinx.collections.immutable)

    implementation(libs.readmore.material3)
    api(libs.kotlinx.datetime)
    api(libs.androidx.metrics)

    // Image loading
    implementation(libs.coil.kt.compose)

    // Dependency injection
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
//    ksp(libs.hilt.compiler)

    // Networking
    implementation(libs.okhttp3)
    implementation(libs.okhttp.logging)


    // Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
//    ksp(libs.androidx.room.compiler)

//    implementation(libs.rometools.rome)
//    implementation(libs.rometools.modules)
    implementation(libs.androidx.adaptive.layout.android)
    implementation(libs.androidx.adaptive.navigation.android)

    coreLibraryDesugaring(libs.core.jdk.desugaring)


    implementation(libs.androidx.glance)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.material3)
    implementation(libs.androidx.compose.materialWindow)

    implementation(libs.androidx.window.core.android)
    implementation(libs.androidx.material3.adaptive.navigation.suite.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}