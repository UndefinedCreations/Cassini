package com.undefined.cassini.menu.pattern

import com.undefined.cassini.menu.Menu

interface MenuPattern<T : Menu<*, *>> {
    fun apply(menu: T)
    fun createMenu(menu: T, action: T.() -> Unit) = menu.action()

    companion object {
        fun <T : Menu<*, *>> create(action: T.() -> Unit): MenuPattern<T> = object : MenuPattern<T> {
            override fun apply(menu: T) = createMenu(menu, action)
        }
    }
}