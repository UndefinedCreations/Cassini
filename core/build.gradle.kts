plugins {
    id("setup")
}

dependencies {
    compileOnly(libs.spigotmc)

    api(project(":common"))
}