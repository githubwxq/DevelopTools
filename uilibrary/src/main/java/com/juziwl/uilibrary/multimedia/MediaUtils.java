package com.juziwl.uilibrary.multimedia;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.List;

/**
 * Created by Rex on 2018/3/30.
 */

public class MediaUtils {

    public static final String TAG = "rex_media";
    /**
     * 播放语音
     * 返回值需要在生命收起结束时候releasePlayer释放
     */
    public static MediaPlayer player = null;

    public static MediaPlayer playVoice(Context activity, String voiceUrl) {
        if (!TextUtils.isEmpty(voiceUrl)) {
            try {
                if (player == null) {
                    player = new MediaPlayer();
                } else {
                    player.reset();
                }
                player.setDataSource(voiceUrl);
                player.prepare();
                final MediaPlayer finalPlayer = player;
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        finalPlayer.start();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(activity, "没有找到相关文件！", Toast.LENGTH_SHORT)
                    .show();
        }

        return player;
    }

    /**
     * 释放播放器
     */
    public static void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    /**
     * @param voiceFiles 需要合成的语音片段
     * @return 返回最终生成的语音判断
     * 注意:amr格式的头文件为6个字节的长度,aac格式的头文件为7或9个字节的长度
     */

    public static String composeVoiceFile( List<String> voiceFiles, String endFileUrl) {
//        String endFileUrl = getNewVoiceFileUrl(name);
        try {
            File unitedFile = new File(endFileUrl);
            FileOutputStream fos = new FileOutputStream(unitedFile);
            RandomAccessFile ra = null;
            for (int i = 0; i < voiceFiles.size(); i++) {

                int headerLen = AACHelper.getHeaderLength(voiceFiles.get(0));//aac头文件不一致 需要额外获取长度

                ra = new RandomAccessFile(voiceFiles.get(i), "r");
                if (i != 0) {
                    ra.seek(headerLen);
                }
                byte[] buffer = new byte[1024 * 8];
                int len = 0;
                while ((len = ra.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
            }
            ra.close();
            fos.close();
        } catch (Exception e) {
        }

        return endFileUrl;
    }

//    public static String getNewVoiceFileUrl(String tag) {
//
//        String url = GlobalContent.VOICEPATH;
//        File file = new File(url);
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        url = url + "/" + System.currentTimeMillis()
//                + "voice.aac";
//
//        return url;
//    }

}
