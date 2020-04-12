const val kotlinVersion = "1.4-M1"
const val objectboxVersion = "2.5.1"

object AppVersion {
    const val versionName: VersionName = "1.0.0-001"
    val versionCode: VersionCode = versionName.toVersionCode()
}

object BuildPlugins {

    object Versions {
        const val buildToolsVersion = "4.0.0-beta03"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val objectBoxGradlePlugin = "io.objectbox:objectbox-gradle-plugin:$objectboxVersion"

    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val kapt = "kotlin-kapt"
    const val objectBox = "io.objectbox"
}

object AndroidSdk {
    const val min = 23
    const val compile = 29
    const val target = compile
}

object Libraries {
    object Versions {
        const val appCompat = "1.1.0"
        const val materialDesign = "1.1.0"
        const val ktxCore = "1.2.0"
        const val lifecycleExtensions = "2.2.0"
        const val viewModelKtx = "2.2.0"
        const val constraintLayout = "1.1.2"
        const val coroutines = "1.3.5"
        const val coroutinesAndroid = "1.3.5"
        const val dagger = "2.27"
        const val retrofit = "2.8.1"
        const val moshi = "1.9.2"
        const val threeTenAbp = "1.2.3"
        const val groupie = "2.8.0"
        const val materialDialogs = "3.3.0"
        const val arrow = "0.10.4"
        const val coil = "0.9.5"
    }

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val materialDesign = "com.google.android.material:material:${Versions.materialDesign}"
    const val ktxCore = "androidx.core:core-ktx:${Versions.ktxCore}"
    const val lifeCycleExtensions =
        "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensions}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelKtx}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val daggerAndroidCompiler =
        "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    const val threeTenAbp = "com.jakewharton.threetenabp:threetenabp:${Versions.threeTenAbp}"
    const val groupie = "com.xwray:groupie:${Versions.groupie}"
    const val groupieViewBinding = "com.xwray:groupie-viewbinding:${Versions.groupie}"
    const val materialDialogsCore =
        "com.afollestad.material-dialogs:core:${Versions.materialDialogs}"
    const val materialDialogsLifecycle =
        "com.afollestad.material-dialogs:lifecycle:${Versions.materialDialogs}"
    const val materialDialogsBottomSheet =
        "com.afollestad.material-dialogs:bottomsheets:${Versions.materialDialogs}"
    const val arrow =
        "io.arrow-kt:arrow-fx:${Versions.arrow}"
    const val arrowSyntax =
        "io.arrow-kt:arrow-syntax:${Versions.arrow}"
    const val arrowMeta =
        "io.arrow-kt:arrow-meta:${Versions.arrow}"
    const val coil =
        "io.coil-kt:coil:${Versions.coil}"
    const val coilSvg =
        "io.coil-kt:coil-svg:${Versions.coil}"
}

object TestLibraries {
    object Versions {
        const val junit4 = "4.13"
        const val testRunner = "1.2.0"
        const val testJUnit = "1.1.1"
        const val espresso = "3.2.0"
    }

    const val junit4 = "junit:junit:${Versions.junit4}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val testJUnit = "androidx.test.ext:junit:${Versions.testJUnit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Libraries.Versions.coroutines}"
}
