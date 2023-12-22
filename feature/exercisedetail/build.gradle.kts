plugins {
    alias(libs.plugins.onestep.android.feature)
    alias(libs.plugins.onestep.android.library.compose)
}

android {
    namespace = "com.colddelight.exercisedetail"
}

dependencies {
    implementation(project(":feature:exercise"))
}