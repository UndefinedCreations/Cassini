package com.undefined.cassini

import com.undefined.cassini.manager.MenuManager
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

object Cassini {

    lateinit var plugin: JavaPlugin
        private set

    var modifySlots = true
        private set
    var miniMessage: MiniMessage = MiniMessage.miniMessage()
        private set

    fun initialize(plugin: JavaPlugin): Cassini {
        if (::plugin.isInitialized) return this
        this.plugin = plugin
        Bukkit.getPluginManager().registerEvents(MenuManager.getPacketListener(), plugin)
        return this
    }

    fun setModifySlots(modifySlots: Boolean): Cassini = apply {
        this.modifySlots = modifySlots
    }

    fun setMiniMessage(miniMessage: MiniMessage): Cassini = apply {
        this.miniMessage = miniMessage
    }

}