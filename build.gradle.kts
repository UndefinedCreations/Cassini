import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.sonarqube") version "6.0.1.5171"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.14" apply false
}

apply(plugin = "maven-publish")

sonar {
    properties {
        property("sonar.projectKey", "Cassini")
        property("sonar.projectName", "Cassini")
    }
}

val projectVersion = "0.0.13"
val projectGroupId = "com.undefined"
val adventureVersion = properties["adventure_version"]

group = "com.undefined"
version = "0.0.1"

publishing {
    repositories {
        maven {
            name = "UndefinedCreations"
            url = uri("https://repo.undefinedcreations.com/releases")
            credentials(PasswordCredentials::class) {
                username = project.properties["mavenUser"].toString()
                password = project.properties["mavenPassword"].toString()
            }
        }
    }
    publications {
        register<MavenPublication>("maven") {
            groupId = projectGroupId
            version = projectVersion

            from(components["java"])
        }
    }
}

allprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    group = projectGroupId
    version = projectVersion

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.codemc.io/repository/nms/")
        maven {
            name = "sonatype"
            url = uri("https://oss.sonatype.org/content/groups/public/")
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation("net.kyori:adventure-api:$adventureVersion")
        implementation("net.kyori:adventure-platform-bukkit:$adventureVersion")
        implementation("net.kyori:adventure-text-minimessage:$adventureVersion")
        implementation("net.kyori:adventure-text-serializer-legacy:$adventureVersion")
        implementation("net.kyori:adventure-text-serializer-bungeecord:$adventureVersion")
    }

}

dependencies {
    implementation(project(":common"))
    implementation(project(":v1_21_4:", "reobf"))
    implementation(project(":api"))
}

tasks {
    assemble {
        dependsOn("shadowJar")
    }
    jar.configure {
        dependsOn("shadowJar")
        archiveClassifier.set("dev")
    }

    withType<ShadowJar> {
        archiveClassifier = ""
        archiveFileName = "${project.name}-${project.version}.jar"
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "21"
    }
}


kotlin {
    jvmToolchain(21)
}