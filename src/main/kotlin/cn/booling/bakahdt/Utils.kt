package cn.booling.bakahdt

import net.mamoe.mirai.event.events.*
import java.io.*

suspend fun MessageEvent.simpleReply(message: String) = this.subject.sendMessage(message)

fun generateFile(fileName: String) {
    val file = File(fileName)
    if (!file.exists()) {
        file.createNewFile()
    }
    getResourceAsText(fileName).let { file.writeText(it) }
}

fun getResourceAsText(path: String): String {
    return object {}::class.java.getResourceAsStream("/${path}")!!.bufferedReader().readText()
}
