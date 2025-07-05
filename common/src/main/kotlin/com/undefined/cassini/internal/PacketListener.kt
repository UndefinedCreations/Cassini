package com.undefined.cassini.internal

import com.undefined.cassini.internal.info.PacketClickInformation

/**
 * Handles any Cassini menu related serverbound packets. This is overriden by the implementation in the core module.
 */
interface PacketListener {
    fun onClick(clickInformation: PacketClickInformation)
}