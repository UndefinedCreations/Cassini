plugins {
    `java-library`
    id("com.gradleup.shadow")
}

tasks {
    shadowJar {
        exclude("**/kotlin/**")
        exclude("**/intellij/**")
        exclude("**/jetbrains/**")
        archiveClassifier = project.name
        archiveFileName = "${rootProject.name}-${project.version}-${project.name}.jar"
    }
}