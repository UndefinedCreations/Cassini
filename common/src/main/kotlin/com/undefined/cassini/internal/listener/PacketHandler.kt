package com.undefined.cassini.internal.listener

import com.undefined.cassini.internal.info.PacketClickInformation
import com.undefined.cassini.internal.info.PacketCloseInformation

/**
 * Handles any Cassini menu related server bound packets and custom events (such as dialog click).
 * This is overridden by the implementation of the core module.
 */
interface PacketHandler {
    fun onClick(clickInformation: PacketClickInformation)
    fun onClose(closeInformation: PacketCloseInformation)
}