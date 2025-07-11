package com.undefined.cassini.internal.listener

import org.bukkit.NamespacedKey
import org.bukkit.entity.Player

interface DialogHandler {
    fun onCustomClickAction(player: Player, key: NamespacedKey, payload: String)
}