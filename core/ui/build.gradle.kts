plugins {
    alias(libs.plugins.onestep.android.library)
    alias(libs.plugins.onestep.android.library.compose)
}

android {
    namespace = "com.colddelight.ui"

}
dependencies {

    implementation(project(":core:model"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:domain"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
}