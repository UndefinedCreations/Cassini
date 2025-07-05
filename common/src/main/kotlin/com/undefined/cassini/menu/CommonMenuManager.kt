package com.undefined.cassini.menu

import org.bukkit.inventory.ItemStack
import java.util.UUID

/**
 * Contains and stores information that should be shared throughout the entire project, including NMS.
 */
object CommonMenuManager {

    const val CONTAINER_ID = 1
    const val SYNC_ID = 1

    /**
     * Contains a list of all players, and what item they are carrying.
     * This only applies to players in a Cassini created menu.
     */
    val carriedItems: HashMap<UUID, ItemStack> = hashMapOf()

}