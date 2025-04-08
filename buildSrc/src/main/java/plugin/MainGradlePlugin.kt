package plugin

import ProjectConfig
import ProjectConfig.versionCode
import ProjectConfig.versionName
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class MainGradlePlugin: Plugin<Project> {

    override fun apply(project: Project) {
//        applyPlugins(project)
//        setProjectConfig(project)
    }

    private fun applyPlugins(project: Project) {
        project.apply {
            plugin("com.android.application")
            plugin("org.jetbrains.kotlin.android")
            plugin("dagger.hilt.android.plugin")
            plugin("kotlin-kapt")
        }
    }

    private fun setProjectConfig(project: Project) {
        project.android().apply {
            compileSdk = ProjectConfig.compileSdk

            defaultConfig {
                minSdk = ProjectConfig.minSdk
                testInstrumentationRunner = "com.dvidal.calorietracker.HiltTestRunner"
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }
    }

    private fun Project.android(): LibraryExtension {
        return extensions.getByType(LibraryExtension::class.java)
    }

}