package com.undefined.cassini.internal.listener

import org.bukkit.entity.Player
import java.util.UUID

interface DialogHandler {
    fun onDialogClick(player: Player, buttonUUID: UUID)
}