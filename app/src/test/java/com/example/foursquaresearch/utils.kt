package com.example.foursquaresearch

import org.mockito.Mockito

/**
 *  a workaround for mockito on kotlin for nullable objects.
 */
fun <T> anyKotlin(): T {
    Mockito.any<T>()
    return uninitialized()
}

@Suppress("UNCHECKED_CAST")
private fun <T> uninitialized(): T = null as T
