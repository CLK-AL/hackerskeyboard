plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    // Android target with compilerOptions DSL
    @Suppress("DEPRECATION")
    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    // JavaScript target for browser
    js(IR) {
        browser {
            webpackTask {
                mainOutputFileName = "unikey.js"
            }
        }
        binaries.library()
    }

    // WebAssembly target for browser (Kotlin/Wasm)
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            webpackTask {
                mainOutputFileName = "unikey-wasm.js"
            }
        }
        binaries.library()
    }

    // Source sets
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }

        val wasmJsMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }

        val wasmJsTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "org.pocketworkstation.unikey"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
