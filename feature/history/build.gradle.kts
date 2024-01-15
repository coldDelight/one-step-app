plugins {
    alias(libs.plugins.onestep.android.feature)
    alias(libs.plugins.onestep.android.library.compose)
}
android {
    namespace = "com.colddelight.history"

}

dependencies {
    implementation ("com.kizitonwose.calendar:compose:2.4.1")
}