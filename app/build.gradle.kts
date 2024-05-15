plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.sign.led"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sign.led"
        minSdk = 24
        targetSdk = 34
        versionCode = 3
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            resValue("string", "solkyname","Solky")
            resValue("string", "ADMOB_ID_MANIFEST","ca-app-pub-1256986380476629~6300321407")
            resValue("string", "ADMOB_ID_ADS","ca-app-pub-1256986380476629/3972158076")
            signingConfig = signingConfigs.getByName("debug")

        }

        getByName("debug"){
            isDebuggable = true

            resValue("string", "solkyname","[DEBUG] Solky")
            resValue("string", "ADMOB_ID_MANIFEST","ca-app-pub-3940256099942544~3347511713")
            resValue("string", "ADMOB_ID_ADS","ca-app-pub-3940256099942544/1033173712")
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
        buildConfig = true
    }



}

dependencies {

    val navVersion = "2.7.7"
    val dagVersion = "2.48"


    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //Dagger Hilt
    implementation("com.google.dagger:hilt-android:$dagVersion")
    kapt("com.google.dagger:hilt-compiler:$dagVersion")

    //Room
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    //Gson
    implementation("com.google.code.gson:gson:2.9.0")

    //ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    //Splash
    implementation("androidx.core:core-splashscreen:1.1.0-alpha02")

    //ADS
    implementation("com.google.android.gms:play-services-ads:23.0.0")

    //OTHERS
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}