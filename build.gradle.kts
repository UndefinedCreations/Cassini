import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    java
    `maven-publish`
    kotlin("jvm") version "1.9.22"
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

apply(plugin = "maven-publish")

val projectVersion = "0.0.2"
val projectGroupId = "com.undefined"

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
    apply(plugin = "java")
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
        implementation("net.kyori:adventure-api:4.3.4")
        implementation("net.kyori:adventure-platform-bukkit:4.3.4")
        implementation("net.kyori:adventure-text-minimessage:4.3.4")
        implementation("net.kyori:adventure-text-serializer-legacy:4.3.4")
        implementation("net.kyori:adventure-text-serializer-bungeecord:4.3.4")
    }

}

dependencies {
    implementation(project(":common"))
    implementation(project(":v1_21_3:", "reobf"))
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