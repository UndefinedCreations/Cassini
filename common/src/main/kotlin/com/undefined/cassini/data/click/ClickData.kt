package com.undefined.cassini.data.click

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

data class ClickData(val player: Player, val type: ClickType)