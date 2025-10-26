plugins {
    id("setup")
}

dependencies {
    compileOnly(libs.papermc)
    compileOnly(platform(libs.reactor.bom))
    compileOnly(libs.reactor)
}