plugins {
    id("com.android.application")
    id("kotlin-android")
    // The Flutter Gradle Plugin must be applied after the Android and Kotlin Gradle plugins.
    id("dev.flutter.flutter-gradle-plugin")
}

// Play app bundles use a different applicationId. Product flavors are not
// used: they would rename flavor-less tasks such as assembleRelease and the
// APK output (app-release.apk). `flutter build appbundle` requests
// bundleRelease; `flutter build apk` requests assembleRelease. Only bundle
// tasks select the Play id, and the two artifacts must be built in separate
// Gradle invocations.
val fdroidApplicationId = "com.lucao.limpazap"
val playApplicationId = "br.tec.lew.limpazap"

fun isPlayBundleBuild(): Boolean {
    return gradle.startParameter.taskNames.any { taskName ->
        taskName.substringAfterLast(':').startsWith("bundle")
    }
}

android {
    // Namespace is the Java package (MainActivity, R). It stays fixed so both
    // application ids can share this source set.
    namespace = "com.lucao.limpazap"
    // Flutter 3.27 still defaults compile/target SDK to 35. Play requires 36.
    compileSdk = 36
    // ndkVersion = flutter.ndkVersion
    ndkVersion = "27.0.12077973"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    defaultConfig {
        applicationId = if (isPlayBundleBuild()) playApplicationId else fdroidApplicationId
        // You can update the following values to match your application needs.
        // For more information, see: https://flutter.dev/to/review-gradle-config.
        minSdk = flutter.minSdkVersion
        targetSdk = 36
        versionCode = flutter.versionCode
        versionName = flutter.versionName
    }

    buildTypes {
        release {
            // TODO: Add your own signing config for the release build.
            // Signing with the debug keys for now, so `flutter run --release` works.
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

flutter {
    source = "../.."
}
