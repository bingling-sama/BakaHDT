package cn.booling.bakahdt

import cn.booling.bakahdt.command.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import java.io.File
import java.util.*
import kotlin.math.min


var subscribeMessages = BAKA.eventChannel.subscribeMessages {
    // utils
    @MessageDsl
    fun onAdminCommand(cmd: String) = content {
        this.sender.id in ADMINS && "$IDENTIFIER$cmd" in this.message.contentToString()
    }

    @MessageDsl
    fun onOPCommand(cmd: String) = content {
        this.sender.hasPermission(Permission.OP) && "$IDENTIFIER$cmd" in this.message.contentToString()
    }

    @MessageDsl
    fun onMemberCommand(cmd: String) = content {
        this.sender.hasPermission(Permission.MEMBER) && "$IDENTIFIER$cmd" in this.message.contentToString()
    }

    // simple commands
    for ((cmd, ret) in simpleCommands) {
        contains("$IDENTIFIER$cmd") {
            if (this.sender.hasPermission(Permission.MEMBER)) {
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

    onOPCommand("mcmod").invoke {
        //TODO
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
        Thread {
            try {
                Thread.sleep(2000L)
            } catch (ignored: InterruptedException) {
            }
        }.start()
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

suspend fun getSearchResults(serialNumber: Int, list: List<SearchResult>, event: GroupMessageEvent): Any{
    if (serialNumber >= list.size) return "输入序号大于可查询数据,此 次查询已取消, 请重新查询。"

    val searchResults = list[serialNumber]
    return when (searchResults.filter) {
        Filter.MOD -> modMessageHandler(searchResults.url, event)
        Filter.DATA -> dataMessageHandler(searchResults.url, event)
        Filter.TUTORIAL -> tutorialMessageHandler(searchResults.url, event)
        else -> Unit
    }
}

suspend fun modMessageHandler(url: String, event: GroupMessageEvent) {
    val mod = MCMODHandler.parseMod(url)
    val forwardMessageBuilder = ForwardMessageBuilder(event.group)
    forwardMessageBuilder.add(event.bot, readImage(mod.iconUrl, event))

    var name = ""
    if (mod.shortName.isNotEmpty()) name += "缩写:${mod.shortName}\n"
    if (mod.cnName.isNotEmpty()) name += "中文:${mod.cnName}\n"
    if (mod.enName.isNotEmpty()) name += "英文:${mod.enName}"
    forwardMessageBuilder.add(event.bot, PlainText(name))
    forwardMessageBuilder.add(event.bot, PlainText(url))
    introductionMessage(forwardMessageBuilder, mod.introduction, event)
    event.group.sendMessage(forwardMessageBuilder.build())
}

suspend fun dataMessageHandler(url: String, event: GroupMessageEvent) {
    val item = MCMODHandler.parseItem(url)

    val forwardMessageBuilder = ForwardMessageBuilder(event.group)
    forwardMessageBuilder.add(event.bot, readImage(item.iconUrl, event))
    forwardMessageBuilder.add(event.bot, PlainText(item.name))
    forwardMessageBuilder.add(event.bot, PlainText(url))
    introductionMessage(forwardMessageBuilder, item.introduction, event)
    forwardMessageBuilder.add(event.bot, PlainText("合成表:${item.tabUrl}"))

    event.group.sendMessage(forwardMessageBuilder.build())
}

suspend fun tutorialMessageHandler(url: String, event: GroupMessageEvent) {
    val tutorial = MCMODHandler.parseTutorial(url)

    val forwardMessageBuilder = ForwardMessageBuilder(event.group)
    forwardMessageBuilder.add(event.bot, PlainText(tutorial.name))
    forwardMessageBuilder.add(event.bot, PlainText(url))
    introductionMessage(forwardMessageBuilder, tutorial.introduction, event)

    event.group.sendMessage(forwardMessageBuilder.build())
}

private suspend fun introductionMessage(
    forwardMessageBuilder: ForwardMessageBuilder,
    introductionHtml: String,
    event: GroupMessageEvent
) {
    var introduction = introductionHtml
    val strList: MutableList<String> = ArrayList()
    val imgList: MutableList<Image> = ArrayList()
    var start: Int
    while (introduction.indexOf("<img data-src=").also { start = it } != -1) {
        strList.add(introduction.substring(0, start))
        introduction = introduction.substring(start)
        val imgUrl = introduction.substringBetween("<img data-src=\"", "\">")
        imgList.add(readImage(imgUrl, event))
        introduction = introduction.substring(imgUrl.length + 17)
    }
    strList.add(introduction)
    var i = 0
    while (strList.size > i) {
        strList[i].split("\n\n").forEach {
            val maxLength = 1500
            val n = it.length / maxLength + if (it.length % maxLength != 0) 1 else 0
            for (i in 0 until n)
                forwardMessageBuilder.add(event.bot, PlainText(it.substring(i*maxLength, min((i+1)*maxLength, it.length))))
        }
        if (i < imgList.size) {
            forwardMessageBuilder.add(event.bot, imgList[i])
        }
        i++
    }
}

private suspend fun readImage(url : String, event : GroupMessageEvent): Image {
    val base64Prefix = "data:image/png;base64,"
    val imageExternalResource = if (url.startsWith(base64Prefix)) {
        Base64.getDecoder().decode(url.substring(base64Prefix.length)).toExternalResource()
    } else {
        val imgFileName = url.substringAfterLast("/").substringBefore("?")
        val file = File("data/top.limbang.mirai-console-mcmod-plugin/img/$imgFileName")
        if (file.exists()) {
            file.readBytes().toExternalResource()
        } else {
            getImage( when {
                url.startsWith('/') -> "https://www.mcmod.cn$url"
                url.startsWith("//") -> "https:$url"
                else -> url
            }, file).toExternalResource()
        }
    }
    val uploadImage = event.group.uploadImage(imageExternalResource)
    imageExternalResource.close()
    return uploadImage
}

data class SearchMessage(val filter: Filter, val key: String)