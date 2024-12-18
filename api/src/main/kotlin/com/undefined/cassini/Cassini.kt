package com.undefined.cassini

import com.undefined.cassini.exception.UnsupportedVersionException
import com.undefined.cassini.handlers.PacketListener
import com.undefined.cassini.util.NMSVersion
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

object Cassini {

    lateinit var plugin: JavaPlugin
        private set

    private val listeners: Map<String, PacketListener> = mapOf(
        "1.21.3" to com.undefined.cassini.v1_21_3.PacketListener
    )

    val openMenus: HashMap<UUID, Menu> = hashMapOf()

    fun initialize(plugin: JavaPlugin) {
        if (::plugin.isInitialized) return
        Bukkit.getPluginManager().registerEvents(listeners[NMSVersion.version] ?: throw UnsupportedVersionException(), plugin)
        this.plugin = plugin
    }
}