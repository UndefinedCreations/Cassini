package com.undefined.cassini

import com.undefined.cassini.listener.PacketHandlerImpl
import com.undefined.cassini.internal.NMSManager
import org.bukkit.plugin.java.JavaPlugin

object CassiniConfig {

    lateinit var plugin: JavaPlugin

    /**
     * WARNING: Make sure this is only called once! Only use this if you don't use the plugin.
     */
    fun initialize(plugin: JavaPlugin) {
        this.plugin = plugin
        NMSManager.nms.initializePacketListener(plugin, PacketHandlerImpl)
    }

}