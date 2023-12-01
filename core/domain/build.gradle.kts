plugins {
    alias(libs.plugins.onestep.android.library)
    alias(libs.plugins.onestep.android.hilt)


}

android {
    namespace = "com.colddelight.domain"

}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)

}