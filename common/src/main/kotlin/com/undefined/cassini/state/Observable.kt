package com.undefined.cassini.state

//enum class ObservableNotFoundStrategy {
//    WARNING,
//    SILENT
//}
//
//@Target(AnnotationTarget.ANNOTATION_CLASS)
//@Retention(AnnotationRetention.BINARY)
//@MustBeDocumented
//annotation class Observable(val strategy: ObservableNotFoundStrategy = ObservableNotFoundStrategy.WARNING)

//interface Observable<T : Any> {
//    fun update(observer: (T) -> Unit) {
//
//    }
//}

fun main() {
    val state = objectState(mutableListOf("test")) {

    }

    state.update {
        it.clear()
        it.add("potatoe")
    }
}