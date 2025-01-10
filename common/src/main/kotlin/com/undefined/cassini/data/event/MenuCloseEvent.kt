package com.undefined.cassini.data.event

import com.undefined.cassini.Menu
import org.bukkit.entity.Player

class MenuCloseEvent(val player: Player, val menu: Menu) : CassiniEvent(false)