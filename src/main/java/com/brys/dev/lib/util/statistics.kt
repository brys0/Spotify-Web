package com.brys.dev.lib.util



object statistics {
    object memStats {
        val max = getSize(Runtime.getRuntime().maxMemory())
        val total = getSize(Runtime.getRuntime().totalMemory())
        val available = getSize(Runtime.getRuntime().freeMemory())
        val used = getSize(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
    }
    object cpu {
        val liveThreads = Thread.getAllStackTraces().size
        val parkedThreads = Thread.getAllStackTraces().size - Thread.activeCount()
    }
}