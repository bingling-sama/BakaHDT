package cn.booling.bakahdt.service

import cn.booling.bakahdt.CF_API_KEY
import com.google.gson.JsonParser
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val BASE_URL = "https://api.curseforge.com"

object CrtVersionChecker {
    private var client = OkHttpClient()
    fun checkCrtVersion() {
        val request = Request.Builder()
            .get()
            .url("$BASE_URL/v1/mods/239197")
            .addHeader("Accept", "application/json")
            .addHeader("x-api-key", CF_API_KEY)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val data = JsonParser
                    .parseString(response.body?.string())
                    .asJsonObject["data"]
                    .asJsonObject["latestFiles"]
                    .asJsonArray
                for (jsonElement in data) {
                    when (jsonElement.asJsonObject["gameVersions"].asJsonArray[0].asString) {
                        "1.12.2" -> Json.decodeFromString<CrtVersionSerialization>(jsonElement.asString)
                        "1.16.5" -> Json.decodeFromString<CrtVersionSerialization>(jsonElement.asString)
                        "1.18.1" -> Json.decodeFromString<CrtVersionSerialization>(jsonElement.asString)
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
            }
        })
    }

    fun updateVersion(data: String) {
        var version = Json.decodeFromString<CrtVersionSerialization>(data)

    }
}

@Serializable
data class CrtVersionSerialization(
    var gameVersion: String,
    var fileName: String,
    var fileDate: String,
    var downloadUrl: String
) {
    fun parseToCrtVersion(): CrtVersion {
        return CrtVersion(
            this.gameVersion.getGameVersion(),
            this.fileName,
            SimpleDateFormat("yyyy-MM-ddTHH:mm:ss.SS").parse(this.fileDate),
            URL(this.downloadUrl)
        )
    }
}

class CrtVersion(
    var gameVersion: GameVersion,
    var fileName: String,
    var fileDate: Date,
    var downloadUrl: URL
)

enum class GameVersion(var version: String) {
    V1122("1.12.2"),
    V1165("1.16.5"),
    V1181("1.18.1"),
    OTHER("other")
}

fun String.getGameVersion(): GameVersion {
    return when (this) {
        "1.12.2" -> GameVersion.V1122
        "1.16.5" -> GameVersion.V1165
        "1.18.1" -> GameVersion.V1181
        else -> GameVersion.OTHER
    }
}