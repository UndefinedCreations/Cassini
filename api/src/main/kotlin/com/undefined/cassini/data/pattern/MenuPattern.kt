package com.undefined.cassini.data.pattern

import com.undefined.cassini.Menu

interface MenuPattern<T : Menu<*>> {
    fun apply(menu: T)
    fun createMenu(menu: T, action: T.() -> Unit) = menu.action()
}