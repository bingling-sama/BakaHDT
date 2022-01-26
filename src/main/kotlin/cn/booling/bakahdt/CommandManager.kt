package cn.booling.bakahdt

import cn.booling.bakahdt.command.*
import kotlinx.coroutines.delay
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.MemberJoinEvent
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.MessageChainBuilder
import net.mamoe.mirai.message.data.firstIsInstanceOrNull
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

typealias CommandHandler = MessageSubscribersBuilder<MessageEvent, Listener<MessageEvent>, Unit, Unit>

suspend fun MessageEvent.simpleReply(message: String) = this.subject.sendMessage(message)
suspend fun MessageEvent.simpleReply(message: MessageChain) = this.subject.sendMessage(message)

@MessageDsl
fun CommandHandler.onAdminCommand(cmd: String) = content {
    this.sender.id in ADMINS && "$IDENTIFIER$cmd" in this.message.contentToString()
}

@MessageDsl
fun CommandHandler.onOPCommand(cmd: String) = content {
    this.sender.hasPermission(Permission.OP) && "$IDENTIFIER$cmd" in this.message.contentToString()
}

@MessageDsl
fun CommandHandler.onMemberCommand(cmd: String) = content {
    this.sender.hasPermission(Permission.MEMBER) && "$IDENTIFIER$cmd" in this.message.contentToString()
}

val subscribeMessages = BAKA.eventChannel.subscribeMessages {
    // simple commands
    for ((cmd, ret) in simpleCommands) {
        contains("$IDENTIFIER$cmd") {
            if (this.sender.hasPermission(Permission.MEMBER) && !this.hasUrl()) {
                this.simpleReply(ret.messages.toMessage())
            }
        }
    }

    // admin commands
    onAdminCommand("op").invoke {
        this.message.firstIsInstanceOrNull<At>()?.target?.let { it1 ->
            permissionMap.op(it1)
            val ret = "Added $it1 to operator list."
            this.simpleReply(ret)
            logger.info(ret)
        }
    }

    onAdminCommand("deop").invoke {
        this.message.firstIsInstanceOrNull<At>()?.target?.let { it1 ->
            permissionMap.deop(it1)
            val ret = "Removed $it1 from operator list."
            this.simpleReply(ret)
            logger.info(ret)
        }
    }

    onOPCommand("ban").invoke {
        this.message.firstIsInstanceOrNull<At>()?.target?.let { it1 ->
            permissionMap.ban(it1)
            val ret = "Added $it1 to blacklist."
            this.simpleReply(ret)
            logger.info(ret)
        }
    }

    onOPCommand("unban").invoke {
        this.message.firstIsInstanceOrNull<At>()?.target?.let { it1 ->
            permissionMap.unban(it1)
            val ret = "Removed $it1 from blacklist."
            this.simpleReply(ret)
            logger.info(ret)
        }
    }

    onOPCommand("reloadPermission").invoke {
        permissionMap.clear()
        loadPermissionMap()
        val ret = "Permission Reloaded."
        this.simpleReply(ret)
        logger.info(ret)
    }

    onOPCommand("reloadCommands").invoke {
        simpleCommands.clear()
        loadSimpleCommands()
        val ret = "Commands Reloaded!"
        this.simpleReply(ret)
        logger.info(ret)
    }

    onMemberCommand("info").invoke {
        this.simpleReply(TextFields.INFO)
    }

    onMemberCommand("help").invoke {
        this.simpleReply(TextFields.HELP)
    }

    onMemberCommand("jrrp").invoke {
        this.simpleReply(this.jrrp())
    }

}

class TweakerListener : SimpleListenerHost() {
    @EventHandler
    suspend fun onMemberJoin(event: MemberJoinEvent) {
        delay(2000)
        val message = MessageChainBuilder()
            .append(At(event.member.id))
            .append(TextFields.MEMBER_JOIN_TIP)
            .asMessageChain()
        event.group.sendMessage(message)
    }
}

fun cmdInit() {
    TWEAKER_CHANNEL.registerListenerHost(TweakerListener())
    subscribeMessages
}

fun MessageEvent.jrrp(): String {
    val random = Random(
        (SimpleDateFormat("yyMMdd").format(Date()).toInt() xor this.sender.id.toInt()).toLong()
    )
    return "${this.sender.nick} 今天的人品值是：${random.nextInt(101)}"
}

