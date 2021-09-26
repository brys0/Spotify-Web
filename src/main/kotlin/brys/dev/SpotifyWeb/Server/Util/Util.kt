package brys.dev.SpotifyWeb.Server.Util

import brys.dev.SpotifyWeb.Server.Util.Util.Data.getSize
import com.github.ajalt.mordant.AnsiCode
import com.github.ajalt.mordant.TermColors
import kotlin.system.exitProcess

object Util {
    internal object Data {
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
    }
    object Logger {
        val color = TermColors()
        fun startHeader(message: String, c: AnsiCode): Logger {
            println("${c}┌──────────────────────$message──────────────────┐${color.reset}")
            return this
        }
        fun endHeader(c: AnsiCode): Logger {
            println("${c}└──────────────────────────────────────────────┘${color.reset}")
            return this
        }
        fun success(detail: String, event: String): Logger {
             println("  ${color.brightGreen.bg} ${color.black}+ \u001B[0m ${color.brightWhite}${color.gray}│${color.reset} $detail ${color.brightGreen}$event\u001B[0m")
            return this
        }
        fun failure(detail: String, event: String, failure: Boolean): Logger {
            return when(failure) {
                true -> {
                    println("  ${color.brightRed.bg} ${color.black}X \u001B[0m ${color.brightWhite}${color.gray}│${color.reset} $detail ${color.brightRed}$event\u001B[0m")
                    exitProcess(1)
                }
                false -> {
                    println("  ${color.brightRed.bg} ${color.black}X \u001B[0m ${color.brightWhite}${color.gray}│${color.reset} $detail ${color.brightRed}$event\u001B[0m")
                    this
                }
            }
        }
    }
    object Memory {
        val max = getSize(Runtime.getRuntime().maxMemory())
        val total = getSize(Runtime.getRuntime().totalMemory())
        val available = getSize(Runtime.getRuntime().freeMemory())
        val used = getSize(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
    }
    object Integer {
        fun isInt(int: String): Boolean {
            return try {
                int.toInt()
                true
            } catch (e: NumberFormatException) {
                false
            }
        }
    }
    fun formatSize(v: Long): String? {
        if (v < 1024) return "$v B"
        val z = (63 - java.lang.Long.numberOfLeadingZeros(v)) / 10
        return String.format("%.1f %sB", v.toDouble() / (1L shl z * 10), " KMGTPE"[z])
    }
}