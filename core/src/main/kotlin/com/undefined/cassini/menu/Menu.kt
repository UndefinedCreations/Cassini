package com.undefined.cassini.menu

import com.undefined.cassini.data.MenuType
import com.undefined.cassini.internal.NMSManager
import com.undefined.cassini.menu.pattern.MenuPattern
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
 * @param type The type of the menu.
 *
 * @param T The extending menu itself.
 * @param C The [MenuSettings] type.
 */
abstract class Menu<T : Menu<T, *>, C : MenuSettings>(
    val title: Component,
    val parent: Menu<*, *>?,
    val type: MenuType,
) {

    val viewers: MutableSet<UUID> = mutableSetOf() // player uuid
    abstract val settings: C

    open fun open(player: Player) {
        if (player.uniqueId !in viewers) viewers.add(player.uniqueId)
        NMSManager.openMenus[player.uniqueId] = this
    }

    /**
     * Update this container for all its viewers.
     */
    open fun update() {
        for (viewer in viewers) update(viewer)
    }

    open fun update(viewer: UUID) {
        throw UnsupportedOperationException("Update method not available for ${this::class.simpleName}.")
    }

    /**
     * Apply a [com.undefined.cassini.menu.pattern.MenuPattern] to this menu.
     */
    @Suppress("UNCHECKED_CAST")
    fun applyPattern(pattern: MenuPattern<T>) = pattern.apply(this as T)

    /**
     * Create and apply a [com.undefined.cassini.menu.pattern.MenuPattern] to this menu.
     */
    @Suppress("UNCHECKED_CAST")
    fun createPattern(action: T.() -> Unit) = MenuPattern.create(action).apply(this as T)

    @ApiStatus.OverrideOnly
    open fun initialize(player: Player) {}

}