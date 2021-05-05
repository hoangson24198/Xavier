package com.hoangson.xavier.core.util

@kotlin.annotation.Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class LayoutResId(val layout: Int = -1){
    companion object {
        var LAYOUT_NOT_DEFINED = -1
    }
}