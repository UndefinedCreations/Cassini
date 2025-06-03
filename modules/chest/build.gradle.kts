plugins {
    id("setup")
    id("shadow")
}

dependencies {
    compileOnly(libs.spigotmc)

    compileOnly(project(":core"))
    compileOnly(project(":common"))
}