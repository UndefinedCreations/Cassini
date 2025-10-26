plugins {
    id("setup")
    id("shadow")
}

dependencies {
    compileOnly(libs.papermc)
    compileOnly(platform(libs.reactor.bom))
    compileOnly(libs.reactor)

    implementation(project(":nms:v1_21_8"))
    implementation(project(":common"))
}