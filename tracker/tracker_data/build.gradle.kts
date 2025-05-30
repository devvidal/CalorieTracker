plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.dvidal.tracker_data"
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.trackerDomain))

    implementation(Retrofit.okHttp)
    implementation(Retrofit.retrofit)
    implementation(Retrofit.okHttpLoggingInterceptor)
    implementation(Retrofit.moshiConverter)

    kapt(Room.roomCompiler)
    implementation(Room.roomKtx)
    implementation(Room.roomRuntime)
}