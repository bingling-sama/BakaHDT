package cn.booling.bakahdt

import cn.booling.bakahdt.command.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.message.data.*

fun cmdInit() {
    BAKA.eventChannel.subscribeMessages {
        // utils
        @MessageDsl
        fun onAdminCommand(cmd: String) = content {
            this.sender in ADMINS && "$IDENTIFIER$cmd" in this.message.contentToString()
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
}
