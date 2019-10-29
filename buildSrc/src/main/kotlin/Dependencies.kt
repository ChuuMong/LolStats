object Config {
    const val minSdk = 21
    const val compileSdk = 29
    const val targetSdk = 29
}

object Versions {
    const val androidBuildTools = "3.5.0"
    const val kotlin = "1.3.50"

    const val appComapt = "1.1.0"
    const val ktx = "1.0.2"

    const val constraintLayout = "1.1.3"
    const val recyclerview = "1.0.0"

    const val koin = "2.0.1"

    const val reftofit = "2.6.1"
    const val okhttp = "4.2.0"
    const val gson = "2.8.5"
    const val rxjava = "2.2.12"
    const val rxAndroid = "2.1.1"

    const val glide = "4.9.0"
}

object Dependencies {
    const val androidBuildTools = "com.android.tools.build:gradle:${Versions.androidBuildTools}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appComapt}"

    const val ktx = "androidx.core:core-ktx:${Versions.ktx}"

    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"

    const val koin = "org.koin:koin-android:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.reftofit}"
    const val retrofitGsonConvert = "com.squareup.retrofit2:converter-gson:${Versions.reftofit}"
    const val retrofitRxJava2Adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.reftofit}"

    const val okttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideComplier = "com.github.bumptech.glide:compiler:${Versions.glide}"
}