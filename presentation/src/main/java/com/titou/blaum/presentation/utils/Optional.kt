package com.titou.blaum.presentation.utils

data class Optional<T>(val value: T?)
fun <T> T?.asOptional() = Optional(this)
