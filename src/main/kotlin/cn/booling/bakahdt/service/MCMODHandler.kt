package cn.booling.bakahdt

import net.mamoe.mirai.contact.*
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.message.data.*
import org.jsoup.*
import org.jsoup.safety.*
import org.jsoup.select.*

private const val DOMAIN = "https://www.mcmod.cn"
private const val RESULT = ".result-item > .head > a"
private const val INTRODUCTION_TEXT = "[class=text-area common-text font14]"
private const val INTRODUCTION_ITEM = "[class=item-content common-text font14]"
private const val INTRODUCTION_POST = "[class=post-content common-text font14]"
private const val NAME = ".name"
private const val SHORT_NAME = ".short-name"
private const val CN_NAME = ".class-title > h3"
private const val EN_NAME = ".class-title > h4"
private const val ICON_URL = ".class-cover-image > img"
private const val TAB_URL = "[class=common-icon-text item-table] > a"

enum class Filter {
    ALL,
    MOD,
    DATA,
    TUTORIAL
}

fun String.getFilter(): Filter {
    return when (this) {
        "ALL" -> Filter.ALL
        "MOD" -> Filter.MOD
        "DATA" -> Filter.DATA
        "TUTORIAL" -> Filter.TUTORIAL
        else -> Filter.ALL
    }
}

data class SearchResult(
    val title: String,
    val url: String,
    val filter: Filter
)

data class Mod(
    val iconUrl: String = "",
    val shortName: String = "",
    val cnName: String = "",
    val enName: String = "",
    val introduction: String = ""
)

data class Tutorial(
    val name: String = "",
    val introduction: String = "",
)

data class Item(
    val iconUrl: String = "",
    val name: String = "",
    val introduction: String = "",
    val tabUrl: String = ""
)

//fun search(key: String, filer: Filter): MutableList<SearchResult> {
//
//}

fun MessageEvent.mcmod() {

}

private fun search(key: String, filer: Filter, page: Int): MutableList<SearchResult> {
    val searchResultsList = mutableListOf<SearchResult>()
    val url = "$DOMAIN/s?key=$key&filter=${filer.ordinal}&page=$page"
    val elements = documentElementSelect(getDocument(url), RESULT)
    elements.forEach {
        searchResultsList.add(SearchResult(it.text(), it.attr("href"), filer))
    }
    return searchResultsList
}

private fun parseMod(url: String): Mod {
    val document = getDocument(url)
    return Mod(
        document.select(ICON_URL).attr("src").run {
            if (this.contains("https")) this
            else "https:$this"
        },
        document.select(SHORT_NAME).text(),
        document.select(CN_NAME).text(),
        document.select(EN_NAME).text(),
        labelReplacement(document.select(INTRODUCTION_TEXT))
    )
}

private fun parseItem(url: String): Item {
    val document = getDocument(url)
    return Item(
        document.select("td > img").attr("src").run {
            if (this.isNotEmpty()) "https:$this"
            else document.select("td > a > img").attr("src")
        },
        document.select(NAME).text(),
        labelReplacement(document.select(INTRODUCTION_ITEM)),
        DOMAIN + document.select(TAB_URL).attr("href")
    )
}

private fun parseTutorial(url: String): Tutorial {
    val document = getDocument(url)
    return Tutorial(
        document.select(NAME).text(),
        labelReplacement(document.select(INTRODUCTION_POST))
    )
}

private fun labelReplacement(elements: Elements): String {
    val whitelist = Whitelist()
    whitelist.addTags("p")
    whitelist.addAttributes("img", "data-src")
    var body = Jsoup.clean(elements.html(), whitelist)
    body = body.replace("<p>".toRegex(), "")
    body = body.replace("</p>".toRegex(), "")
    body = body.replace("&nbsp;".toRegex(), " ")
    return body
}

class MCMODProvider {

    private val searchResultsList = mutableListOf<SearchResult>()
    private var page: Int = 1
    private var nextPage: Boolean = false

    private fun search(key: String, filer: Filter) {
        searchResultsList.addAll(search(key, filer, page))
        nextPage = searchResultsList.size == 30
    }

