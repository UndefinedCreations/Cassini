package com.undefined.cassini.data.click

enum class ClickAction(val action: ClickData<*>.() -> Unit) {
    BACK({ back() }),
    CLOSE({ close() }),
    CANCEL({ isCancelled = true }),
}