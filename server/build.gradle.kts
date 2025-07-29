import com.undefinedcreations.nova.ServerType

plugins {
    id("setup")
    id("com.gradleup.shadow")
    id("com.undefinedcreations.nova") version "0.0.4"
    id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.3.0"
}

repositories {
    maven {
        name = "undefined-releases"
        url = uri("https://repo.undefinedcreations.com/releases")
    }
    maven {
        name = "undefined-snapshots"
        url = uri("https://repo.undefinedcreations.com/snapshots")
    }
}

val adventureVersion = properties["adventure_version"]!!

dependencies {
    compileOnly(libs.spigotmc)

    // Undefined Creations Libraries
    implementation("com.undefined:stellar:1.0.3")

    // Adventure
    implementation("net.kyori:adventure-api:$adventureVersion")
    implementation("net.kyori:adventure-text-minimessage:$adventureVersion")
    implementation("net.kyori:adventure-platform-bukkit:4.4.0")
    implementation("net.kyori:adventure-text-serializer-legacy:$adventureVersion")

    // Project Dependencies
    implementation(project(":"))
}

tasks {
    shadowJar {
        archiveFileName = "server.jar"
    }
    runServer {
        minecraftVersion("1.21.8")
        serverType(ServerType.SPIGOT)
        perVersionFolder(true)
        acceptMojangEula()
    }
}

bukkitPluginYaml {
    name = "Cassini"
    version = properties["version"] as String
    main = "Main"
    apiVersion = "1.21"
    author = "StillLutto"
}