plugins {
    alias(libs.plugins.onestep.android.library)
    alias(libs.plugins.onestep.android.hilt)
}

android {
    namespace = "com.colddelight.datastore"
}

dependencies {

    implementation(project(":core:model"))
    implementation(libs.androidx.dataStore.core)
    implementation(libs.androidx.dataStore.preferences)
    implementation(libs.kotlinx.coroutines.android)
}