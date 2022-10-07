package dev.egonr.jfxtemplate.util


class ErKtUtils {
    companion object {
        fun randomString(length: Int, chars: CharRange = 'a' .. 'z'): String {
            var result = ""
            for(i in 0 .. length) {
                result += chars.random()
            }
            return result
        }
    }
}