plugins {
    id("com.android.application")
}

android {
    namespace = "ro.pub.cs.systems.eim.practicaltest02"
    compileSdk = 34 // Folosim o versiune stabila

    defaultConfig {
        applicationId = "ro.pub.cs.systems.eim.practicaltest02"
        minSdk = 26 // Conform cerintei: Jelly Bean [cite: 34]
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // Aceasta linie este vitala pentru a folosi HttpClient-ul vechi [cite: 53]
    useLibrary("org.apache.http.legacy")
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Adaugam manual libraria pentru a elimina erorile "cz.msebera"
    implementation("com.loopj.android:android-async-http:1.4.11")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}