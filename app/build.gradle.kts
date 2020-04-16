plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kapt)
    id(BuildPlugins.objectBox)
}

android {
    compileSdkVersion(AndroidSdk.compile)
    defaultConfig {
        applicationId = "com.genericandwildcard.coronafinder.app"
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionName = AppVersion.versionName
        versionCode = AppVersion.versionCode
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
//    buildTypes {
//        getByName("release") {
////            isMinifyEnabled = false
////            proguardFiles(
////                getDefaultProguardFile("proguard-android-optimize.txt"),
////                "proguard-rules.pro"
////            )
//        }
//    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(Libraries.mpAndroidChartLib)
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.appCompat)
    implementation(Libraries.materialDesign)
    implementation(Libraries.ktxCore)
    implementation(Libraries.viewModelKtx)
    implementation(Libraries.lifeCycleExtensions)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.coroutines)
    implementation(Libraries.coroutinesAndroid)
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitMoshi)
    implementation(Libraries.moshi)
    kapt(Libraries.moshiCodegen)
    implementation(Libraries.threeTenAbp)
    implementation(Libraries.groupie)
    implementation(Libraries.groupieViewBinding)
    implementation(Libraries.materialDialogsCore)
    implementation(Libraries.materialDialogsBottomSheet)
    implementation(Libraries.materialDialogsLifecycle)
    implementation(Libraries.arrow)
    implementation(Libraries.arrowSyntax)
    kapt(Libraries.arrowMeta)
    implementation(Libraries.coil)
    implementation(Libraries.coilSvg)

    implementation(Libraries.dagger)
    implementation(Libraries.daggerAndroid)
    implementation(Libraries.daggerAndroidSupport)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerAndroidCompiler)

    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.coroutines)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.testJUnit)
    androidTestImplementation(TestLibraries.espresso)
}
