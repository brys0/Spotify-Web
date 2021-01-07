package com.brys.dev.lib.util


fun getSize(size: Long): String {
    val n: Long = 1024
    var s = ""
    val kb = size.toDouble() / n
    val mb = kb / n
    val gb = mb / n
    val tb = gb / n
    if (size < n) {
        s = "$size Bytes"
    } else if (size >= n && size < n * n) {
        s = String.format("%.2f", kb) + " KB"
    } else if (size >= n * n && size < n * n * n) {
        s = String.format("%.2f", mb) + " MB"
    } else if (size >= n * n * n && size < n * n * n * n) {
        s = String.format("%.2f", gb) + " GB"
    } else if (size >= n * n * n * n) {
        s = String.format("%.2f", tb) + " TB"
    }
    return s
}