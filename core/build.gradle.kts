plugins {
    kotlin("jvm")
}

dependencies {
    implementation ("com.badlogicgames.gdx:gdx:1.12.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

kotlin {
    jvmToolchain(25)
}