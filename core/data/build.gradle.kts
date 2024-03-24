plugins {
    alias(libs.plugins.onestep.android.library)
    alias(libs.plugins.onestep.android.hilt)
}

android {
    namespace = "com.colddelight.data"
}

dependencies {


    implementation(project(":core:datastore"))
    implementation(project(":core:database"))
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.core.ktx)

}