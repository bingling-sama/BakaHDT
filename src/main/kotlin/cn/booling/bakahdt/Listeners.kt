package cn.booling.bakahdt

import cn.booling.bakahdt.command.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.*
import java.util.*
import java.util.regex.*

class BakaListener : SimpleListenerHost() {
    @EventHandler
    suspend fun onMessage(event: MessageEvent) {
        val cmds: Queue<Command> = ArrayDeque()
        if (event.message.contentToString().contains("&")) {
            val matcher = Pattern.compile("&.*?\\s|&.*$").matcher(event.message.contentToString())
            matcher.results().forEach { result: MatchResult? ->
                cmds.add(commands[result?.group()?.replace("&", "")?.replace(" ", "")])
            }
        }
        for (cmd in cmds) {
            if (event.sender.hasPermission(cmd.permission)) cmd.execute.invoke(event)
        }
    }
}