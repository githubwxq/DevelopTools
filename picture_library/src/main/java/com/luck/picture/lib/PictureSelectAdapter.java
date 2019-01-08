package com.luck.picture.lib;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.luck.picture.lib.config.PictureConfig.TYPE_VIDEO;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.pictureselector.adapter
 * email：893855882@qq.com
 * data：16/7/27   图片显示控件 需要封装
 */
public class PictureSelectAdapter extends
        RecyclerView.Adapter<PictureSelectAdapter.ViewHolder> {
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private List<LocalMedia> list = new ArrayList<>();
    private int selectMax = 9;
    private Context context;
    boolean isOnlyVideo = false;  //只有一个视频否者多个
    private int width;   //控件宽度
    private int type = 0;
    private boolean isSpaceType = false;
    private static final int NINE_PIC = 9;

    /**
     * 点击添加图片跳转
     */
    private onAddPicClickListener mOnAddPicClickListener;

    /**
     * x显示弹窗的
     */
    private OnItemShowPicClickListener onItemShowPicClickListener;

    public interface onAddPicClickListener {
        void onAddPicClick();
    }

    public interface OnItemShowPicClickListener {
        void onClick();
    }

    public PictureSelectAdapter(Context context, onAddPicClickListener mOnAddPicClickListener, int width) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mOnAddPicClickListener = mOnAddPicClickListener;
        this.width = width; // 宽度
    }

    public PictureSelectAdapter(Context context, OnItemShowPicClickListener onItemShowPicClickListener, int width, int type) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.onItemShowPicClickListener = onItemShowPicClickListener;
        this.width = width; // 宽度
        this.type = type;
    }

    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    public void setList(List<LocalMedia> list) {
        this.list = list;
        if (list.size() > 0) {
            if (PictureMimeType.isPictureType(list.get(0).getPictureType()) == TYPE_VIDEO) {
                isOnlyVideo = true;
            } else {
                isOnlyVideo = false;
            }

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImg;
        LinearLayout ll_del;
        TextView tv_duration;
        RelativeLayout rl_frame;
        RelativeLayout rl_bottom;
        TextView tv_Pic;

        public ViewHolder(View view) {
            super(view);
            mImg = (ImageView) view.findViewById(R.id.fiv);
            ll_del = (LinearLayout) view.findViewById(R.id.ll_del);
            tv_duration = (TextView) view.findViewById(R.id.tv_duration);
            rl_frame = (RelativeLayout) view.findViewById(R.id.rl_frame);
            rl_bottom = (RelativeLayout) view.findViewById(R.id.rl_bottom);
            tv_Pic = (TextView) view.findViewById(R.id.tv_img_number);
        }
    }

    @Override
    public int getItemCount() {
        if (isOnlyVideo) {
            return 1;
        }
        if (list.size() < selectMax) {
            return list.size() + 1;
        } else {
            return list.size();
        }
     /*   if (list.size() > NINE_PIC) {
            if (list.size() != selectMax) {
                return 10;
            } else {
                return NINE_PIC;
            }
        } else {
            return list.size() + 1;
        }*/
    }

    @Override
    public int getItemViewType(int position) {
        if (isOnlyVideo) {
            return TYPE_PICTURE;
        }
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
      /*  if (isOnlyVideo) {
            return TYPE_PICTURE;
        }
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            if (position == NINE_PIC) {
                return TYPE_CAMERA;
            } else {
                return TYPE_PICTURE;
            }
        }*/
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.picture_select_item,
                viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    private boolean isShowAddItem(int position) {
        int size = list.size() == 0 ? 0 : list.size();
        return position == size;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // modify by zh 20180315 空间发布显示的需求改变
        if (isSpaceType) {
            viewHolder.rl_frame.getLayoutParams().height = width;
            viewHolder.rl_frame.getLayoutParams().width = width;
            if (getItemViewType(position) == TYPE_CAMERA) {
                viewHolder.mImg.setImageResource(R.drawable.btn_upload_pic_selector);
                /*if (position == NINE_PIC || position == 0) {
                    viewHolder.mImg.setImageResource(R.drawable.btn_upload_pic_selector);
                }*/
                viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (type == 0) {
                            mOnAddPicClickListener.onAddPicClick();
                        } else {
                            onItemShowPicClickListener.onClick();
                        }
                    }
                });
                viewHolder.ll_del.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.ll_del.setVisibility(View.VISIBLE);
                viewHolder.ll_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int index = viewHolder.getAdapterPosition();
                        // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                        // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                        if (index != RecyclerView.NO_POSITION) {
                            if (PictureMimeType.isPictureType(list.get(index).getPictureType()) == TYPE_VIDEO) {
                                isOnlyVideo = false;
                            }
                            list.remove(index);
//                        notifyItemRemoved(index);
//                        notifyItemRangeChanged(index, list.size());
                            notifyDataSetChanged();
                            if (mItemClickListener != null) {
                                mItemClickListener.onDelClick(index);
                            }
//                            DebugUtil.i("delete position:", index + "--->remove after:" + list.size());
                        }
                    }
                });
                LocalMedia media = list.get(position);
                int mimeType = media.getMimeType();
                String path = "";
                if (media.isCut() && !media.isCompressed()) {
                    // 裁剪过
                    path = media.getCutPath();
                } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                    // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                    path = media.getCompressPath();
                } else {
                    // 原图
                    path = media.getPath();
                }
                // 图片
