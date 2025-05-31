plugins {
    id("setup")
}

dependencies {
    compileOnly(libs.spigotmc)

    compileOnly(project(":core"))
    compileOnly(project(":common"))
}