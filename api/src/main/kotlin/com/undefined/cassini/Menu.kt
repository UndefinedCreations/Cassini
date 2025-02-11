package com.undefined.cassini

import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.manager.MenuManager
import com.undefined.cassini.nms.wrapper.MenuWrapper
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.jetbrains.annotations.ApiStatus

abstract class Menu<T : Menu<T>>(var title: Component, val optimization: MenuOptimization, val parent: Menu<*>?) {

    @Suppress("UNCHECKED_CAST")
    fun create(init: T.() -> Unit) { (this as T).init() }

    @ApiStatus.OverrideOnly
    open fun preinitialize(player: Player) {}

    @ApiStatus.OverrideOnly
    open fun initialize(player: Player) {}

    @ApiStatus.OverrideOnly
    open fun afterinitialize(player: Player) {}

    @ApiStatus.OverrideOnly
    open fun onOpen(player: Player) {}

    @ApiStatus.OverrideOnly
    open fun onClose(player: Player) {}

    protected inline fun <reified T : MenuWrapper> getWrapper(): T? {
        val id = MenuManager.menus.entries.firstOrNull { it.value == this }?.key ?: return null
        return MenuManager.wrappers[id] as? T
    }

    @Suppress("UNCHECKED_CAST")
    abstract class Builder<T, R : Menu<R>>(
        var title: Component,
        val optimization: MenuOptimization,
        val parent: Menu<*>?
    ) {
        protected var initializations: MutableList<Player.() -> (Unit)> = mutableListOf()
        protected var openActions: MutableList<Player.() -> (Unit)> = mutableListOf()
        protected var closeActions: MutableList<Player.() -> (Unit)> = mutableListOf()

        fun initialize(initialize: Player.() -> (Unit)): T = apply { initializations.add(initialize) } as T
        fun onOpen(open: Player.() -> (Unit)): T = apply { openActions.add(open) } as T
        fun onClose(close: Player.() -> (Unit)): T = apply { closeActions.add(close) } as T

        abstract fun build(): R
    }

}