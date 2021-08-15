package cn.booling.bakahdt

import net.mamoe.mirai.event.events.*
import java.io.*

suspend fun MessageEvent.simpleReply(message: String) = this.subject.sendMessage(message)

fun generateFile(fileName: String) {
    val path = "src/main/resources/${fileName}"
    val file = File(fileName)
    if (!file.exists()) {
        file.createNewFile()
    }
    file.writeText(File(path).readText())
}
