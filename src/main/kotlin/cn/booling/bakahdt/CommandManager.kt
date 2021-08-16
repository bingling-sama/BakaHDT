package cn.booling.bakahdt

import cn.booling.bakahdt.command.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.message.data.*

fun cmdInit() {
    BAKA.eventChannel.subscribeMessages {
        for ((cmd, ret) in simpleCommands) {
            contains("/$cmd") {
                if (this.sender.hasPermission(Permission.MEMBER)) {
                    this.simpleReply(ret.messages.toMessage())
                }
            }
        }
        // special commands
        sentBy(OWNER.id) {
            if (this.message.any { it is At }) {
                if ("/op" in this.message.contentToString()) {
                    this.message.firstIsInstanceOrNull<At>()?.target?.let { it1 ->
                        permissionMap.op(it1)
                        val ret = "Added $it1 to operator list."
                        this.simpleReply(ret)
                        logger.info(ret)
                    }
                }
                if ("/deop" in this.message.contentToString()) {
                    this.message.firstIsInstanceOrNull<At>()?.target?.let { it1 ->
                        permissionMap.deop(it1)
                        val ret = "Removed $it1 from operator list."
                        this.simpleReply(ret)
                        logger.info(ret)
                    }
                }
            }
        }
        content { "/" in this.message.contentToString() && this.sender.hasPermission(Permission.MEMBER) }.invoke {
            val message = this.message.contentToString()
            // op commands
            if (this.sender.hasPermission(Permission.OP)) {
                if (this.message.any { it is At }) {
                    if ("/ban" in message) {
                        this.message.firstIsInstanceOrNull<At>()?.target?.let { it1 ->
                            permissionMap.ban(it1)
                            val ret = "Added $it1 to blacklist."
                            this.simpleReply(ret)
                            logger.info(ret)
                        }
                    }
                    if ("/unban" in message) {
                        this.message.firstIsInstanceOrNull<At>()?.target?.let { it1 ->
                            permissionMap.unban(it1)
                            val ret = "Removed $it1 from blacklist."
                            this.simpleReply(ret)
                            logger.info(ret)
                        }
                    }
                    if ("/reloadPermission" in message) {
                        permissionMap.clear()
                        loadPermissionMap()
                        val ret = "Permission Reloaded."
                        this.simpleReply(ret)
                        logger.info(ret)
                    }
                    if ("/reloadCommands" in message) {
                        simpleCommands.clear()
                        loadSimpleCommands()
                        val ret = "Commands Reloaded!"
                        this.simpleReply(ret)
                        logger.info(ret)
                    }
                }
            }
            // normal commands
            if ("/help" in message) {
                this.simpleReply(TextFields.HELP)
            }
            if ("/jrrp" in message) {
                this.simpleReply(this.jrrp())
            }
        }
    }
}
