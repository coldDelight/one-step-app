plugins {
    alias(libs.plugins.onestep.android.library)
    alias(libs.plugins.onestep.android.hilt)
}

android {
    namespace = "com.colddelight.data"
}

dependencies {


    implementation(project(":core:datastore"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)


    implementation(libs.androidx.core.ktx)
}