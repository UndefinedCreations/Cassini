package com.undefined.cassini.data.dialog

/**
 * Represents an operation performed on a dialog after click or submit actions.
 */
enum class AfterAction(val tagName: String) {
    CLOSE("close"),
    NONE("none"),
    WAIT_FOR_RESPONSE("wait_for_response"),
}