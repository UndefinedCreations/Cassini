package com.undefined.cassini.internal

import com.undefined.cassini.exception.UnsupportedVersionException
import com.undefined.cassini.menu.CassiniMenu
import org.bukkit.Bukkit
import java.util.*

object NMSManager {

    val nms: NMS by lazy { versions[version]?.let { it() } ?: throw UnsupportedVersionException(versions.keys) }
    val openMenus: HashMap<UUID, CassiniMenu<*, *>> = hashMapOf() // player uuid to menu

    private val version by lazy { Bukkit.getBukkitVersion().split("-")[0] }
    private val versions: Map<String, () -> NMS> = mapOf(
        "1.21.8" to { NMS1_21_8() },
        "1.21.7" to { NMS1_21_8() },
        "1.21.6" to { NMS1_21_8() },
    )

}