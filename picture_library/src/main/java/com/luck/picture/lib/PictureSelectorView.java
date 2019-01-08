package com.luck.picture.lib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片选择器封装
 *
 * @author wangxq
 * @modify Neil
 */
public class PictureSelectorView extends RelativeLayout {
    private RecyclerView recyclerView;
    private PictureSelectAdapter adapter;
    private Activity activity;
    private List<LocalMedia> selectList = new ArrayList<>();
    /**
     * 前往相册 true
     * 不前往相册 false
     */
    private boolean mode = true;
    /**
     * 默认是所有包括图片和视频
     */
    private int chooseMode = PictureMimeType.ofAll();
    /**
     * 最大图片显示数量
     */
    private int maxSelectNum = 9;
    /**
     * 默认是多选
     */
    private int SelectModel = PictureConfig.MULTIPLE;
    /**
     * 默认显示拍照按钮
     */
    private boolean cb_isCamera = true;
    int itemWidth;

    private String outputCameraPath = "";
    private String outputVideoPath = "";
    private int loginType;
    private OnImageDeleteListener onImageDeleteListener;
    private long videoDuration = PictureConfig.DAFAULT_VIDEO_DURATION;

    public PictureSelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPictureSelectorView(context, attrs);
    }

    /**
     * 初始化数据
     *
     * @param activity     当前Activity
     * @param colume       列数
     * @param maxSelectNum 最大显示图片数量
     * @param totalWidth   整个控件显示的宽度，单位px
     */
    public void initData(final Activity activity, int colume, int maxSelectNum, int totalWidth) {
        this.activity = activity;
        this.maxSelectNum = maxSelectNum;
        if (maxSelectNum == 1) {
            SelectModel = PictureConfig.SINGLE;
        }
//        FullyGridLayoutManager manager = new FullyGridLayoutManager(activity, colume, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, colume, GridLayoutManager.VERTICAL, false));
        //间距和列数
        recyclerView.addItemDecoration(new SpacesItemDecoration(8, colume));
        // 默认item的宽
        itemWidth = (totalWidth - (colume - 1) * (int) (activity.getResources().getDisplayMetrics().density * 8 + 0.5f)) / colume;
        adapter = new PictureSelectAdapter(activity, onAddPicClickListener, itemWidth);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        adapter.setIsSpaceType(false);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new PictureSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(activity).openPreview().setStyle(loginType).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(activity).openPreview().setStyle(loginType).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(activity).openPreview().setStyle(loginType).externalPictureAudio(media.getPath());
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void onDelClick(int position) {
                if (onImageDeleteListener != null) {
                    onImageDeleteListener.onImageDelete(position);
                }
            }
        });
    }

    /**
     * 空间图片选择时初始化数据
     *
     * @param activity
     * @param colume
     * @param maxSelectNum
     * @param totalWidth
     * @param onItemShowPicClickListener
     */

    public void initData(final Activity activity, int colume, int maxSelectNum, int totalWidth, PictureSelectAdapter.OnItemShowPicClickListener onItemShowPicClickListener) {
        this.activity = activity;
        this.maxSelectNum = maxSelectNum;
        if (maxSelectNum == 1) {
            SelectModel = PictureConfig.SINGLE;
        }
//        FullyGridLayoutManager manager = new FullyGridLayoutManager(activity, colume, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, colume, GridLayoutManager.VERTICAL, false));
        //间距和列数
        recyclerView.addItemDecoration(new SpacesItemDecoration(8, colume));
        // 默认item的宽
        itemWidth = (totalWidth - (colume - 1) * (int) (activity.getResources().getDisplayMetrics().density * 8 + 0.5f)) / colume;
        adapter = new PictureSelectAdapter(activity, onItemShowPicClickListener, itemWidth, 6);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new PictureSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(activity).openPreview().setStyle(loginType).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(activity).openPreview().setStyle(loginType).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(activity).openPreview().setStyle(loginType).externalPictureAudio(media.getPath());
                            break;
                        default:
                            break;
                    }
                }
            }

            @Override
            public void onDelClick(int position) {
                if (onImageDeleteListener != null) {
                    onImageDeleteListener.onImageDelete(position);
                }
            }
        });
    }

    private PictureSelectAdapter.onAddPicClickListener onAddPicClickListener = new PictureSelectAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            if (mode) {
                // 进入相册 以下是例子：不需要的api可以不写
                PictureSelector.create(activity)
                        // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .openGallery(chooseMode)
                        // 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
//                        .theme(themeId)
                        // 最大图片选择数量
                        .maxSelectNum(maxSelectNum)
                        // 最小选择数量
                        .minSelectNum(1)
                        // 每行显示个数
                        .imageSpanCount(4)
                        // 多选 or 单选
                        .selectionMode(SelectModel)
                        // 自定义拍照保存路径
                        .setOutputCameraPath(outputCameraPath)
                        // 自定义拍视频保存路径
                        .setOutputVideoPath(outputVideoPath)
                        // 是否可预览图片
                        .previewImage(true)
                        // 是否显示拍照按钮
                        .isCamera(cb_isCamera)
                        // 是否裁剪
                        .enableCrop(false)
                        // 是否压缩
                        .compress(false)
                        // glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .glideOverride(160, 160)
                        // 是否传入已选图片
                        .selectionMedia(selectList)
                        //视频时长限制
                        .videoDuration(videoDuration)
                        //视频大小限制
                        .setStyle(loginType)
                        //结果回调onActivityResult code
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            } else {
                // 单独拍照
                PictureSelector.create(activity)
                        // 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                        .openCamera(chooseMode)
                        // 主题样式设置 具体参考 values/styles
//                        .theme(themeId)
                        // 最大图片选择数量
                        .maxSelectNum(maxSelectNum)
                        // 最小选择数量
                        .minSelectNum(1)
                        // 多选 or 单选
                        .selectionMode(PictureConfig.SINGLE)
                        // 是否可预览图片
                        .previewImage(true)
                        // 自定义拍照保存路径
                        .setOutputCameraPath(outputCameraPath)
                        // 自定义拍视频保存路径
                        .setOutputVideoPath(outputVideoPath)
                        // 是否显示拍照按钮
                        .isCamera(false)
                        // 是否裁剪
                        .enableCrop(true)
                        // 是否压缩
                        .compress(false)
                        // glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .glideOverride(160, 160)
                        // 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .withAspectRatio(1, 1)
                        // 是否显示uCrop工具栏，默认不显示
                        .hideBottomControls(true)
                        // 裁剪框是否可拖拽
                        .freeStyleCropEnabled(true)
                        // 是否圆形裁剪
                        .circleDimmedLayer(true)
                        // 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                        .showCropFrame(false)
                        // 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                        .showCropGrid(false)
                        // 是否传入已选图片
                        .selectionMedia(selectList)
                        //预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        .previewEggs(false)
                        //视频时长限制
                        .videoDuration(videoDuration)
                        .setStyle(loginType)
                        //结果回调onActivityResult code
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        }
    };

    public PictureSelectAdapter.onAddPicClickListener getOnAddPicClickListener() {
        return onAddPicClickListener;
    }

    public void setOutputCameraPath(String outputCameraPath) {
        this.outputCameraPath = outputCameraPath;
    }

    public void setOutputVideoPath(String outputVideoPath) {
        this.outputVideoPath = outputVideoPath;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public boolean isMode() {
        return mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public int getChooseMode() {
        return chooseMode;
    }

    public void setChooseMode(int chooseMode) {
        this.chooseMode = chooseMode;
    }

    public int getMaxSelectNum() {
        return maxSelectNum;
    }

    public void setMaxSelectNum(int maxSelectNum) {
        this.maxSelectNum = maxSelectNum;
    }

    public int getSelectModel() {
        return SelectModel;
    }

    public void setSelectModel(int selectModel) {
        SelectModel = selectModel;
    }

    public boolean isCb_isCamera() {
        return cb_isCamera;
    }

    public void setCb_isCamera(boolean cb_isCamera) {
        this.cb_isCamera = cb_isCamera;
    }

    public void setVideoDuration(long videoDuration) {
        this.videoDuration = videoDuration;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的

                    if (selectList.size() >= 2) {
                        LocalMedia media = selectList.get(selectList.size() - 1);
                        if (!media.getPictureType().split("/")[0].equalsIgnoreCase(selectList.get(selectList.size() - 2).getPictureType().split("/")[0])) {
//                             移除之前的刘最后一个
                            selectList.clear();
                            selectList.add(media);
                        }
                    }

                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }

    public void setDataForPicSelectView(List<LocalMedia> list) {
        selectList = list;
        adapter.setList(selectList);
        adapter.notifyDataSetChanged();
    }

    private void initPictureSelectorView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.picture_select_layout, this, true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        // 默认item的宽
        itemWidth = (context.getResources().getDisplayMetrics().widthPixels - 2 * (int) (context.getResources().getDisplayMetrics().density * 8 + 0.5f)) / 3;
    }

    public List<LocalMedia> getSelectList() {
        return selectList;
    }

    public interface OnImageDeleteListener {
        void onImageDelete(int position);
    }

    public void setOnImageDeleteListener(OnImageDeleteListener onImageDeleteListener) {
        this.onImageDeleteListener = onImageDeleteListener;
    }
}