import com.undefinedcreations.runServer.RamAmount
import com.undefinedcreations.runServer.ServerType

plugins {
    kotlin("jvm") version "1.9.22"
    id("com.undefinedcreations.runServer") version "0.1.6"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    maven("https://repo.undefinedcreations.com/releases")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT")
    implementation(project(":common"))
    implementation(project(":v1_21_4:", "reobf"))
    implementation(project(":api"))
    implementation("com.undefined:stellar:0.1.36:spigot")
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "21"
    }
    runServer {
        minecraftVersion("1.21.4")
        serverType(ServerType.PAPERMC)
        allowedRam(1, RamAmount.GIGABYTE)
        serverFolderName { "run" }
        acceptMojangEula()
    }
}

java {
    disableAutoTargetJvm()
}

kotlin {
    jvmToolchain(21)
}