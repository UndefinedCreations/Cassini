package com.undefined.cassini.nms

import com.undefined.cassini.exception.UnsupportedVersionException
import com.undefined.cassini.internal.NMS
import com.undefined.cassini.internal.NMS1_21_4
import org.bukkit.Bukkit

object NMSManager {

    val nms: NMS by lazy { versions[version]?.let { it() } ?: throw UnsupportedVersionException(versions.keys) }
    private val version by lazy { Bukkit.getBukkitVersion().split("-")[0] }
    private val versions: Map<String, () -> NMS> = mapOf(
        "1.21.4" to { NMS1_21_4 },
    )

}