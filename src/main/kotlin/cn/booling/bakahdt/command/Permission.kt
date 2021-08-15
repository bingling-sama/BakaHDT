package cn.booling.bakahdt.command

import cn.booling.bakahdt.command.Permission.*
import kotlinx.serialization.*
import net.mamoe.mirai.contact.*

var permissionMap = PermissionMap()

enum class Permission(val displayName: String) {
    OP("operator"),
    MEMBER("member"),
    BANNED("banned");
}

fun String.getPermission(): Permission {
    return when (this) {
        OP.displayName -> OP
        MEMBER.displayName -> MEMBER
        BANNED.displayName -> BANNED
        else -> MEMBER
    }
}

@Serializable
data class PermissionMap(
    var op: List<Long> = mutableListOf(),
    var banned: List<Long> = mutableListOf()
)

fun User.getPermission(): Permission {
    return when (this.id) {
        in permissionMap.op -> OP
        in permissionMap.banned -> BANNED
        else -> MEMBER
    }
}

fun User.hasPermission(permission: Permission): Boolean {
    return when (this.getPermission()) {
        OP -> return true
        MEMBER -> this.getPermission() == permission
        BANNED -> return false
    }
}
