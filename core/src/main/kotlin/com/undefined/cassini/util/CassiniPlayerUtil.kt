package com.undefined.cassini.util

import com.undefined.cassini.menu.CassiniMenu
import org.bukkit.entity.Player

fun Player.openMenu(menu: CassiniMenu<*, *>) = menu.open(this)