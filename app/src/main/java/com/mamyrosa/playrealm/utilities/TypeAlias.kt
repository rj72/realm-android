package com.mamyrosa.playrealm.utilities

import android.content.ContextWrapper
import android.view.View
import java.util.UUID


typealias VoidClosure = (() -> Unit)
typealias Closure<T> = ((T) -> Unit)
typealias PairClosure<T, U> = ((T, U) -> Unit)
typealias TripleClosure<T, U, V> = ((T, U, V) -> Unit)
typealias ClickEvent = View.OnClickListener
typealias ButtonClickEvent = View.OnClickListener

val randomUUID: String
    get() = UUID.randomUUID().toString()


inline fun <reified PARENT> View.getParentActivity(): PARENT? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is PARENT) {
            return context
        }
        context = context.baseContext
    }
    return null
}
