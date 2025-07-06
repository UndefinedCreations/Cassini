package com.undefined.cassini

import com.undefined.cassini.nms.NMSManager
import com.undefined.cassini.nms.PacketListenerImpl
import org.bukkit.plugin.java.JavaPlugin

object Cassini {

    /**
     * WARNING: Make sure this is only called once! Only use this if you don't use the plugin.
     */
    fun initialize(plugin: JavaPlugin) {
        NMSManager.nms.initializePacketListener(plugin, PacketListenerImpl)
    }

}