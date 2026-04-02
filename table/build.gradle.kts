@file:OptIn(ExperimentalWasmDsl::class)
@file:Suppress("DEPRECATION")

import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
}

kotlin {
    // Android Target
    androidLibrary {
        namespace = "io.table.kmp"
        compileSdk = 36
        minSdk = 24

        withJava()
        withHostTestBuilder {}.configure {}
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }
    }

    // Desktop (JVM) Target
    jvm()

    // Web (WasmJS) Target
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
    js {
        browser()
        binaries.executable()
    }

    // iOS Targets
    val xcfName = "table"

    iosX64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)

                 implementation(compose.runtime)
                 implementation(compose.foundation)
                 implementation(compose.material3)
            }
        }

        androidMain {
            dependencies {
            }
        }


        iosMain {
            dependencies {
            }
        }

        jvmMain {
            dependencies {
//                implementation(libs.androidx.ui.desktop)
                // Desktop specific dependencies (e.g., Coroutines Swing)
            }
        }

        wasmJsMain {
            dependencies {
                implementation(compose.ui)
            }
        }
    }
}

group = "io.github.mamon-aburawi" // this group name in maven central repository
version = "1.0.0" // version of library

mavenPublishing {
    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Empty(),
            sourcesJar = true,
            androidVariantsToPublish = listOf("release", "debug"),
        )
    )

    coordinates(
        groupId = group.toString(),
        version = version.toString(),
        artifactId = "table-kmp"
    )

    pom {
        name = "Table KMP"
        description = "A powerful Compose Multiplatform table library."
        inceptionYear = "2026"
        url = "" // add link of repository
        licenses {
            license {
                name = "MIT License"
                url = "https://opensource.org/licenses/MIT"
            }
        }
        developers {
            developer {
                name = "Mamon Aburawi"
                email = "mamon.aburawi@gmail.com"
            }
        }
        scm {
            url = ""
        }
    }

    publishToMavenCentral()
    signAllPublications()
}