    private fun getSearchList(key: String, filer: Filter, entry: Int): List<SearchResult> {
        if (page == 1) {
            search(key, filer)
            page++
        }
        val subList = if (entry <= searchResultsList.size) {
            searchResultsList.subList(0, entry)
        } else {
            if (nextPage) {
                search(key, filer)
                return getSearchList(key, filer, entry)
            } else {
                page = 1
                searchResultsList.subList(0, searchResultsList.size)
            }
        }
        val toList = subList.toList()
        subList.clear()
        return toList
    }

    fun searchListToString(list: List<SearchResult>, group: Group, sender: Member): Message {
        if (list.isEmpty()) return PlainText("未查询到此内容...\n")
        val builder = ForwardMessageBuilder(group)

        builder.add(sender, PlainText("30秒内回复编号查看"))

        for (i in list.indices) {
            val title = list[i].title
                .replace(Regex("\\([^()]*\\)"), "")
                .replace(Regex("\\[[^\\[\\]]*]"), "")
                .replace(Regex("\\s*-\\s*"), "-")
            builder.add(sender.id, i.toString(), PlainText(title))
        }
        if (searchResultsList.size > 0) builder.add(sender, PlainText("回复[P]下一页"))
        return builder.build()
    }

    fun getNextPage() = nextPage

    fun getSearchResultsListSize() = searchResultsList.size

    fun clear() {
        searchResultsList.clear()
        page = 1
        nextPage = false
    }

}


private suspend fun getSearchResults(serialNumber: Int, list: List<SearchResult>, event: GroupMessageEvent): Any {
    if (serialNumber >= list.size) return "输入序号大于可查询数据,此 次查询已取消, 请重新查询。"

    val searchResults = list[serialNumber]
    return when (searchResults.filter) {
        Filter.MOD -> modMessageHandler(searchResults.url, event)
        Filter.DATA -> dataMessageHandler(searchResults.url, event)
        Filter.TUTORIAL -> tutorialMessageHandler(searchResults.url, event)
        else -> Unit
    }
}

private suspend fun modMessageHandler(url: String, event: GroupMessageEvent) {
    val mod = parseMod(url)
    val forwardMessageBuilder = ForwardMessageBuilder(event.group)
    forwardMessageBuilder.add(event.bot, event.group.uploadImage(readImage(mod.iconUrl)))

    var name = ""
    if (mod.shortName.isNotEmpty()) name += "缩写:${mod.shortName}\n"
    if (mod.cnName.isNotEmpty()) name += "中文:${mod.cnName}\n"
    if (mod.enName.isNotEmpty()) name += "英文:${mod.enName}"
    forwardMessageBuilder.add(event.bot, PlainText(name))
    forwardMessageBuilder.add(event.bot, PlainText(url))
    introductionMessage(forwardMessageBuilder, mod.introduction, event)
    event.group.sendMessage(forwardMessageBuilder.build())
}

private suspend fun dataMessageHandler(url: String, event: GroupMessageEvent) {
    val item = parseItem(url)

    val forwardMessageBuilder = ForwardMessageBuilder(event.group)
    forwardMessageBuilder.add(event.bot, event.group.uploadImage(readImage(item.iconUrl)))
    forwardMessageBuilder.add(event.bot, PlainText(item.name))
    forwardMessageBuilder.add(event.bot, PlainText(url))
    introductionMessage(forwardMessageBuilder, item.introduction, event)
    forwardMessageBuilder.add(event.bot, PlainText("合成表:${item.tabUrl}"))

    event.group.sendMessage(forwardMessageBuilder.build())
}

private suspend fun tutorialMessageHandler(url: String, event: GroupMessageEvent) {
    val tutorial = parseTutorial(url)

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
        imgList.add(event.group.uploadImage(readImage(imgUrl)))
        introduction = introduction.substring(imgUrl.length + 17)
    }
    strList.add(introduction)
    var i = 0
    while (strList.size > i) {
        strList[i].split("\n\n").forEach {
            val maxLength = 1500
            val n = it.length / maxLength + if (it.length % maxLength != 0) 1 else 0
            for (j in 0 until n)
                forwardMessageBuilder.add(
                    event.bot,
                    PlainText(it.substring(j * maxLength, Integer.min((j + 1) * maxLength, it.length)))
                )
        }
        if (i < imgList.size) {
            forwardMessageBuilder.add(event.bot, imgList[i])
        }
        i++
    }
}
