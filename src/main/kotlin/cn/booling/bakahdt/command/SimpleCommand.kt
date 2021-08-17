package cn.booling.bakahdt.command

import kotlinx.serialization.*

var simpleCommands = mutableMapOf<String, SimpleCommand>()

@Serializable
data class SimpleCommand(
    val name: String,
    val info: String? = null,
    val permission: String = "member",
    val messages: MutableList<String> = mutableListOf()
)

@Serializable
data class CommandMap(var commands: List<SimpleCommand> = mutableListOf())
