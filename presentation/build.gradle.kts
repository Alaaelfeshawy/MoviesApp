plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "banquemisr.challenge05.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "banquemisr.challenge05.CustomTestRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
//    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class) {
//        kotlinOptions.jvmTarget = "19"
//    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

dependencies {

    //Hilt-Dagger
    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-android-compiler:2.45")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.02.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.paging:paging-compose:3.2.1")
    //coil
    implementation ("io.coil-kt:coil-compose:2.4.0")

    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")


    implementation("androidx.test:rules:1.5.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("io.mockk:mockk:1.13.10")
    androidTestImplementation ("androidx.navigation:navigation-testing:2.7.7")
    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.45")
    kaptTest ("com.google.dagger:hilt-android-compiler:2.45")
    kaptAndroidTest ("com.google.dagger:hilt-android-compiler:2.45")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    implementation(project(":domain"))
    androidTestImplementation(project(":app"))

//    implementation ("androidx.compose.ui:ui:1.6.2")

}