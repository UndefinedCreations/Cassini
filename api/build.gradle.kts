plugins {
    kotlin("jvm") version "1.9.22"
}

publishing {
    publications {
        register<MavenPublication>("maven") {
            groupId = group.toString()
            version = version.toString()

            from(components["java"])
        }
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.3-R0.1-SNAPSHOT")
    compileOnly(project(":v1_21_4"))
    implementation(project(":common"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "21"
    }
}

kotlin {
    jvmToolchain(21)
}