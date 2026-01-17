import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidLibrary {
        namespace = "com.hazrat.messagelater.feature.createReminder.ui"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )

    targets
        .withType<KotlinAndroidTarget>()
        .configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }


    sourceSets {

        androidMain.dependencies {

        }

        commonMain.dependencies {
            implementation(projects.common.ui)
            implementation(projects.common.domain.repository)

            implementation(projects.core.contacts)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.material.icons.extended)
            implementation(libs.kotlinx.datetime)

            implementation(libs.koin.compose.viewmodel)

            implementation(libs.moko.permissions.notifications)
            implementation(libs.moko.permissions.contact)
        }

        iosMain.dependencies {

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
