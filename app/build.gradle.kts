import com.android.build.gradle.internal.dsl.BuildType
import com.android.builder.internal.ClassFieldImpl

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId = "com.example.foursquaresearch"
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.foursquaresearch.CustomTestRunner"
    }

    buildTypes {
        getByName("debug") {
            addBuildConfigField(
                ClassFieldImpl(
                    "String", "CLIENT_ID", "\"${BuildConfig.CLIENT_ID}\""
                )
            )
            addBuildConfigField(
                ClassFieldImpl(
                    "String", "CLIENT_SECRET", "\"${BuildConfig.CLIENT_SECRET}\""
                )
            )
        }
        getByName("release") {
            addBuildConfigField(
                ClassFieldImpl(
                    "String", "CLIENT_ID", "\"${BuildConfig.CLIENT_ID}\""
                )
            )
            addBuildConfigField(
                ClassFieldImpl(
                    "String", "CLIENT_SECRET", "\"${BuildConfig.CLIENT_SECRET}\""
                )
            )
            isMinifyEnabled = false
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.APP_COMPAT)
    implementation(Dependencies.CONSTRAINT_LAYOUT)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_CONVERTER_GSON)
    implementation(Dependencies.HILT)
    kapt(Dependencies.HILT_KAPT)
    implementation(Dependencies.COROUTINES)
    implementation(Dependencies.GOOGLE_PLAY_LOCATION)

    testImplementation(Dependencies.JUNIT)
    testImplementation(Dependencies.MOCKITO)
    testImplementation(Dependencies.COROUTINES_TEST)

    androidTestImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.MOCKITO)
    androidTestImplementation(Dependencies.MOCKITO_ANDROID)
    androidTestImplementation(Dependencies.ANDROIDX_JUNIT)
    androidTestImplementation(Dependencies.ANDROIDX_JUNIT_KTX)
    androidTestImplementation(Dependencies.ANDROIDX_TEST_CORE)
    androidTestImplementation(Dependencies.ESPRESSO)
    androidTestImplementation(Dependencies.HILT_TESTING)
    kaptAndroidTest(Dependencies.HILT_KAPT)
}
