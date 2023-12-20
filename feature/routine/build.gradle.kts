plugins {
    alias(libs.plugins.onestep.android.feature)
    alias(libs.plugins.onestep.android.library.compose)
}

android {
    namespace = "com.colddelight.routine"

}

dependencies {
    implementation ("androidx.compose.foundation:foundation:1.3.0")

}