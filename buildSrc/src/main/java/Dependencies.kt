/**
 * Dependencies that are used in the application
 */
object Dependencies {
    private const val KOTLIN_VERSION = "1.3.72"
    private const val BUILD_GRADLE_PLUGIN_VERSION = "4.1.2"

    private const val CORE_KTX_VERSION = "1.3.2"
    private const val APP_COMPAT_VERSION = "1.2.0"
    private const val CONSTRAINT_LAYOUT_VERSION = "2.0.4"
    private const val MATERIAL_VERSION = "1.2.1"
    private const val RETROFIT_VERSION = "2.6.2"
    private const val COROUTINES_VERSION = "1.3.9"
    private const val GOOGLE_PLAY_VERSION = "18.0.0"

    private const val JUNIT_VERSION = "4.+"
    private const val MOCKITO_VERSION = "2.23.0"
    private const val HILT_VERSION = "2.28-alpha"

    private const val ANDROIDX_JUNIT_VERSION = "1.1.2"
    private const val ANDROIDX_TEST_CORE_VERSION = "1.3.0"
    private const val ESPRESSO_VERSION = "3.3.0"

    // plugins
    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${KOTLIN_VERSION}"
    const val BUILD_GRADLE_PLUGIN = "com.android.tools.build:gradle:${BUILD_GRADLE_PLUGIN_VERSION}"
    const val HILT_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:$HILT_VERSION"

    // main dependencies
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${KOTLIN_VERSION}"
    const val CORE_KTX = "androidx.core:core-ktx:${CORE_KTX_VERSION}"
    const val APP_COMPAT = "com.google.android.material:material:$APP_COMPAT_VERSION"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:$CONSTRAINT_LAYOUT_VERSION"
    const val MATERIAL = "com.google.android.material:material:$MATERIAL_VERSION"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION"
    const val RETROFIT_CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION"
    const val HILT = "com.google.dagger:hilt-android:$HILT_VERSION"
    const val HILT_KAPT = "com.google.dagger:hilt-android-compiler:$HILT_VERSION"
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINES_VERSION"
    const val GOOGLE_PLAY_LOCATION = "com.google.android.gms:play-services-location:$GOOGLE_PLAY_VERSION"

    // test dependencies
    const val JUNIT = "junit:junit:$JUNIT_VERSION"
    const val MOCKITO = "org.mockito:mockito-core:$MOCKITO_VERSION"
    const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$COROUTINES_VERSION"

    // ui test dependencies
    const val MOCKITO_ANDROID = "org.mockito:mockito-android:$MOCKITO_VERSION"
    const val ANDROIDX_JUNIT = "androidx.test.ext:junit:$ANDROIDX_JUNIT_VERSION"
    const val ANDROIDX_JUNIT_KTX = "androidx.test.ext:junit-ktx:$ANDROIDX_JUNIT_VERSION"
    const val ANDROIDX_TEST_CORE = "androidx.test:core-ktx:$ANDROIDX_TEST_CORE_VERSION"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:$ESPRESSO_VERSION"
    const val HILT_TESTING = "com.google.dagger:hilt-android-testing:$HILT_VERSION"
}
