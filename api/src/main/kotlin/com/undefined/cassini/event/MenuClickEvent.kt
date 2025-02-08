package com.undefined.cassini.event

import com.undefined.cassini.Menu
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class MenuClickEvent(val player: Player, val menu: Menu<*>, val slot: Int, val type: ClickType) : CassiniEvent()