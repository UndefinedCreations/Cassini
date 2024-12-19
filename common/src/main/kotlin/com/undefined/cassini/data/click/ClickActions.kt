package com.undefined.cassini.data.click

import com.undefined.cassini.data.CassiniContext

object ClickActions {
    val BACK: CassiniContext.() -> Unit = { back() }
    val CLOSE: CassiniContext.() -> Unit = { close() }
    val CANCEL: CassiniContext.() -> Unit = { isCancelled = true }
}