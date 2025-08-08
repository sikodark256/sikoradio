package com.siko.webplayer

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var webView: WebView
    private val webUrl = "https://sikoradio.nicepage.io/"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this)
        setContentView(webView)

        val ws: WebSettings = webView.settings
        ws.javaScriptEnabled = true
        ws.mediaPlaybackRequiresUserGesture = false
        ws.domStorageEnabled = true

        webView.addJavascriptInterface(WebAppInterface(this), "Android")
        webView.webViewClient = WebViewClient()
        webView.loadUrl(webUrl)
    }
}
