plugins {
    id("setup")
    id("shadow")
}

dependencies {
    compileOnly(libs.spigotmc)

    implementation(project(":nms:v1_21_4"))
    implementation(project(":common"))
}