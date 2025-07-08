package com.undefined.cassini.util.delegate

import org.bukkit.Bukkit
import java.util.UUID
import kotlin.reflect.KProperty

class UpdatablePlayerMapDelegate<V>(var state: MutableMap<UUID, V>) : MutableMap<UUID, V> by state {
    fun onUpdate(player: UUID) {
        Bukkit.getPlayer(player)?.updateCommands()
    }
    override fun put(key: UUID, value: V): V? = state.put(key, value).also { onUpdate(key) }
    override fun remove(key: UUID): V? = state.remove(key).also { onUpdate(key) }
    operator fun getValue(thisRef: Any?, property: KProperty<*>): UpdatablePlayerMapDelegate<V> = this
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: UpdatablePlayerMapDelegate<V>) {
        state = value
        throw IllegalStateException("State has been changed!")
    }
}