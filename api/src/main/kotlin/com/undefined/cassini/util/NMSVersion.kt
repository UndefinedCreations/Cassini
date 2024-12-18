package com.undefined.cassini.util

import org.bukkit.Bukkit

object NMSVersion {
    val version by lazy { Bukkit.getBukkitVersion().split("-").first() }
}