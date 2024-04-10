plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "nl.littlerobots.example.llamacpp"
    compileSdk = 34
    defaultConfig {
        minSdk = 30
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        externalNativeBuild {
            cmake {
                arguments+= listOf("-DCMAKE_BUILD_TYPE=Release",
                    "-DBUILD_SHARED_LIBS=ON",
                    "-DANDROID_STL=c++_shared")
            }
        }
        ndk {
            //noinspection ChromeOsAbiSupport
            abiFilters.add("arm64-v8a")
            ndkVersion = libs.versions.ndk.get()
        }
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        prefab = true
        prefabPublishing = true
    }

    prefab {
        create("llama") {
            headers = "llama.cpp"
            libraryName = "libllama"
        }

        create("common") {
            headers = "llama.cpp/common"
            libraryName = "libcommon"
        }
    }

    externalNativeBuild {
        cmake {
            path = file("CMakeLists.txt")
            version = "3.22.1"
        }
    }
}
