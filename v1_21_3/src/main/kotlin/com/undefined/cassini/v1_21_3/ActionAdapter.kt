package com.undefined.cassini.v1_21_3

import com.undefined.cassini.Menu
import com.undefined.cassini.data.click.ClickActionType
import com.undefined.cassini.data.item.GUIItem
import org.bukkit.entity.Player

object ActionAdapter {

    fun handleClickActions(player: Player, item: GUIItem, menu: Menu): Boolean {
        val serverPlayer = player.serverPlayer()
        for (action in item.actions) {
            println(action.name)
            when (action) {
                ClickActionType.RETURN -> menu.parent?.let { MenuHandler.setContents(player, menu) } ?: serverPlayer.closeContainer()
                ClickActionType.CLOSE -> serverPlayer.closeContainer()
                ClickActionType.CANCEL -> return true
            }
        }
        return false
    }

}