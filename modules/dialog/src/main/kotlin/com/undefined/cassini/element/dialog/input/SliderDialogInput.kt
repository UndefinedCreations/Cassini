package com.undefined.cassini.element.dialog.input

import net.kyori.adventure.text.Component

/**
 * A [DialogInputElement] that represents a number slider in a dialog.
 *
 * @param key A string identifier of value used when submitting data, must be a valid template argument (letters, digits and _).
 * @param label The dialog input label.
 * @param start The minimum number of the slider.
 * @param end The maximum number of the slider.
 * @param step The step size. If present, only values of `initial + <any_integer> * step` will be allowed. If absent, any value is allowed.
 * @param initial The initial value of the slider. Default to the middle of the range.
 * @param labelFormat A translation key to be used for building label. Defaults to options.generic.value (`%s: %s` in english).
 * @param width The width of the input. A value between `1` and `1024`, defaults to `200`.
 */
class SliderDialogInput(
    key: String,
    label: Component,
    val start: Float,
    val end: Float,
    val step: Float? = null,
    val initial: Float = (start + end) / 2,
    val labelFormat: String = "options.generic_value",
    val width: Int = DEFAULT_WIDTH,
) : DialogInputElement("minecraft:number_range", key, label) {

    override fun toJson() = super.toJson().also { json ->
        json.addProperty("initial", initial)
        json.addProperty("label_format", labelFormat)
        json.addProperty("width", width)
        json.addProperty("start", start)
        json.addProperty("end", end)
        if (step != null) json.addProperty("step", step)
        json.addProperty("initial", initial)
    }

    companion object {
        const val DEFAULT_WIDTH = 200
    }

}