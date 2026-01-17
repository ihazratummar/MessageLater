import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.composeCompiler)

    alias(libs.plugins.sql.delight)
}

kotlin {
    androidLibrary {
        namespace = "com.hazrat.messagelater.core.database"
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
            api(libs.koin.android)
            implementation(libs.sql.delight.android)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            api(libs.koin.core)

            api(libs.sql.delight.common)
            api(libs.sql.delight.common.coroutines)

        }

        iosMain.dependencies {
            implementation(libs.sql.delight.ios)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

sqldelight {
    databases {
        create("AppDatabase"){
            packageName.set("com.hazrat.messagelater.database")
            srcDirs("src/commonMain/sqldelight")
            version = 1
        }
    }
}