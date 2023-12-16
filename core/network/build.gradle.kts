plugins {
    alias(libs.plugins.onestep.android.library)
    alias(libs.plugins.onestep.android.hilt)
    id("kotlinx-serialization")

}

android {
    namespace = "com.colddelight.network"

    val key: String = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
        .getProperty("supabaseKey")
    val url: String = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
        .getProperty("supabaseUrl")
    val googleClientId: String = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(
        rootDir
    ).getProperty("googleClientId")

    defaultConfig{
        buildConfigField("String","supabaseKey","\"$key\"")
        buildConfigField("String","supabaseUrl","\"$url\"")
        buildConfigField("String","googleClientId","\"$googleClientId\"")
    }
    buildFeatures {
        buildConfig = true
    }

}

dependencies {

    implementation(libs.supabase.gotrue)
    implementation(libs.ktor.client.cio)
    implementation(libs.supabase.compose.auth)
    implementation(libs.supabase.postgrest)
    implementation(libs.supabase.compose.auth.ui)
    implementation(libs.kotlinx.serialization.json)



}