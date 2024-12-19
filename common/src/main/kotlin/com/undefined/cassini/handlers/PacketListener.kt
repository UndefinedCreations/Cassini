package com.undefined.cassini.handlers

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

interface PacketListener : Listener {
    var plugin: JavaPlugin
}