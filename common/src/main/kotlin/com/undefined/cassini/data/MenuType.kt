package com.undefined.cassini.data

/**
 * Represents a menu type, mostly used for NMS purposes.
 */
enum class MenuType {
    CHEST_9X1,
    CHEST_9X2,
    CHEST_9X3,
    CHEST_9X4,
    CHEST_9X5,
    CHEST_9X6,
    DROPPER,
    ANVIL;

    companion object {
        fun rowsToChestMenu(rows: Int): MenuType = when (rows) {
            1 -> CHEST_9X1
            2 -> CHEST_9X2
            3 -> CHEST_9X3
            4 -> CHEST_9X4
            5 -> CHEST_9X5
            6 -> CHEST_9X6
            else -> throw IllegalArgumentException("Rows must be between 1 to 6!")
        }
    }

}