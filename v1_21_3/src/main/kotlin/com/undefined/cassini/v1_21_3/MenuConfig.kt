package com.undefined.cassini.v1_21_3

import com.undefined.cassini.Menu
import org.bukkit.entity.Player

data class MenuConfig(val player: Player, val menu: Menu, val id: Int, val modifySlots: Boolean)
