plugins {
    alias(libs.plugins.onestep.android.feature)
    alias(libs.plugins.onestep.android.library.compose)
}

android {
    namespace = "com.colddelight.home"

}

dependencies {
    implementation(project(":feature:exercise"))
    implementation(project(":core:network"))

    implementation(libs.supabase.compose.auth)
    implementation(libs.supabase.compose.auth.ui)
}