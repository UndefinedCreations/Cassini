package com.undefined.cassini.menu

/**
 * An enum class that dictates how the menu works on the client and server.
 */
enum class MenuOptimization {
    /**
     * The inventory and its contents only exists for the player, not on the server.
     */
    FASTEST,
    /**
     * The inventory's contents only exists for the player, not on the server. The inventory itself does exist on the server, just without the contents.
     */
    FAST,
    /**
     * The inventory and contents exist both on the client and server.
     */
    NORMAL
}