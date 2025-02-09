package com.undefined.cassini.extensions

import java.lang.IndexOutOfBoundsException

enum class AnvilSlot {
    LEFT,
    RIGHT,
    OUTPUT;

    companion object {
        fun fromSlotNumber(slot: Int): AnvilSlot = when (slot) {
            0 -> LEFT
            1 -> RIGHT
            2 -> OUTPUT
            else -> throw IndexOutOfBoundsException("No anvil slot found with slot $slot!")
        }
    }
}