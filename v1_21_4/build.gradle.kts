plugins {
    kotlin("jvm") version "1.9.22"
    id("io.papermc.paperweight.userdev")
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.REOBF_PRODUCTION

dependencies {
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
    compileOnly(project(":common"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "21"
    }
}

kotlin {
    jvmToolchain(21)
}