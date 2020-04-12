repositories {
    jcenter()
}

plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("junit:junit:4.13")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}
