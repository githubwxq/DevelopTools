package com.juziwl.uilibrary.chatview;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import com.wxq.commonlibrary.util.ToastUtils;
import java.io.File;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
/**
 * 媒体播放工具
 */
public class MediaUtil {

    private static final String TAG = "MediaUtil";

    private IMediaPlayer player;
    private EventListener eventListener;
    private String currentPlayFile = null;
    private static AudioManager audioManager = null;
    private static AudioManager.OnAudioFocusChangeListener changeListener = null;

    private MediaUtil() {
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        player = new IjkMediaPlayer();

    }

    private static MediaUtil instance = new MediaUtil();

    public static MediaUtil getInstance(Context context) {
        audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        changeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {

            }
        };
        return instance;
    }

    public IMediaPlayer getPlayer() {
        return player;
    }

    public void setEventListener(final EventListener eventListener) {
        this.eventListener = eventListener;
        if (player != null) {
            player.setOnCompletionListener(mp -> {
                if (this.eventListener != null) {
                    this.eventListener.onStop();
                    this.eventListener = null;
                }
                currentPlayFile = null;
                try {
                    audioManager.abandonAudioFocus(changeListener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void play(File file) {
        try {
            if (eventListener != null) {
                eventListener.onStop();
            }
            try {
                audioManager.requestAudioFocus(changeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (player != null) {
                if (player.isPlaying()) {
                    player.stop();
                }
                player.release();
            }
            player = new IjkMediaPlayer();
            currentPlayFile = file.getAbsolutePath();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(currentPlayFile);
            player.prepareAsync();
            player.setVolume(1, 1);
            player.start();
        } catch (Exception e) {
            ToastUtils.showShort(e.getMessage());
        }
    }

    public void stop() {
        if (player != null && player.isPlaying()) {
            player.stop();
        }
        if (eventListener != null) {
            eventListener.onStop();
            eventListener = null;
        }
        try {
            audioManager.abandonAudioFocus(changeListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentPlayFile = null;
    }

    public long getDuration(String path,Context context) {
        MediaPlayer player = MediaPlayer.create(context, Uri.parse(path));
        if (player == null) {
            return -1;
        }
        return player.getDuration();
    }

    public boolean isPlaying() {
        return player != null && player.isPlaying();
    }

    public String getCurrentPlayFile() {
        return currentPlayFile;
    }

    /**
     * 播放器事件监听
     */
    public interface EventListener {
        void onStop();
    }
}
