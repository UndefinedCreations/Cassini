package com.undefined.cassini.menu

import com.undefined.cassini.internal.NMSManager
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.jetbrains.annotations.ApiStatus
import java.util.*

/**
 * This is the base for any menu, although it's no menu in itself.
 *
 * @param title The menu title.
 * @param settings The menu settings. Contains information on the menu's functionality.
 * @param parent The menu this was opened from, if any. This menu might use it in some cases to go back to the previously opened menu.
 *
 * @param T The extending menu itself.
 * @param C The [MenuSettings] type.
 */
abstract class CassiniMenu<T : CassiniMenu<T, *>, C : MenuSettings>(
    val title: Component,
    val parent: CassiniMenu<*, *>?,
) {

    val viewers: MutableList<UUID> = mutableListOf() // player uuid
    abstract val settings: C

    open fun open(player: Player) {
        if (player.uniqueId in viewers) return
        viewers.add(player.uniqueId)
        NMSManager.openMenus[player.uniqueId] = this
    }

    open fun update() {
        for (viewer in viewers) update(viewer)
    }

    open fun update(viewer: UUID) {
        throw UnsupportedOperationException("Update method not available for ${this::class.simpleName}.")
    }

    @ApiStatus.OverrideOnly
    open fun initialize(player: Player) {}

}