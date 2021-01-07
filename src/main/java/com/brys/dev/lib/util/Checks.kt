package com.brys.dev.lib.util
import com.brys.dev.lib.util.Checks.t
import com.github.ajalt.mordant.TermColors
import org.json.simple.parser.JSONParser
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception
import kotlin.system.exitProcess

object  Checks {
    val t = TermColors()
    val checkJson = checkJson()
    val checkVersion = checkVersion()
    val checkOs = checkOs()
    val checkWrite = checkWrite()
    val check = checkAll()
}
private fun checkJson() {
    try {
        val obj: Any = JSONParser().parse(FileReader("conf.json"))
        val jo: org.json.simple.JSONObject = obj as org.json.simple.JSONObject
        jo["public"] as String
        jo["private"] as String
    } catch (e: FileNotFoundException)
    {
        println("${t.brightYellow.bg} ${t.black}~ ${t.reset} ${t.brightWhite}- conf.json was not found, creating and exiting..${t.reset}")
        val writer = FileWriter("conf.json")
        writer.write("{\n" +
                "  \"public\": \"public client\",\n" +
                "  \"private\": \"private client\"\n" +
                "}")
        writer.close()
        exitProcess(0)
    } catch (e: Exception) {
        println("${t.brightRed.bg} ${t.black}X ${t.reset} ${t.brightWhite}- Error occurred when trying to read or make json${t.reset}")
    }
}
private fun getVersion(): Int {
    var version = System.getProperty("java.version")
    if (version.startsWith("1.")) {
        version = version.substring(2, 3)
    } else {
        val dot = version.indexOf(".")
        if (dot != -1) {
            version = version.substring(0, dot)
        }
    }
    return version.toInt()
}
fun checkVersion() {
    val ver = getVersion()
    if (ver <= 1.6) {
        println("${t.brightRed.bg} ${t.black}X ${t.reset} ${t.brightWhite}- Webserver must have java 1.8 or greater${t.reset}")
        exitProcess(1)
    }
}
fun checkOs() {
    val name = System.getProperty("os.name").toLowerCase()
    if (name == "mac" || name == "darwin") {
        println("${t.brightYellow.bg} ${t.black}~ ${t.reset} ${t.brightWhite}- Webserver may not work on macOS${t.reset}")
    }
}
fun checkWrite() {
    try {
        File("temp.brys").createNewFile()
    } catch (e: SecurityException) {
        println("${t.brightRed.bg} ${t.black}X ${t.reset} ${t.brightWhite}- Webserver must be able to write to local path where jar is located.${t.reset}")
        exitProcess(1)
    }
    File("temp.brys").delete()
}
fun checkAll() {
    checkJson()
    checkVersion()
    checkOs()
    checkWrite()
}

