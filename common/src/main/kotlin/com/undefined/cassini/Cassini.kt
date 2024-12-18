package com.undefined.cassini

import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

object Cassini {

    var plugin: JavaPlugin? = null
    val openMenus: HashMap<UUID, Menu> = hashMapOf()

    fun initialize(plugin: JavaPlugin) {
        this.plugin?.let { this.plugin = plugin } ?: return
    }

}