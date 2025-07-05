plugins {
    id("setup")
    id("shadow")
}

dependencies {
    compileOnly(libs.spigotmc)

    implementation(project(":nms:v1_21_7"))
    implementation(project(":common"))
}