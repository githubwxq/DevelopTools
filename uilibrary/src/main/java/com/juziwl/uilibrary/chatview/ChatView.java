package com.juziwl.uilibrary.chatview;



import java.util.List;

/**
 * 聊天界面的接口
 */
public interface ChatView {

//    /**
//     * 显示消息
//     */
//    void showMessage(TIMMessage message);
//
//    /**
//     * 显示消息
//     */
//    void showMessage(List<TIMMessage> messages);
//
//    /**
//     * 显示撤回消息
//     */
//    void showRevokeMessage(TIMMessageLocator timMessageLocator);

    /**
     * 撤回消息提示信息
     */
    void showRevokeMessageErrorTips(int code);

//    /**
//     * 撤回消息成功的回调
//     */
//    void showRevokeMessageSuccess(TIMMessage timMessage);

    /**
     * 清除所有消息(离线恢复),并等待刷新
     */
    void clearAllMessage();

//    /**
//     * 发送消息成功
//     *
//     * @param message 返回的消息
//     */
//    void onSendMessageSuccess(TIMMessage message);
//
//    /**
//     * 发送消息失败
//     *
//     * @param code    返回码
//     * @param desc    返回描述
//     * @param message 发送的消息
//     */
//    void onSendMessageFail(int code, String desc, TIMMessage message);


    /**
     * 发送图片消息
     */
    void sendImage();

    /**
     * 发送图片消息
     */
    void takePhotos();

    /**
     * 发送文字消息
     */
    void sendText();

    /**
     * 发送文件
     */
    void sendFile();

    /**
     * 发送小视频消息
     */
    void sendVideo();

    /**
     * 发送语音
     */
    void sendVoice(long length, String path);

    /**
     * 正在发送
     */
    void sending(boolean flag);

//    /**
//     * 显示草稿
//     */
//    void showDraft(TIMMessageDraft draft);

    /**
     * 视频按钮点击事件
     */
    void scrollBottom();

    /**
     * 显示@界面
     */
    void showAtPage();
}
