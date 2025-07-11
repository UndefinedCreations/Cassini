package com.undefined.cassini.element.dialog.input

import com.google.gson.JsonObject

/**
 * Multi line options for [TextDialogInput].
 *
 * @param height Height of input. A value between `1` and `512`.
 * @param maxLines Optional positive integer. If present, limits maximum lines.
 */
data class MultiLineOptions(val height: Int, val maxLines: Int? = null) {
    fun toJson(): JsonObject = JsonObject().also { json ->
        json.addProperty("height", height)
        if (maxLines != null) json.addProperty("max_lines", maxLines)
    }
}