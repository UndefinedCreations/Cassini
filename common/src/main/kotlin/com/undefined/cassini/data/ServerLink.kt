package com.undefined.cassini.data

import net.kyori.adventure.text.Component
import java.net.URI

/**
 * Represents a server link.
 *
 * @param url A valid URL to be opened.
 */
sealed class ServerLink(val url: URI) {

    /**
     * Represents a known server link, as defined [here](https://minecraft.wiki/w/Pause_menu#Server_links).
     * Known server links have predefined labels.
     *
     * @param type The link type.
     * @param url A valid URL to be opened.
     */
    class KnownLink(val type: Type, url: URI) : ServerLink(url)

    /**
     * Represents a custom server link. Custom server links allow for custom labels.
     *
     * @param label The label to be displayed.
     */
    class Custom(val label: Component, url: URI) : ServerLink(url)

    /**
     * A known server link type.
     */
    enum class Type {
        BUG_REPORT,
        COMMUNITY_GUIDELINES,
        SUPPORT,
        STATUS,
        FEEDBACK,
        COMMUNITY,
        WEBSITE,
        FORUMS,
        NEWS,
        ANNOUNCEMENTS,
    }

}