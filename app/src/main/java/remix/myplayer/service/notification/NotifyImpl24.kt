package remix.myplayer.service.notification

import android.annotation.TargetApi
import android.app.Notification
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat

import remix.myplayer.R
import remix.myplayer.bean.mp3.Song
import remix.myplayer.request.RemoteUriRequest
import remix.myplayer.request.RequestConfig
import remix.myplayer.service.Command
import remix.myplayer.service.MusicService
import remix.myplayer.service.MusicService.EXTRA_CONTROl
import remix.myplayer.util.DensityUtil

import remix.myplayer.util.ImageUriUtil.getSearchRequestWithAlbumType

/**
 * Created by Remix on 2017/11/22.
 */
@TargetApi(Build.VERSION_CODES.O)
class NotifyImpl24(context: MusicService) : Notify(context) {

    override fun updateForPlaying() {
        val song = service.currentSong

        //设置封面
        val size = DensityUtil.dip2px(service, 128f)
        object : RemoteUriRequest(getSearchRequestWithAlbumType(song), RequestConfig.Builder(size, size).build()) {
            override fun onError(errMsg: String) {
                val result = BitmapFactory.decodeResource(service.resources, R.drawable.album_empty_bg_night)
                updateWithBitmap(result, song)
            }

            override fun onSuccess(bitmap: Bitmap?) {
                var result = bitmap
                //                Bitmap result = copy(bitmap);
                if (result == null) {
                    result = BitmapFactory.decodeResource(service.resources, R.drawable.album_empty_bg_night)
                }
                updateWithBitmap(result, song)
            }

        }.load()
    }

    private fun updateWithBitmap(bitmap: Bitmap?, song: Song) {
        val playPauseIcon = if (service.isPlaying) R.drawable.ic_pause_black_24dp else R.drawable.ic_play_arrow_black_24dp

        val deleteIntent = Intent(MusicService.ACTION_CMD)
        deleteIntent.putExtra(EXTRA_CONTROl, Command.CLOSE_NOTIFY)

        val notification = NotificationCompat.Builder(service, Notify.PLAYING_NOTIFICATION_CHANNEL_ID)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.notifbar_icon)
                .addAction(R.drawable.ic_skip_previous_black_24dp, service.getString(R.string.previous),
                        buildPendingIntent(service, Command.PREV))
                .addAction(playPauseIcon, service.getString(R.string.play_pause),
                        buildPendingIntent(service, Command.TOGGLE))
                .addAction(R.drawable.ic_skip_next_black_24dp, service.getString(R.string.next),
                        buildPendingIntent(service, Command.NEXT))
                .addAction(R.drawable.ic_desktop_lyric_black_24dp, service.getString(R.string.float_lrc),
                        buildPendingIntent(service, Command.TOGGLE_FLOAT_LRC))
                .setDeleteIntent(buildPendingIntent(service, Command.CLOSE_NOTIFY))
                .setContentIntent(contentIntent)
                .setContentTitle(song.title)
                .setLargeIcon(bitmap)
                .setShowWhen(false)
                .setOngoing(service.isPlaying)
                .setContentText(song.artist + " - " + song.album)
                .setStyle(android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(service.mediaSession.sessionToken))
                .build()
        if (isStop)
            return
        pushNotify(notification)
    }

}
