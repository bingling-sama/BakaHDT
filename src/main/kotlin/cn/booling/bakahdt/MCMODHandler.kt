package cn.booling.bakahdt

import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.message.data.ForwardMessageBuilder
import net.mamoe.mirai.message.data.Message
import net.mamoe.mirai.message.data.PlainText
import org.jsoup.Jsoup
import org.jsoup.safety.Whitelist
import org.jsoup.select.Elements

object MCMODHandler {
    private const val DOMAIN = "https://www.mcmod.cn"
    private const val RESULT = ".result-item > .head > a"
    private const val INTRODUCTION_TEXT = "[class=text-area common-text font14]"
    private const val INTRODUCTION_ITEM = "[class=item-content common-text font14]"
    private const val INTRODUCTION_POST= "[class=post-content common-text font14]"
    private const val NAME = ".name"
    private const val SHORT_NAME = ".short-name"
    private const val CN_NAME = ".class-title > h3"
    private const val EN_NAME = ".class-title > h4"
    private const val ICON_URL = ".class-cover-image > img"
    private const val TAB_URL = "[class=common-icon-text item-table] > a"

    fun search(searchKey : String, filer: Filter, page : Int): MutableList<SearchResult> {
        return search(searchKey, filer, RESULT, page)
    }

    fun parseMod(url: String): Mod {
        val document = getDocument(url)
        return Mod (
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

    fun parseItem(url: String): Item {
        val document = getDocument(url)
        return Item (
            document.select("td > img").attr("src").run {
                if (this.isNotEmpty()) "https:$this"
                    else document.select("td > a > img").attr("src")
            },
            document.select(NAME).text(),
            labelReplacement(document.select(INTRODUCTION_ITEM)),
            DOMAIN + document.select(TAB_URL).attr("href")
        )
    }

    fun parseTutorial(url: String): Tutorial {
        val document = getDocument(url)
        return Tutorial(
            document.select(NAME).text(),
            labelReplacement(document.select(INTRODUCTION_POST))
        )
    }

    private fun search(key: String, filer: Filter, cssQuery: String, page: Int): MutableList<SearchResult> {
        val searchResultsList = mutableListOf<SearchResult>()
        val url = "$DOMAIN/s?key=$key&filter=${filer.ordinal}&page=$page"
        val elements = documentElementSelect(getDocument(url), cssQuery)
        elements.forEach {
            searchResultsList.add(SearchResult(it.text(), it.attr("href"), filer))
        }
        return searchResultsList
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
}

class MCMODProvider {

    private val searchResultsList = mutableListOf<SearchResult>()
    private var page: Int = 1
    private var nextPage: Boolean = false

    private fun search(key: String, filer: Filter) {
        searchResultsList.addAll(MCMODHandler.search(key, filer, page))
        nextPage = searchResultsList.size == 30
    }

    fun getSearchList(key: String, filer: Filter, entry: Int): List<SearchResult> {
        if (page == 1) {
            search(key, filer);
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
                .replace(Regex("\\([^()]*\\)"),"")
                .replace(Regex("\\[[^\\[\\]]*\\]"),"")
                .replace(Regex("\\s*-\\s*"),"-")
            builder.add(sender.id,i.toString(), PlainText(title))
        }
        if(searchResultsList.size > 0) builder.add(sender, PlainText("回复[P]下一页"))
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

enum class Filter {
    ALL,
    MOD,
    DATA,
    TUTORIAL
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