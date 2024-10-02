plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.kapt)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.myretrofit)
    implementation(libs.moshi)
    implementation(libs.converter.moshi)
    kapt(libs.moshi.kapt)
}


kapt {
    correctErrorTypes = true
}
