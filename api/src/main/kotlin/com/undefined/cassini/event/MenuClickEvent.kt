package com.undefined.cassini.event

import com.undefined.cassini.ContainerMenu
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class MenuClickEvent(val player: Player, val menu: ContainerMenu<*>, val slot: Int, val type: ClickType) : CassiniEvent()