plugins {
    alias(libs.plugins.onestep.android.library)
    alias(libs.plugins.onestep.android.library.compose)
}

android {
    namespace = "com.colddelight.designsystem"

}

dependencies {
    debugApi(libs.androidx.compose.ui.tooling)
    implementation(project(":core:model"))

    implementation(libs.androidx.core.ktx)
}