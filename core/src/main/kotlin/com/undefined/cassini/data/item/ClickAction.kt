package com.undefined.cassini.data.item

enum class ClickAction(val action: ClickData<*>.() -> Unit) {
    BACK({ back() }),
    CLOSE({ close() }),
    CANCEL({ isCancelled = true }),
}