//                if (media.isCompressed()) {
//                    Log.i("compress image result:", new File(media.getCompressPath()).length() / 1024 + "k");
//                    Log.i("压缩地址::", media.getCompressPath());
//                }
//                Log.i("原图地址::", media.getPath());
                int pictureType = PictureMimeType.isPictureType(media.getPictureType());
//                if (media.isCut()) {
//                    Log.i("裁剪地址::", media.getCutPath());
//                }
                long duration = media.getDuration();
                viewHolder.tv_duration.setVisibility(pictureType == TYPE_VIDEO
                        ? View.VISIBLE : View.GONE);

                if (pictureType == TYPE_VIDEO) {
                    //是视频
                    viewHolder.rl_frame.getLayoutParams().width = (int) (width * 16.0 / 9);
                    viewHolder.rl_frame.getLayoutParams().height = width;
                    viewHolder.mImg.getLayoutParams().width = viewHolder.rl_frame.getLayoutParams().width;
                    viewHolder.mImg.getLayoutParams().height = width;
                } else {
                    //是图片
                    if (position > NINE_PIC) {
                        viewHolder.rl_frame.setVisibility(View.GONE);
                    } else {
                        if (position == NINE_PIC - 1) {
                            viewHolder.rl_bottom.setVisibility(View.VISIBLE);
                            viewHolder.tv_Pic.setText(list.size() + "");
                        } else {
                            viewHolder.rl_bottom.setVisibility(View.GONE);
                            viewHolder.rl_frame.setVisibility(View.VISIBLE);
                        }
                        viewHolder.rl_frame.getLayoutParams().height = width;
                        viewHolder.rl_frame.getLayoutParams().width = width;
                    }
                }
                if (mimeType == PictureMimeType.ofAudio()) {
                    viewHolder.tv_duration.setVisibility(View.VISIBLE);
                    Drawable drawable = ContextCompat.getDrawable(context, R.drawable.picture_audio);
                    StringUtils.modifyTextViewDrawable(viewHolder.tv_duration, drawable, 0);
                } else {
                    Drawable drawable = ContextCompat.getDrawable(context, R.drawable.video_icon);
                    StringUtils.modifyTextViewDrawable(viewHolder.tv_duration, drawable, 0);
                }
                viewHolder.tv_duration.setText(DateUtils.timeParse(duration));
                if (mimeType == PictureMimeType.ofAudio()) {
                    viewHolder.mImg.setImageResource(R.drawable.audio_placeholder);
                } else {
                    RequestOptions options = new RequestOptions()
                            .dontAnimate()
                            .dontTransform()
                            .priority(Priority.HIGH)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .placeholder(R.drawable.falseimg)
                            .fallback(R.drawable.falseimg)
                            .error(R.drawable.falseimg);
                    Glide.with(context)
                            .load(path)
                            .apply(options)
                            .into(viewHolder.mImg);
                }
            }
        } else {    //确定宽高
            viewHolder.rl_frame.getLayoutParams().height = width;
            viewHolder.rl_frame.getLayoutParams().width = width;
            //少于8张，显示继续添加的图标 但是如果是视频的话另外处理
            if (getItemViewType(position) == TYPE_CAMERA) {
                viewHolder.mImg.setImageResource(R.drawable.btn_upload_pic_selector);
                viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (type == 0) {
                            mOnAddPicClickListener.onAddPicClick();
                        } else {
                            onItemShowPicClickListener.onClick();
                        }
                    }
                });
                viewHolder.ll_del.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.ll_del.setVisibility(View.VISIBLE);
                viewHolder.ll_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int index = viewHolder.getAdapterPosition();
                        // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                        // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                        if (index != RecyclerView.NO_POSITION) {
                            if (PictureMimeType.isPictureType(list.get(index).getPictureType()) == TYPE_VIDEO) {
                                isOnlyVideo = false;
                            }
                            list.remove(index);
