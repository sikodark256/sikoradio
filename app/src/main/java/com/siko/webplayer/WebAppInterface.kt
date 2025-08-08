package com.siko.webplayer

import android.content.Context
import android.content.Intent
import android.webkit.JavascriptInterface
import androidx.core.content.ContextCompat

class WebAppInterface(private val ctx: Context) {
    @JavascriptInterface
    fun play(url: String, title: String?, artist: String?, image: String?) {
        val intent = Intent(ctx, MyMediaService::class.java).apply {
            action = MyMediaService.ACTION_PLAY
            putExtra("url", url)
            putExtra("title", title)
            putExtra("artist", artist)
            putExtra("image", image)
        }
        ContextCompat.startForegroundService(ctx, intent)
    }
    @JavascriptInterface
    fun pause() {
        val intent = Intent(ctx, MyMediaService::class.java).apply { action = MyMediaService.ACTION_PAUSE }
        ContextCompat.startForegroundService(ctx, intent)
    }
    @JavascriptInterface
    fun stop() {
        val intent = Intent(ctx, MyMediaService::class.java).apply { action = MyMediaService.ACTION_STOP }
        ContextCompat.startForegroundService(ctx, intent)
    }
    @JavascriptInterface
    fun seek(positionMs: Long) {
        val intent = Intent(ctx, MyMediaService::class.java).apply {
            action = MyMediaService.ACTION_SEEK
            putExtra("pos", positionMs)
        }
        ContextCompat.startForegroundService(ctx, intent)
    }
}
