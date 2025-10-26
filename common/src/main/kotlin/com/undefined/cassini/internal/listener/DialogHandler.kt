package com.undefined.cassini.internal.listener

import io.papermc.paper.dialog.DialogResponseView
import net.kyori.adventure.key.Key
import org.bukkit.entity.Player

interface DialogHandler {
    fun onCustomClickAction(player: Player, key: Key, payload: DialogResponseView)
}