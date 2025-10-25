plugins {
    id("setup")
    id("shadow")
}

dependencies {
    compileOnly(libs.papermc)

    compileOnly(project(":modules:item:chest"))
}