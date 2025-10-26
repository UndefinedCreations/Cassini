package com.undefined.cassini.data

/**
 * Represents a menu type, mostly used for NMS purposes.
 */
enum class MenuType(val itemMenu: Boolean) {
    CHEST_9X1(true),
    CHEST_9X2(true),
    CHEST_9X3(true),
    CHEST_9X4(true),
    CHEST_9X5(true),
    CHEST_9X6(true),
    DROPPER(true),
    ANVIL(true),
    DIALOG(false);

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