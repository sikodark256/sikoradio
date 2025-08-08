package com.siko.webplayer

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.ui.PlayerNotificationManager

class MyMediaService : MediaSessionService() {

    companion object {
        const val ACTION_PLAY = "ACTION_PLAY"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_STOP = "ACTION_STOP"
        const val ACTION_SEEK = "ACTION_SEEK"
        const val NOTIF_CHANNEL = "siko_channel"
        const val NOTIF_ID = 1
    }

    private lateinit var player: ExoPlayer
    private lateinit var mediaSession: MediaSession
    private lateinit var notifManager: PlayerNotificationManager

    override fun onCreate() {
        super.onCreate()
        player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player)
            .setSessionActivity(createActivityPendingIntent())
            .build()

        val channel = NotificationChannelCompat.Builder(NOTIF_CHANNEL, NotificationManagerCompat.IMPORTANCE_LOW)
            .setName("Siko Player")
            .build()
        NotificationManagerCompat.from(this).createNotificationChannel(channel)

        notifManager = PlayerNotificationManager.Builder(this, NOTIF_ID, NOTIF_CHANNEL)
            .setChannelNameResourceId(android.R.string.untitled)
            .setMediaDescriptionAdapter(object : PlayerNotificationManager.MediaDescriptionAdapter {
                override fun getCurrentContentTitle(player: Player): CharSequence {
                    return player.currentMediaItem?.mediaMetadata?.title ?: "Siko Player"
                }
                override fun createCurrentContentIntent(player: Player): PendingIntent? {
                    return createActivityPendingIntent()
                }
                override fun getCurrentContentText(player: Player): CharSequence? {
                    return player.currentMediaItem?.mediaMetadata?.artist
                }
                override fun getCurrentLargeIcon(player: Player, callback: PlayerNotificationManager.BitmapCallback): android.graphics.Bitmap? {
                    return null
                }
            })
            .setNotificationListener(object : PlayerNotificationManager.NotificationListener {
                override fun onNotificationPosted(notificationId: Int, notification: Notification, ongoing: Boolean) {
                    if (ongoing) startForeground(notificationId, notification) else stopForeground(false)
                }
                override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) { stopSelf() }
            })
            .build().also { it.setPlayer(player) }
    }

    private fun createActivityPendingIntent(): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession = mediaSession

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let { action ->
            when(action) {
                ACTION_PLAY -> {
                    val url = intent.getStringExtra("url") ?: return START_STICKY
                    playUrl(url)
                }
                ACTION_PAUSE -> player.pause()
                ACTION_STOP -> {
                    player.stop()
                    stopForeground(true)
                    stopSelf()
                }
                ACTION_SEEK -> {
                    val pos = intent.getLongExtra("pos", 0L)
                    player.seekTo(pos)
                }
            }
        }
        return START_STICKY
    }

    private fun playUrl(url: String) {
        player.setMediaItem(MediaItem.fromUri(Uri.parse(url)))
        player.prepare()
        player.play()
    }

    override fun onDestroy() {
        player.release()
        mediaSession.release()
        super.onDestroy()
    }
}
