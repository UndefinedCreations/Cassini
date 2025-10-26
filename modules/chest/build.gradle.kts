plugins {
    id("setup")
    id("shadow")
}

dependencies {
    compileOnly(libs.papermc)

    compileOnly(project(":core"))
    compileOnly(project(":common"))
}