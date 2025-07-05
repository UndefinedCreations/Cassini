package com.undefined.cassini

import com.undefined.cassini.nms.NMSManager
import org.bukkit.plugin.java.JavaPlugin

object Cassini {

    fun initialize(plugin: JavaPlugin) {
        NMSManager.nms.initializePacketListener(plugin)
    }

}