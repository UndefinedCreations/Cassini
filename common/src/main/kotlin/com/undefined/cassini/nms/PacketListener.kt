package com.undefined.cassini.nms

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

abstract class PacketListener(val manager: PacketManager) : Listener