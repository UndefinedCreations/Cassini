plugins {
    id("setup")
    id("shadow")
}

dependencies {
    compileOnly(libs.papermc)

    implementation(project(":nms:v1_21_8"))
    implementation(project(":common"))
}