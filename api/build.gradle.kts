plugins {
    kotlin("jvm") version "1.9.22"
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.3-R0.1-SNAPSHOT")
    implementation(project(":common"))
    compileOnly(project(":v1_21_3"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "21"
    }
}

kotlin {
    jvmToolchain(21)
}