//                        notifyItemRemoved(index);
//                        notifyItemRangeChanged(index, list.size());
                            notifyDataSetChanged();
//                            DebugUtil.i("delete position:", index + "--->remove after:" + list.size());
                            if (mItemClickListener != null) {
                                mItemClickListener.onDelClick(index);
                            }
                        }
                    }
                });
                LocalMedia media = list.get(position);
                int mimeType = media.getMimeType();
                String path = "";
                if (media.isCut() && !media.isCompressed()) {
                    // 裁剪过
                    path = media.getCutPath();
                } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                    // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                    path = media.getCompressPath();
                } else {
                    // 原图
                    path = media.getPath();
                }
                // 图片
//                if (media.isCompressed()) {
//                    Log.i("compress image result:", new File(media.getCompressPath()).length() / 1024 + "k");
//                    Log.i("压缩地址::", media.getCompressPath());
//                }

//                Log.i("原图地址::", media.getPath());
                int pictureType = PictureMimeType.isPictureType(media.getPictureType());
//                if (media.isCut()) {
//                    Log.i("裁剪地址::", media.getCutPath());
//                }
                long duration = media.getDuration();
                viewHolder.tv_duration.setVisibility(pictureType == TYPE_VIDEO
                        ? View.VISIBLE : View.GONE);

                if (pictureType == TYPE_VIDEO) {
                    //是视频
                    viewHolder.rl_frame.getLayoutParams().width = (int) (width * 16.0 / 9);
                    viewHolder.rl_frame.getLayoutParams().height = width;
                    viewHolder.mImg.getLayoutParams().width = viewHolder.rl_frame.getLayoutParams().width;
                    viewHolder.mImg.getLayoutParams().height = width;
                } else {
                    //是图片33333333
                    viewHolder.rl_frame.getLayoutParams().height = width;
                    viewHolder.rl_frame.getLayoutParams().width = width;
                }
                if (mimeType == PictureMimeType.ofAudio()) {
                    viewHolder.tv_duration.setVisibility(View.VISIBLE);
                    Drawable drawable = ContextCompat.getDrawable(context, R.drawable.picture_audio);
                    StringUtils.modifyTextViewDrawable(viewHolder.tv_duration, drawable, 0);
                } else {
                    Drawable drawable = ContextCompat.getDrawable(context, R.drawable.video_icon);
                    StringUtils.modifyTextViewDrawable(viewHolder.tv_duration, drawable, 0);
                }
                viewHolder.tv_duration.setText(DateUtils.timeParse(duration));
                if (mimeType == PictureMimeType.ofAudio()) {
                    viewHolder.mImg.setImageResource(R.drawable.audio_placeholder);
                } else {

//                RequestOptions options = new RequestOptions()
//                        .centerCrop()
//                        .placeholder(R.color.color_4d)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL);

                    RequestOptions options = new RequestOptions()
                            .dontAnimate()
                            .dontTransform()
                            .priority(Priority.HIGH)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .placeholder(R.drawable.falseimg)
                            .fallback(R.drawable.falseimg)
                            .error(R.drawable.falseimg);
                    Glide.with(context)
                            .load(path)
                            .apply(options)
                            .into(viewHolder.mImg);
                }
            }
        }
        //itemView 的点击事件
        if (mItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = viewHolder.getAdapterPosition();
                    mItemClickListener.onItemClick(adapterPosition, v);
                }
            });
        }
    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);

        void onDelClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void setIsSpaceType(boolean isSpaceType) {
        this.isSpaceType = isSpaceType;
    }
}
