plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose)
//    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
    alias(libs.plugins.protobuf)

}

android {
    namespace = "com.rasel.baseappcompose"
    compileSdk = 35

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

        /**
         * Set the Room database schema export location for debug build to inspect the database
         */
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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

// Allow references to generated code
/*kapt {
    correctErrorTypes = true
}*/

// Setup protobuf configuration, generating lite Java and Kotlin classes
protobuf {
    protoc {
//        artifact = libs.protobuf.protoc.get().toString()
        artifact = "com.google.protobuf:protoc:4.26.1"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

/*androidComponents.beforeVariants {
    android.sourceSets.register(it.name) {
        val buildDir = layout.buildDirectory.get().asFile
        java.srcDir(buildDir.resolve("generated/source/proto/${it.name}/java"))
        kotlin.srcDir(buildDir.resolve("generated/source/proto/${it.name}/kotlin"))
    }
}*/

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
    implementation(libs.coil.kt)
    implementation(libs.glide.compose)
    implementation(libs.coil.kt.svg)


    // Dependency injection
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    implementation(libs.androidx.animation.graphics.android)
    implementation(libs.androidx.graphics.shapes.android)
    implementation(libs.androidx.constraintlayout.compose)
//    kapt(libs.hilt.android.compiler)
//    ksp(libs.hilt.compiler)
    ksp(libs.hilt.android.compiler)

    // Networking
    implementation(libs.okhttp3)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.kotlinx.serialization.json)


    implementation (libs.androidx.paging.runtime)
    implementation (libs.androidx.paging.compose)


    implementation(libs.androidx.tracing.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.work.ktx)
    implementation(libs.hilt.ext.work)

    api(libs.androidx.dataStore)
    api(libs.androidx.dataStore.core)
    api(libs.protobuf.kotlin.lite)


    implementation(libs.androidx.browser)
    implementation(libs.kotlinx.coroutines.core)


    implementation(libs.rometools.rome)
    implementation(libs.rometools.modules)


    // Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.room.compiler)

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