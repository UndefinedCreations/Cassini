package com.undefined.cassini

import com.undefined.cassini.manager.MenuManager
import org.bukkit.scheduler.BukkitRunnable

object CassiniRunnable : BukkitRunnable() {
	var isRunning = false
	override fun run() {
		isRunning = true

		for ((_, menu) in MenuManager.menus) {
			menu.onTick()
		}
	}
}