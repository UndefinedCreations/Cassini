import org.gradle.jvm.tasks.Jar
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("setup")
    id("com.gradleup.shadow")
    `maven-publish`
    id("org.jetbrains.dokka") version "2.0.0"
}

dependencies {
    compileOnly(libs.spigotmc)

    api(project(":nms"))

    dokkaPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:2.0.0")
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
    tasks.withType<DokkaTask>()

    tasks.register<Jar>("sourceJar") {
        archiveClassifier = "sources"
        from(sourceSets.main.get().allSource)
    }
}

val packageJavadoc by tasks.registering(Jar::class) {
    group = "stellar"
    archiveClassifier = "javadoc"

    dependsOn(tasks.dokkaJavadocCollector)
    from(tasks.dokkaJavadocCollector.flatMap { it.outputDirectory })
}

val packageSources by tasks.registering(Jar::class) {
    group = "stellar"
    archiveClassifier = "sources"
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(subprojects.map { it.sourceSets.main.get().allSource })
}

publishing {
    publications {
        create<MavenPublication>("kotlin") {
            artifactId = rootProject.name
            from(components["shadow"])
            artifact(packageJavadoc)
            artifact(packageSources)

            pom {
                name = "Cassini"
                description = "Cassini is a simplistic, yet very extensive Menu library."
                url = "https://www.github.com/UndefinedCreations/Cassini"
                licenses {
                    license {
                        name = "MIT"
                        url = "https://mit-license.org/"
                        distribution = "https://mit-license.org/"
                    }
                }
                developers {
                    developer {
                        id = "lutto"
                        name = "StillLutto"
                        url = "https://github.com/StillLutto/"
                    }
                }
                scm {
                    url = "https://github.com/UndefinedCreations/Cassini/"
                    connection = "scm:git:git://github.com/UndefinedCreations/Cassini.git"
                    developerConnection = "scm:git:ssh://git@github.com/UndefinedCreations/Cassini.git"
                }
            }
        }
    }
    repositories {
        maven {
            name = "undefined-releases"
            url = uri("https://repo.undefinedcreations.com/releases")
            credentials(PasswordCredentials::class) {
                username = System.getenv("MAVEN_NAME") ?: property("mavenUser").toString()
                password = System.getenv("MAVEN_SECRET") ?: property("mavenPassword").toString()
            }
        }
        maven {
            name = "undefined-snapshots"
            url = uri("https://repo.undefinedcreations.com/snapshots")
            credentials(PasswordCredentials::class) {
                username = System.getenv("MAVEN_NAME") ?: property("mavenUser").toString()
                password = System.getenv("MAVEN_SECRET") ?: property("mavenPassword").toString()
            }
        }
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks {
    shadowJar {
        minimize {
            exclude("**/kotlin/**")
            exclude("**/intellij/**")
            exclude("**/jetbrains/**")
        }
        archiveClassifier = ""
    }
}