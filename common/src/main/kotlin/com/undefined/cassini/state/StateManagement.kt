@file:JvmName("StateManagement")
package com.undefined.cassini.state

// These values should also be updated in the Container class.
@JvmOverloads
fun <T : Any> state(initialState: T, observer: (T) -> Unit = {}): PrimitiveStateObserver<T> = PrimitiveStateObserver(initialState).apply { observe(observer) }

@JvmOverloads
fun <T : Any> listState(initialState: MutableCollection<T>, observer: (MutableCollection<T>) -> Unit = {}): ListStateObserver<T> = ListStateObserver(initialState).apply { observe(observer) } // TODO IMPROVED!

@JvmOverloads
fun <T : Any> objectState(initialState: T, observer: (T) -> Unit = {}): ObjectStateObserver<T> = ObjectStateObserver(initialState).apply { observe(observer) }