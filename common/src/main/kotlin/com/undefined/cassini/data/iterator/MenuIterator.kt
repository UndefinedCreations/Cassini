package com.undefined.cassini.data.iterator

import com.undefined.cassini.data.item.Slot

class MenuIterator(private val slots: Array<Slot>) : ListIterator<Slot> {

	constructor(vararg slots: Slot) : this(slots.toTypedArray())
	constructor(vararg slots: IntRange) : this(slots.flatMap { it.toList() }.toTypedArray())

	private var index: Int = 0

	override fun hasNext(): Boolean = index < slots.size

	override fun hasPrevious(): Boolean = index > 0

	override fun next(): Slot {
		if (!hasNext()) throw NoSuchElementException()
		return slots[index++]
	}

	override fun nextIndex(): Int = index + 1

	override fun previous(): Slot {
		if (!hasPrevious()) throw NoSuchElementException()
		return slots[index--]
	}

	override fun previousIndex(): Int = index - 1

}