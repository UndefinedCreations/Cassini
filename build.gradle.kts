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

val versionVar = "0.0.6"
val groupIdVar = "com.undefined"

group = "com.undefined"
version = "0.0.1"

publishing {
    repositories {
        maven {
            name = "UndefinedCreation"
            url = uri("https://repo.undefinedcreation.com/releases")
            credentials(PasswordCredentials::class) {
                username = System.getenv("MAVEN_NAME")
                password = System.getenv("MAVEN_SECRET")
            }
        }
    }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    group = groupIdVar
    version = versionVar

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.codemc.io/repository/nms/")
        maven {
            name = "sonatype"
            url = uri("https://oss.sonatype.org/content/groups/public/")
        }
    }

    publishing {
        publications {
            register<MavenPublication>("maven") {
                groupId = groupIdVar
                artifactId = "cassini"
                version = versionVar

                from(components["java"])
            }
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation("net.kyori:adventure-api:4.17.0")
        implementation("net.kyori:adventure-platform-bukkit:4.3.4")
        implementation("net.kyori:adventure-text-minimessage:4.17.0")
        implementation("net.kyori:adventure-text-serializer-legacy:4.17.0")
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
        archiveClassifier.set("mapped")
        archiveFileName.set("${project.name}-${project.version}.jar")
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "21"
    }
}


kotlin {
    jvmToolchain(21)
}