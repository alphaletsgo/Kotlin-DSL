package cn.isif.demo

import cn.isif.demo.utils.Output

interface Node {
    fun render(): String
}

class StringNode(private val content: String) : Node {
    override fun render(): String {
        return content
    }
}

class HtmlBlock(private val name: String) : Node {
    private val child = ArrayList<Node>()
    private val properties = HashMap<String, Any>()

    override fun render(): String {
        return "<$name ${
            properties.map { "${it.key}='${it.value}'" }.joinToString(" ")
        }> ${child.joinToString("") { it.render() }} </$name>"
    }

    operator fun String.invoke(value: String) {
        this@HtmlBlock.properties[this] = value
    }

    operator fun String.unaryPlus() {
        this@HtmlBlock.child += StringNode(this)
    }

    fun body(block: HtmlBlock.() -> Unit) {
        val body = HtmlBlock("body")
        block.invoke(body)
        this.child += body
    }

    fun div(block: HtmlBlock.() -> Unit) {
        val div = HtmlBlock("div")
        block.invoke(div)
        this.child += div
    }

    fun head(block: HtmlBlock.() -> Unit) {
        val head = HtmlBlock("head")
        block.invoke(head)
        this.child += head
    }

    fun title(block: HtmlBlock.() -> Unit) {
        val title = HtmlBlock("title")
        block.invoke(title)
        this.child += title
    }

}

fun html(block: HtmlBlock.() -> Unit) {
    val html = HtmlBlock("html")
    html.block()
    Output.output(html.render())
}

fun main() {
    html {
        head {
            title { +"Kotlin" }
        }
        body {
            div {
                "style"(
                    """
                        background-color:#FFA500;
                        clear:both;
                        text-align:center;
                    """.trimIndent()
                )
                +"halo Kotlin DSL"
            }
        }
    }
}