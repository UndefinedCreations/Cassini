package com.undefined.cassini.data.pattern

import com.undefined.cassini.ContainerMenu

interface MenuPattern<T : ContainerMenu<*>> {
    fun apply(menu: T)
    fun createMenu(menu: T, action: T.() -> Unit) = menu.action()
}