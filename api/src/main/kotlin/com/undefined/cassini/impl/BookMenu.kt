package com.undefined.cassini.impl

import com.undefined.cassini.Cassini
import com.undefined.cassini.Menu
import com.undefined.cassini.data.MenuOptimization
import com.undefined.cassini.data.book.BookPage
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

abstract class BookMenu(
    optimization: MenuOptimization = MenuOptimization.NORMAL,
    parent: Menu<*>? = null
) : Menu<BookMenu>(Component.empty(), optimization, parent) {

    val pages: MutableList<BookPage> = mutableListOf()

    fun addPages(vararg pages: BookPage): BookMenu = apply { this.pages.addAll(pages.asList()) }
    fun addPages(pages: Collection<BookPage>): BookMenu = apply { this.pages.addAll(pages) }
    fun addPages(pages: Iterable<BookPage>): BookMenu = apply { this.pages.addAll(pages) }
    fun addPages(pages: Sequence<BookPage>): BookMenu = apply { this.pages.addAll(pages.toList()) }

    fun addPage(page: BookPage): BookMenu = apply { pages.add(page) }
    fun addPage(vararg rows: Component): BookMenu = addPage(BookPage(rows.asList()))
    fun addPage(rows: Collection<Component>): BookMenu = addPage(BookPage(rows))
    fun addPage(rows: Iterable<Component>): BookMenu = addPage(BookPage(rows))
    fun addPage(rows: Sequence<Component>): BookMenu = addPage(BookPage(rows.toList()))

    fun clearPages(): BookMenu = apply { pages.clear() }

    fun setPage(i: Int, page: BookPage): BookMenu = apply { pages[i] = page }
    fun setPage(i: Int, vararg rows: Component): BookMenu = setPage(i, BookPage(rows.asList()))
    fun setPage(i: Int, rows: Collection<Component>): BookMenu = setPage(i, BookPage(rows))
    fun setPage(i: Int, rows: Iterable<Component>): BookMenu = setPage(i, BookPage(rows))
    fun setPage(i: Int, rows: Sequence<Component>): BookMenu = setPage(i, BookPage(rows.toList()))

    fun getPage(i: Int): BookPage? = pages.getOrNull(i)

    companion object {
        fun builder(optimization: MenuOptimization = MenuOptimization.NORMAL, parent: Menu<*>? = null): Builder = Builder(optimization, parent)
    }

    class Builder(
        optimization: MenuOptimization = MenuOptimization.NORMAL,
        parent: Menu<*>? = null
    ): Menu.Builder<Builder, BookMenu>(Component.empty(), optimization, parent) {

        val pages: MutableList<BookPage> = mutableListOf()

        fun addPages(vararg pages: BookPage): Builder = apply { this.pages.addAll(pages.asList()) }
        fun addPages(pages: Collection<BookPage>): Builder = apply { this.pages.addAll(pages) }
        fun addPages(pages: Iterable<BookPage>): Builder = apply { this.pages.addAll(pages) }
        fun addPages(pages: Sequence<BookPage>): Builder = apply { this.pages.addAll(pages.toList()) }

        fun addPage(page: BookPage): Builder = apply { pages.add(page) }
        fun addAdvancedPage(vararg components: Component): Builder = addPage(BookPage(components.asList()))
        fun addAdvancedPage(components: Collection<Component>): Builder = addPage(BookPage(components))
        fun addAdvancedPage(components: Iterable<Component>): Builder = addPage(BookPage(components))
        fun addAdvancedPage(components: Sequence<Component>): Builder = addPage(BookPage(components.toList()))

        fun addPage(vararg components: String): Builder = addPage(BookPage(components.map { Cassini.miniMessage.deserialize(it) }))
        fun addPage(components: Collection<String>): Builder = addPage(BookPage(components.map { Cassini.miniMessage.deserialize(it) }))
        fun addPage(components: Iterable<String>): Builder = addPage(BookPage(components.map { Cassini.miniMessage.deserialize(it) }))
        fun addPage(components: Sequence<String>): Builder = addPage(BookPage(components.map { Cassini.miniMessage.deserialize(it) }.toList()))

        fun clearPages(): Builder = apply { pages.clear() }

        fun setPage(i: Int, page: BookPage): Builder = apply { pages[i] = page }
        fun setPage(i: Int, vararg rows: Component): Builder = setPage(i, BookPage(rows.asList()))
        fun setPage(i: Int, rows: Collection<Component>): Builder = setPage(i, BookPage(rows))
        fun setPage(i: Int, rows: Iterable<Component>): Builder = setPage(i, BookPage(rows))
        fun setPage(i: Int, rows: Sequence<Component>): Builder = setPage(i, BookPage(rows.toList()))

        fun getPage(i: Int): BookPage? = pages.getOrNull(i)

        override fun build(): BookMenu =
            object : BookMenu(optimization, parent) {
                override fun initialize(player: Player) {
                    for (initialization in initializations) initialization(player)
                    addPages(this@Builder.pages)
                }
                override fun onOpen(player: Player) {
                    for (action in openActions) action(player)
                }
                override fun onClose(player: Player) {
                    for (action in closeActions) action(player)
                }
            }
    }

}