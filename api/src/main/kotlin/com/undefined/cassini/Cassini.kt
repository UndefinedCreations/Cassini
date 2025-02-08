package com.undefined.cassini

import com.undefined.cassini.exception.UnsupportedVersionException
import com.undefined.cassini.nms.PacketListener
import com.undefined.cassini.v1_21_3.PacketListener1_21_3
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

object Cassini {

    lateinit var plugin: JavaPlugin
        private set

    var modifySlots = true

    fun initialize(plugin: JavaPlugin): Cassini {
        if (::plugin.isInitialized) return this
        this.plugin = plugin
        Bukkit.getPluginManager().registerEvents(MenuManager.getPacketListener(), plugin)
        Bukkit.getPluginManager().registerEvents(MenuManager, plugin)
        return this
    }

    fun setModifySlots(modifySlots: Boolean): Cassini {
        this.modifySlots = modifySlots
        return this
    }

}