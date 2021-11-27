plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
    id("androidx.navigation.safeargs.kotlin")
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
}
configurations {

    testImplementation {
        resolutionStrategy {
            force("com.squareup.okhttp3:okhttp:4.9.0")
        }
    }
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId = "com.example.finalplayground"
        minSdkVersion(26)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.0")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-android-compiler:2.38.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")

    implementation("androidx.recyclerview:recyclerview:1.2.1")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0")

    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")

    testImplementation("junit:junit:4.+")

    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:3.14.9")
    testImplementation("androidx.arch.core:core-testing:2.1.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
