package com.undefined.cassini.exception

class InvalidSlotException(slots: List<Int>) : Exception("[${slots}] is a invalid slot to place a item.") {
    constructor(range: IntRange): this(range.toList())
    constructor(vararg slots: Int): this(slots.toList())
}