package com.undefined.cassini.internal.info

import org.bukkit.entity.Player

data class PacketClickInformation(val player: Player, val slot: Short)