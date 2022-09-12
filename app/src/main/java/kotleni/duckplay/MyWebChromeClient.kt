package kotleni.duckplay

import android.content.res.Resources
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient

class MyWebChromeClient: WebChromeClient() {
    class GameJSInterface {
        @android.webkit.JavascriptInterface
        fun getLanguage(): String {
            val locale = Resources.getSystem().configuration.locale
            return locale.language
        }
    }

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        Log.d("MyApplication", consoleMessage!!.message() + " -- From line "
                + consoleMessage!!.lineNumber() + " of "
                + consoleMessage!!.sourceId());
        return super.onConsoleMessage(consoleMessage)
    }
}