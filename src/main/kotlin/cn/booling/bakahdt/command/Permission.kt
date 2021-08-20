package cn.booling.bakahdt.command

import cn.booling.bakahdt.*
import cn.booling.bakahdt.command.Permission.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
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
    var op: MutableList<Long> = mutableListOf(),
    var banned: MutableList<Long> = mutableListOf()
) {
    fun clear() {
        op = mutableListOf()
        banned = mutableListOf()
    }

    private fun save() {
        permissionsFile.writeText(Json.encodeToString(this))
        logger.info(this.toString())
    }

    fun op(id: Long) {
        if (!op.contains(id)) op.add(id)
        save()
    }

    fun deop(id: Long) {
        if (op.contains(id)) op.remove(id)
        save()
    }

    fun ban(id: Long) {
        if (!op.contains(id) && !banned.contains(id)) banned.add(id)
        save()
    }

    fun unban(id: Long) {
        if (banned.contains(id)) banned.remove(id)
        save()
    }
}

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
        MEMBER -> this.getPermission() == permission || this.getPermission() == OP
        BANNED -> return false
    }
}
