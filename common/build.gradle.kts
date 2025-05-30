plugins {
    id("setup")
}

dependencies {
    compileOnly(libs.spigotmc)
    compileOnly(platform(libs.reactor.bom))
    compileOnly(libs.reactor)
}