plugins {
    alias(libs.plugins.onestep.android.library)
    alias(libs.plugins.onestep.android.hilt)
//    kotlin("kapt")
}

android {
    namespace = "com.colddelight.database"
}

dependencies {

    implementation(project(":core:model"))


    implementation(libs.kotlinx.coroutines.android)


    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

}