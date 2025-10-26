package com.undefined.cassini

import com.undefined.cassini.internal.NMSManager
import com.undefined.cassini.listener.PacketHandlerImpl
import org.bukkit.plugin.java.JavaPlugin

object CassiniConfig {

    lateinit var plugin: JavaPlugin

    private var initialized = false

    fun initialize(plugin: JavaPlugin) {
        if (initialized) return
        initialized = true

        this.plugin = plugin
        NMSManager.nms.initializePacketListener(plugin, PacketHandlerImpl)
        plugin.server.pluginManager.registerEvents(PacketHandlerImpl, plugin)
    }

}