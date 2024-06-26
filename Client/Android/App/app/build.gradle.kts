plugins {
    id("com.android.application")

    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.monopoco.musicmp4"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.monopoco.musicmp4"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.annotation:annotation:1.7.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui:2.7.6")
    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))

    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")

    implementation("com.google.firebase:firebase-auth")

    implementation("com.github.Dimezis:BlurView:version-2.0.0")

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation("androidx.media:media:1.7.0")

    implementation("androidx.work:work-runtime-ktx:2.9.0")

    implementation("com.squareup.retrofit2:retrofit:2.3.0")
    implementation("com.squareup.retrofit2:converter-gson:2.3.0")

    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    implementation("io.reactivex.rxjava2:rxandroid:2.0.1")
    implementation("io.reactivex.rxjava2:rxjava:2.0.1")

    implementation("com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.google.zxing:core:3.2.1")
    implementation("com.auth0.android:jwtdecode:2.0.2")

}