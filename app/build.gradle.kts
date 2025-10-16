plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
    alias(libs.plugins.kotlin.kapt)

}

android {
    namespace = "com.example.qurio"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.qurio"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.android.gif.drawable)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    //room
    implementation(libs.androidx.room.ktx)
    implementation(libs.transport.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    //dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)


}