package com.luck.picture.lib.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.R;
import com.luck.picture.lib.anim.OptAnimationLoader;
import com.luck.picture.lib.cameralibrary.util.FileUtil;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.DebugUtil;
import com.luck.picture.lib.tools.StringUtils;
import com.luck.picture.lib.tools.VoiceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.luck.picture.lib.config.PictureConfig.TYPE_AUDIO;
import static com.luck.picture.lib.config.PictureConfig.TYPE_IMAGE;
import static com.luck.picture.lib.config.PictureConfig.TYPE_VIDEO;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.lib.adapter
 * email：893855882@qq.com
 * data：2016/12/30
 */
public class PictureImageGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int DURATION = 450;
    private Context context;
    private boolean showCamera = true;
    private OnPhotoSelectChangedListener imageSelectChangedListener;
    private int maxSelectNum;
    private List<LocalMedia> images = new ArrayList<LocalMedia>();        //展现的数据
    private List<LocalMedia> selectImages = new ArrayList<LocalMedia>();  //最新选中的集合
    private boolean enablePreview;
    private int selectMode = PictureConfig.MULTIPLE;
    private boolean enablePreviewVideo = false;
    private boolean enablePreviewAudio = false;
    private boolean is_checked_num;
    private boolean enableVoice;
    private int overrideWidth, overrideHeight;
    private float sizeMultiplier;
    private Animation animation;
    private PictureSelectionConfig config;
    private int mimeType;
    private boolean zoomAnim;
    private long videoDuration, videoSize, maxImageSize;

    private boolean isJudeImageSize;

    public PictureImageGridAdapter(Context context, PictureSelectionConfig config) {
        this.context = context;
        this.config = config;
        this.selectMode = config.selectionMode;
        this.showCamera = config.isCamera;
        this.maxSelectNum = config.maxSelectNum;
        this.enablePreview = config.enablePreview;
        this.enablePreviewVideo = config.enPreviewVideo;
        this.enablePreviewAudio = config.enablePreviewAudio;
        this.is_checked_num = config.checkNumMode;
        this.overrideWidth = config.overrideWidth;
        this.overrideHeight = config.overrideHeight;
        this.enableVoice = config.openClickSound;
        this.sizeMultiplier = config.sizeMultiplier;
        this.mimeType = config.mimeType;
        this.zoomAnim = config.zoomAnim;
        this.videoDuration = config.videoDuration;
        this.videoSize = config.videoSize;
        this.isJudeImageSize = config.isJudgeImageSize;
        this.maxImageSize = config.maxImageSize;
        animation = OptAnimationLoader.loadAnimation(context, R.anim.modal_in);
    }

    public void setShowCamera(boolean showCamera) {
        this.showCamera = showCamera;
    }

    public void bindImagesData(List<LocalMedia> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    public void bindSelectImages(List<LocalMedia> images) {
        // 这里重新构构造一个新集合，不然会产生已选集合一变，结果集合也会添加的问题
        List<LocalMedia> selection = new ArrayList<>();
        for (LocalMedia media : images) {
            selection.add(media);
        }
        this.selectImages = selection;
        subSelectPosition();
        if (imageSelectChangedListener != null) {
            imageSelectChangedListener.onChange(selectImages);
        }
    }

    public List<LocalMedia> getSelectedImages() {
        if (selectImages == null) {
            selectImages = new ArrayList<>();
        }
        return selectImages;
    }

    public List<LocalMedia> getImages() {
        if (images == null) {
            images = new ArrayList<>();
        }
        return images;
    }

    @Override
    public int getItemViewType(int position) {
        if (showCamera && position == 0) {
            return PictureConfig.TYPE_CAMERA;
        } else {
            return PictureConfig.TYPE_PICTURE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == PictureConfig.TYPE_CAMERA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_item_camera, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_image_grid_item, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == PictureConfig.TYPE_CAMERA) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageSelectChangedListener != null) {
                        imageSelectChangedListener.onTakePhoto();
                    }
                }
            });
        } else {
            final ViewHolder contentHolder = (ViewHolder) holder;
            final LocalMedia image = images.get(showCamera ? position - 1 : position);     //显示第一个那么数据 与位子相差一 ！！！
            image.position = contentHolder.getAdapterPosition();
            final String path = image.getPath();
            final String pictureType = image.getPictureType();
            contentHolder.ll_check.setVisibility(selectMode ==
                    PictureConfig.SINGLE ? View.GONE : View.VISIBLE); //单选或者多选
//            if (is_checked_num) {
            notifyCheckChanged(contentHolder, image);
//            }
            selectImage(contentHolder, isSelected(image), false);  //设置好默认

            final int picture = PictureMimeType.isPictureType(pictureType);
            boolean gif = PictureMimeType.isGif(pictureType);
            contentHolder.tv_isGif.setVisibility(gif ? View.VISIBLE : View.GONE);
            if (mimeType == PictureMimeType.ofAudio()) {
                contentHolder.tv_duration.setVisibility(View.VISIBLE);
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.picture_audio);
                StringUtils.modifyTextViewDrawable(contentHolder.tv_duration, drawable, 0);
            } else {
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.video_icon);
                StringUtils.modifyTextViewDrawable(contentHolder.tv_duration, drawable, 0);
                contentHolder.tv_duration.setVisibility(picture == PictureConfig.TYPE_VIDEO
                        ? View.VISIBLE : View.GONE);
            }
            int width = image.getWidth();
            int height = image.getHeight();
            int h = width * 5;
            contentHolder.tv_long_chart.setVisibility(height > h ? View.VISIBLE : View.GONE);
            long duration = image.getDuration();
            contentHolder.tv_duration.setText(DateUtils.timeParse(duration));
            if (mimeType == PictureMimeType.ofAudio()) {
                contentHolder.iv_picture.setImageResource(R.drawable.audio_placeholder);
            } else {
                RequestOptions options = new RequestOptions()
                        .dontAnimate()
                        .dontTransform()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .placeholder(R.drawable.falseimg)
                        .fallback(R.drawable.falseimg)
                        .error(R.drawable.falseimg);
                if (overrideWidth <= 0 && overrideHeight <= 0) {

                } else {
                    options = options.override(overrideWidth, overrideHeight);
                }
                Glide.with(context)
                        .load(path)
                        .apply(options)
                        .into(contentHolder.iv_picture);
            }
            if (enablePreview || enablePreviewVideo || enablePreviewAudio) {
                contentHolder.ll_check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeCheckboxState(contentHolder, image);
                    }
                });

            }
            contentHolder.contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 如原图路径不存在或者路径存在但文件不存在
                    if (!new File(path).exists()) {
                        Toast.makeText(context, context.getString(R.string.picture_error), Toast.LENGTH_LONG)
                                .show();
                        return;
                    }
                    if (picture == PictureConfig.TYPE_IMAGE && (enablePreview
                            || selectMode == PictureConfig.SINGLE)) {
                        int index = showCamera ? position - 1 : position;
                        imageSelectChangedListener.onPictureClick(image, index); //根据index 位子Activity中获取
                    } else if (picture == PictureConfig.TYPE_VIDEO && (enablePreviewVideo
                            || selectMode == PictureConfig.SINGLE)) {
                        int index = showCamera ? position - 1 : position;
                        imageSelectChangedListener.onPictureClick(image, index);
                    } else if (picture == TYPE_AUDIO && (enablePreviewAudio
                            || selectMode == PictureConfig.SINGLE)) {
                        int index = showCamera ? position - 1 : position;
                        imageSelectChangedListener.onPictureClick(image, index);
                    } else {
                        changeCheckboxState(contentHolder, image);
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return showCamera ? images.size() + 1 : images.size();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        View headerView;
        TextView tv_title_camera;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerView = itemView;
            tv_title_camera = (TextView) itemView.findViewById(R.id.tv_title_camera);
            String title = mimeType == PictureMimeType.ofAudio() ?
                    context.getString(R.string.picture_tape)
                    : context.getString(R.string.picture_take_picture);
            tv_title_camera.setText(title);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_picture;
        TextView check;
        TextView tv_duration, tv_isGif, tv_long_chart;
        View contentView;
        LinearLayout ll_check;

        public ViewHolder(View itemView) {
            super(itemView);
            contentView = itemView;
            iv_picture = (ImageView) itemView.findViewById(R.id.iv_picture);
            check = (TextView) itemView.findViewById(R.id.check);
            ll_check = (LinearLayout) itemView.findViewById(R.id.ll_check);
            tv_duration = (TextView) itemView.findViewById(R.id.tv_duration);
            tv_isGif = (TextView) itemView.findViewById(R.id.tv_isGif);
            tv_long_chart = (TextView) itemView.findViewById(R.id.tv_long_chart);
        }
    }

    public boolean isSelected(LocalMedia image) {  //判断当前页面是不是被选中过的
        for (LocalMedia media : selectImages) {
            if (media.getPath().equals(image.getPath())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 选择按钮更新
     */
    public void notifyCheckChanged(ViewHolder viewHolder, LocalMedia imageBean) {
        viewHolder.check.setText("");
        for (LocalMedia media : selectImages) {
            if (media.getPath().equals(imageBean.getPath())) {
                imageBean.setNum(media.getNum());
                media.setPosition(imageBean.getPosition());
                viewHolder.check.setText(String.valueOf(imageBean.getNum()));
            }
        }
    }

    /**
     * 改变图片选中状态
     *
     * @param contentHolder
     * @param image
     */

    private void changeCheckboxState(ViewHolder contentHolder, LocalMedia image) {
        boolean isChecked = contentHolder.check.isSelected();

        String pictureType = selectImages.size() > 0 ? selectImages.get(0).getPictureType() : "";  //获取的第一个视频或者图片
        if (!TextUtils.isEmpty(pictureType)) {
            // 控制最多选一个视频 默认不加选择多个视频
            if (PictureMimeType.isPictureType(image.getPictureType()) == TYPE_VIDEO && !isChecked) {
                boolean toEqual = PictureMimeType.mimeToEqual(pictureType, image.getPictureType());  //类型是不是图片
                if (!toEqual) {
                    Toast.makeText(context, context.getString(R.string.picture_rule), Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                Toast.makeText(context, "最多选择一个视频", Toast.LENGTH_LONG).show();
                return;

            }
            boolean toEqual = PictureMimeType.mimeToEqual(pictureType, image.getPictureType());
            if (!toEqual) {
                Toast.makeText(context, context.getString(R.string.picture_rule), Toast.LENGTH_LONG)
                        .show();
                return;
            }
        }
        if (PictureMimeType.isPictureType(image.getPictureType()) == TYPE_IMAGE && !isChecked) {
            if (isJudeImageSize) {
                if (FileUtil.getFileSize(image.getPath()) > maxImageSize) {
                    Toast.makeText(context, String.format(Locale.getDefault(), "图片不允许大于%dM",
                            maxImageSize / (1024 * 1024)), Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
        if (PictureMimeType.isPictureType(image.getPictureType()) == TYPE_VIDEO && !isChecked) {
            if (image.getDuration() / 1000 > videoDuration / 1000) {
                Toast.makeText(context, String.format(Locale.getDefault(), "视频时长超过%ds",
                        videoDuration / 1000), Toast.LENGTH_LONG).show();
                return;
            }
            if (FileUtil.getFileSize(image.getPath()) > videoSize) {
                Toast.makeText(context, String.format(Locale.getDefault(), "视频大小不能超过%dm",
                        videoSize / (1024 * 1024)), Toast.LENGTH_LONG).show();
                return;
            }
        }

        if (selectImages.size() >= maxSelectNum && !isChecked) {
            boolean eqImg = pictureType.startsWith(PictureConfig.IMAGE);
            @SuppressLint("StringFormatMatches") String str = eqImg ? context.getString(R.string.picture_message_max_num, maxSelectNum)
                    : context.getString(R.string.picture_message_video_max_num, maxSelectNum);
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();
            return;
        }

        if (isChecked) {
            for (LocalMedia media : selectImages) {
                if (media.getPath().equals(image.getPath())) {    //
                    selectImages.remove(media);
                    DebugUtil.i("selectImages remove::", config.selectionMedias.size() + "");
                    subSelectPosition();
                    disZoom(contentHolder.iv_picture);
                    break;
                }
            }
        } else {
            selectImages.add(image);
            DebugUtil.i("selectImages add::", config.selectionMedias.size() + "");
            image.setNum(selectImages.size());
            VoiceUtils.playVoice(context, enableVoice);
            zoom(contentHolder.iv_picture);
        }

        //通知点击项发生了改变
//        notifyItemChanged(contentHolder.getAdapterPosition());
        notifyDataSetChanged();
        selectImage(contentHolder, !isChecked, true);   //点击开始动画
        if (imageSelectChangedListener != null) {
            imageSelectChangedListener.onChange(selectImages);
        }
    }

    /**
     * 更新选择的顺序
     */
    private void subSelectPosition() {
        if (is_checked_num) {
            int size = selectImages.size();
            for (int index = 0, length = size; index < length; index++) {
                LocalMedia media = selectImages.get(index);
                media.setNum(index + 1);
                notifyDataSetChanged();
//                notifyItemChanged(media.position);
            }
        }
    }

    public void selectImage(ViewHolder holder, boolean isChecked, boolean isAnim) {
        holder.check.setSelected(isChecked);
        if (isChecked) {
            if (isAnim) {
                if (animation != null) {
                    holder.check.startAnimation(animation);
                }
            }
            holder.iv_picture.setColorFilter(ContextCompat.getColor
                    (context, R.color.image_overlay_true), PorterDuff.Mode.SRC_ATOP);
        } else {
            holder.iv_picture.setColorFilter(ContextCompat.getColor
                    (context, R.color.image_overlay_false), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public interface OnPhotoSelectChangedListener {
        void onTakePhoto();

        void onChange(List<LocalMedia> selectImages);


        void onPictureClick(LocalMedia media, int position);
    }

    public void setOnPhotoSelectChangedListener(OnPhotoSelectChangedListener
                                                        imageSelectChangedListener) {
        this.imageSelectChangedListener = imageSelectChangedListener;
    }

    private void zoom(ImageView iv_img) {
        if (zoomAnim) {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(iv_img, "scaleX", 1f, 1.12f),
                    ObjectAnimator.ofFloat(iv_img, "scaleY", 1f, 1.12f)
            );
            set.setDuration(DURATION);
            set.start();
        }
    }

    private void disZoom(ImageView iv_img) {
        if (zoomAnim) {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(iv_img, "scaleX", 1.12f, 1f),
                    ObjectAnimator.ofFloat(iv_img, "scaleY", 1.12f, 1f)
            );
            set.setDuration(DURATION);
            set.start();
        }
    }
}
