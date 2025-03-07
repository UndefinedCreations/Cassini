package com.undefined.cassini.event

import com.undefined.cassini.Menu
import org.bukkit.entity.Player

class MenuOpenEvent(val player: Player, val menu: Menu<*>) : CassiniEvent(false)