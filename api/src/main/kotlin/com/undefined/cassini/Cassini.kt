package com.undefined.cassini

import com.undefined.cassini.exception.UnsupportedVersionException
import com.undefined.cassini.handlers.PacketListener
import com.undefined.cassini.util.NMSVersion
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

object Cassini {

    lateinit var plugin: JavaPlugin
        private set

    var MODIFY_SLOTS = true

    private val listeners: Map<String, PacketListener> = mapOf(
        "1.21.3" to com.undefined.cassini.v1_21_3.PacketListener
    )

    fun initialize(plugin: JavaPlugin): Cassini {
        if (::plugin.isInitialized) return this
        this.plugin = plugin
        val listener = listeners[NMSVersion.version] ?: throw UnsupportedVersionException()
        listener.plugin = plugin
        Bukkit.getPluginManager().registerEvents(listener, plugin)
        Bukkit.getPluginManager().registerEvents(MenuManager, plugin)
        return this
    }

    fun setModifySlots(modifySlots: Boolean): Cassini {
        MODIFY_SLOTS = modifySlots
        return this
    }

}