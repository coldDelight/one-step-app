plugins {
    alias(libs.plugins.onestep.android.feature)
    alias(libs.plugins.onestep.android.library.compose)
}

android {
    namespace = "com.colddelight.login"

}

dependencies {

    implementation(libs.supabase.compose.auth)
    implementation(libs.supabase.compose.auth.ui)


}