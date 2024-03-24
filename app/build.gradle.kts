plugins {
    alias(libs.plugins.onestep.android.application)
    alias(libs.plugins.onestep.android.hilt)
    alias(libs.plugins.onestep.android.application.compose)
    id("com.google.android.gms.oss-licenses-plugin")
}

android {
    namespace = "com.colddelight.onestep"

    defaultConfig {
        applicationId = "com.colddelight.onestep"
        versionCode = 5
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
//            isMinifyEnabled = false
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":feature:home"))
    implementation(project(":feature:exercise"))
    implementation(project(":feature:exercisedetail"))

    implementation(project(":feature:routine"))
    implementation(project(":feature:history"))

    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))



    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)



    implementation(libs.androidx.core.splashscreen)
    implementation ("com.google.android.gms:play-services-oss-licenses:17.0.0")


}