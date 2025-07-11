package com.undefined.cassini.data.dialog

import com.google.gson.JsonObject
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer

// TODO-DOCS
data class DialogListOption(val id: String, val display: Component, val initial: Boolean) {
    fun toJson(): JsonObject = JsonObject().also { json ->
        json.addProperty("id", id)
        json.add("display", GsonComponentSerializer.gson().serializeToTree(display))
        json.addProperty("initial", initial)
    }
}