plugins {
    id("com.gradle.develocity") version("4.2.2")
}

rootProject.name = "cassini"
include(
    ":common",
    ":core",
    ":modules:chest",
    ":modules:dialog",
    ":nms:v1_21_8",
    ":server",
)