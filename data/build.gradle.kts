plugins {
    kotlin("kapt")

    id("com.android.library")
    id("kotlin-android")

    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.sqldelight)
}

sqldelight {
    databases {
        create("Favourites") {
            packageName.set("com.paranid5.emonlineshop.data")
        }
    }
}

android {
    namespace = "com.paranid5.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        lint.targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.okhttp)

    implementation(libs.sqldelight.android.driver)
    implementation(libs.sqldelight.coroutines.extensions)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}