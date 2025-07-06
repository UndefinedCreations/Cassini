import com.undefinedcreations.echo.tasks.RemapTask

plugins {
    id("setup")
    id("com.undefinedcreations.echo")
}

dependencies {
    echo("1.21.7", printDebug = true)
    compileOnly(project(":common"))
}

tasks {
    remap {
        minecraftVersion("1.21.7")
        this.action(RemapTask.Action.MOJANG_TO_SPIGOT)
    }
}