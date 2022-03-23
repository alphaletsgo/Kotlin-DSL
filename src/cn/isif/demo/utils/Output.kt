package cn.isif.demo.utils

import java.io.File

class Output {
    companion object {
        private const val OUT_DIR = "out/html"
        private const val OUT_FILE = "$OUT_DIR/index.html"
        private fun checkOut(): Boolean {
            val dir = File(OUT_DIR)
            return if (!dir.exists()) {
                dir.mkdirs()
            } else true
        }

        fun output(text: String) {
            if (checkOut()) {
                val file = File(OUT_FILE)
                file.writeText(text)
            } else {
                println("create failed")
            }
        }
    }
}