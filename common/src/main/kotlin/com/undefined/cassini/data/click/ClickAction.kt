package com.undefined.cassini.data.click

import com.undefined.cassini.data.CassiniContext

enum class ClickAction(val action: CassiniContext.() -> Unit) {
    BACK({ back() }),
    CLOSE({ close() }),
    CANCEL({ isCancelled = true }),
}