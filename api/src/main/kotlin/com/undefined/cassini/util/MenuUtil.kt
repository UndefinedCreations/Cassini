package com.undefined.cassini.util

import com.undefined.cassini.data.iterator.MenuIterator

fun menuIterator(block: () -> Iterable<Int>) = MenuIterator(block())
fun menuIterator(vararg range: Iterable<Int>) = MenuIterator(*range)