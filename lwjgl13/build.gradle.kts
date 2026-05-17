plugins {
    kotlin("jvm") version "2.3.20"
    application
}

dependencies {
    implementation (project(":core"))

    implementation ("com.badlogicgames.gdx:gdx-backend-lwjgl3:1.12.1")
    implementation ("com.badlogicgames.gdx:gdx-platform:1.12.1:natives-desktop")
}

application {

}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

kotlin {
    jvmToolchain(25)
}