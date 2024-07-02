package com.ashrafunahid.imagebackgroundremover.Utils

import kotlinx.coroutines.runBlocking

fun <T> runBlockingInJava(block: suspend () -> T): T {
    return runBlocking { block() }
}