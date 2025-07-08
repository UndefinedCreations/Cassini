package com.undefined.cassini.internal

import com.undefined.cassini.exception.UnsupportedVersionException
import com.undefined.cassini.menu.CassiniMenu
import com.undefined.cassini.util.delegate.UpdatablePlayerMapDelegate
import org.bukkit.Bukkit

object NMSManager {

    val nms: NMS by lazy { versions[version]?.let { it() } ?: throw UnsupportedVersionException(versions.keys) }
    val openMenus: UpdatablePlayerMapDelegate<CassiniMenu<*, *>> by UpdatablePlayerMapDelegate(hashMapOf()) // player uuid to menu

    private val version by lazy { Bukkit.getBukkitVersion().split("-")[0] }
    private val versions: Map<String, () -> NMS> = mapOf(
        "1.21.7" to { NMS1_21_7 },
    )

}