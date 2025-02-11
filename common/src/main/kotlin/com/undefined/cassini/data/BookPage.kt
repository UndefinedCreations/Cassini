package com.undefined.cassini.data

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer
import net.md_5.bungee.api.chat.BaseComponent

data class BookPage(val text: Component) {

    constructor(text: Iterable<Component>) : this(Component.join(JoinConfiguration.noSeparators(), text))

    fun toBungee(): Array<BaseComponent> = BungeeComponentSerializer.get().serialize(text)

    companion object {
        fun builder(): Builder = Builder()
    }

    class Builder {
        val text: Component = Component.empty()

        fun addComponent(components: Iterable<Component>): Builder = apply { for (component in components) text.append(component) }
        fun addComponent(vararg components: Component): Builder = addComponent(components.asList())
        fun addComponent(components: Sequence<Component>): Builder = addComponent(components.toList())
        fun add(strings: Iterable<String>): Builder = apply { for (string in strings) text.append(MiniMessage.miniMessage().deserialize(string)) }
        fun add(vararg strings: String): Builder = add(strings.asList())
        fun add(strings: Sequence<String>): Builder = add(strings.toList())
        fun newLine(): Builder = apply { text.appendNewline() }

        fun build(): BookPage = BookPage(text)
    }

}