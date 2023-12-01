import com.android.build.gradle.LibraryExtension
import com.colddelight.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    //feature 등에서 쓰는 compose

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
        }
    }

}