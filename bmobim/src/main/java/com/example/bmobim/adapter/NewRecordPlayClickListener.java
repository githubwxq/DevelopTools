package com.example.bmobim.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.View;
import android.widget.ImageView;

import com.example.bmobim.R;
import com.wxq.commonlibrary.bmob.CommonBmobUser;
import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.imageloader.utils.MD5Utils;
import com.wxq.commonlibrary.util.ToastUtils;

import java.io.File;
import java.io.FileInputStream;
import cn.bmob.newim.bean.BmobIMAudioMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.core.BmobDownloadManager;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

public class NewRecordPlayClickListener implements View.OnClickListener {

	BmobIMMessage message;
	ImageView iv_voice;
	private AnimationDrawable anim = null;
	Context mContext;
	String currentObjectId = "";
	MediaPlayer mediaPlayer = null;
	public static boolean isPlaying = false;         //static表面只有一个状态多个实现了共用一个状态
	public static NewRecordPlayClickListener currentPlayListener = null;  // 、、static表面只有一个状态多个实现了共用一个状态 上衣个对象的实列
	static BmobIMMessage currentMsg = null;

	public NewRecordPlayClickListener(Context context, BmobIMMessage msg, ImageView voice) {
		this.iv_voice = voice;
		this.message = msg;
		this.mContext = context.getApplicationContext();
		currentMsg = msg;
		currentPlayListener = this;
		try {
			currentObjectId = BmobUser.getCurrentUser(CommonBmobUser.class).getObjectId();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	public void startPlayRecord(String filePath, boolean isUseSpeaker) {
		if (!(new File(filePath).exists())) {
			return;
		}
		AudioManager audioManager = (AudioManager) mContext
				.getSystemService(Context.AUDIO_SERVICE);
		mediaPlayer = new MediaPlayer();
		if (isUseSpeaker) {
			audioManager.setMode(AudioManager.MODE_NORMAL);
			audioManager.setSpeakerphoneOn(true);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
		} else {
			audioManager.setSpeakerphoneOn(false);
			audioManager.setMode(AudioManager.MODE_IN_CALL);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
		}

		try {
			mediaPlayer.reset();
			// 单独使用此方法会报错播放错误:setDataSourceFD failed.: status=0x80000000
			// mediaPlayer.setDataSource(filePath);
			// 因此采用此方式会避免这种错误
			FileInputStream fis = new FileInputStream(new File(filePath));
			mediaPlayer.setDataSource(fis.getFD());
			mediaPlayer.prepare();
			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer arg0) {
					isPlaying = true;
					currentMsg = message;
					arg0.start();
					startRecordAnimation();
				}
			});
			mediaPlayer
					.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer mp) {
							stopPlayRecord();
						}

					});
			currentPlayListener = this;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopPlayRecord() {
		stopRecordAnimation();
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
		isPlaying = false;
	}

	private void startRecordAnimation() {
		if (message.getFromId().equals(currentObjectId)) {
			iv_voice.setImageResource(R.drawable.anim_chat_voice_right);
		} else {
			iv_voice.setImageResource(R.drawable.anim_chat_voice_left);
		}
		anim = (AnimationDrawable) iv_voice.getDrawable();
		anim.start();
	}

	private void stopRecordAnimation() {
		if (message.getFromId().equals(currentObjectId)) {
			iv_voice.setImageResource(R.mipmap.voice_left3);
		} else {
			iv_voice.setImageResource(R.mipmap.voice_right3);
		}
		if (anim != null) {
			anim.stop();
		}
	}

	@Override
	public void onClick(View arg0) {
		if (isPlaying) { // 上一个对象
			currentPlayListener.stopPlayRecord();
			if (currentMsg != null
					&& currentMsg.hashCode() == message.hashCode()) {
				currentMsg = null;
				return;
			}
		}
//		if (message.getFromId().equals(currentObjectId)) {// 如果是自己发送的语音消息，则播放本地地址
//			String localPath = message.getContent();
//			startPlayRecord(localPath, true);
//		} else {// 如果是收到的消息，则需要先下载后播放
			final File file = new File(GlobalContent.VOICEPATH, MD5Utils.toMD5(message.getContent()));
			if (file.exists()) {
				startPlayRecord(file.getPath(), true);
			}else {
				BmobFile bmobFile=new BmobFile(MD5Utils.toMD5(message.getContent()),"",message.getContent());
				//允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
				File saveFile = new File(GlobalContent.VOICEPATH, bmobFile.getFilename());
				bmobFile.download(saveFile,new DownloadFileListener() {
					@Override
					public void done(String savePath, BmobException e) {
						if(e==null){
							startPlayRecord(savePath, true);
						}else{
							ToastUtils.showShort("下载失败："+e.getErrorCode()+","+e.getMessage());
						}
					}
					@Override
					public void onProgress(Integer integer, long l) {

					}
				});
			}
//		}
	}

}