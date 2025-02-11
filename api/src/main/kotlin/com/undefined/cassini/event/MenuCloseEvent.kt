package com.undefined.cassini.event

import com.undefined.cassini.ContainerMenu
import org.bukkit.entity.Player

class MenuCloseEvent(val player: Player, val menu: ContainerMenu<*>) : CassiniEvent(false)