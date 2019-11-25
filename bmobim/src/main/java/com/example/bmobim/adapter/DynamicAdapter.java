package com.example.bmobim.adapter;

import android.app.Activity;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobim.R;
import com.example.bmobim.activity.UserInfoActivity;
import com.example.bmobim.bean.DynamicBean;
import com.example.bmobim.bean.DynamicComment;
import com.juziwl.uilibrary.activity.WatchImagesActivity;
import com.juziwl.uilibrary.emoji.MTextView;
import com.juziwl.uilibrary.nestlistview.NestFullListView;
import com.juziwl.uilibrary.nestlistview.NestFullListViewAdapter;
import com.juziwl.uilibrary.nestlistview.NestFullViewHolder;
import com.juziwl.uilibrary.ninegridview.NewNineGridlayout;
import com.juziwl.uilibrary.ninegridview.NineGridlayout;
import com.juziwl.uilibrary.popupwindow.EasyPopup;
import com.juziwl.uilibrary.popupwindow.EditPopupWindow;
import com.juziwl.uilibrary.popupwindow.XGravity;
import com.juziwl.uilibrary.popupwindow.YGravity;
import com.luck.picture.lib.PictureVideoPlayActivity;
import com.wxq.commonlibrary.bmob.CommonBmobUser;
import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.util.ConvertUtils;
import com.wxq.commonlibrary.util.KeyboardUtils;
import com.wxq.commonlibrary.util.ScreenUtils;
import com.wxq.commonlibrary.util.SpanUtils;
import com.wxq.commonlibrary.util.StringUtils;
import com.wxq.commonlibrary.util.TimeUtils;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.util.UIHandler;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class DynamicAdapter extends BaseQuickAdapter<DynamicBean, BaseViewHolder> {

    private Activity activity;

    public DynamicAdapter(List<DynamicBean> dynamicBeanList, Activity activity) {
        super(R.layout.item_dynamic, dynamicBeanList);
        this.activity = activity;

    }

    class ClickSpan extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            Log.e("aaa", "aaa");
            ToastUtils.showShort("aaa");
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(mContext.getResources().getColor(R.color.common_576b95));
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicBean item) {
        ImageView image = helper.getView(R.id.iv_img);
        LoadingImgUtil.loadimg(item.publishUser.avatar, image, true);
        image.setOnClickListener(v -> UserInfoActivity.navToActivity(mContext, item.publishUser));
        TextView tv_name = helper.getView(R.id.tv_name);
        helper.setText(R.id.tv_name, item.publishUser.getUsername());
        helper.setText(R.id.tv_time, TimeUtils.getFriendlyTimeSpanByNow(item.getCreatedAt()));
        MTextView content = helper.getView(R.id.expandable_text);
        ImageView iv_video_pic = helper.getView(R.id.iv_video_pic);
        content.setMText(item.content);

        if (StringUtils.isEmpty(item.pics)) {
            helper.setGone(R.id.ngl_pics, false);
        } else {
            helper.setGone(R.id.ngl_pics, true);
            NewNineGridlayout piclayout = helper.getView(R.id.ngl_pics);
            piclayout.showPic(ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(100), item.pics, new NineGridlayout.onNineGirdItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    WatchImagesActivity.navToWatchImages(helper.getConvertView().getContext(), item.pics, position);
                }
            });
        }
        if (StringUtils.isEmpty(item.video) || StringUtils.isEmpty(item.videoImage)) {
            helper.setGone(R.id.rl_video, false);
        } else {
            helper.setGone(R.id.rl_video, true);
            LoadingImgUtil.loadimg(item.videoImage, iv_video_pic, false);
            iv_video_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PictureVideoPlayActivity.navToVideoPlay(helper.getConvertView().getContext(), item.video, item.videoImage, true, GlobalContent.VIDEOPATH);
                }
            });
        }

        helper.getView(R.id.iv_popup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //显示popupwindow
                showCirclePop(view, item);
            }
        });

//        、、显示评论
        if (item.dynamicCommentList != null && item.dynamicCommentList.size() > 0) {
            helper.setGone(R.id.iv_jiao, true);
            helper.setGone(R.id.iv_jiao, true);
        } else {
            helper.setGone(R.id.iv_jiao, false);
            helper.setGone(R.id.iv_jiao, false);
        }
        NestFullListView commentList = helper.getView(R.id.nest_full_listview);
        commentList.setAdapter(new NestFullListViewAdapter<DynamicComment>(R.layout.item_comment, item.dynamicCommentList) {
            @Override
            public void onBind(int pos, DynamicComment dynamicComment, NestFullViewHolder holder) {
                TextView contentView = holder.getView(R.id.tv_comment_receive);
                contentView.setMovementMethod(LinkMovementMethod.getInstance());
                contentView.setText(new SpanUtils().append(dynamicComment.author.getUsername() + "：").setClickSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(ContextCompat.getColor(mContext, R.color.red_200));
                    }

                    @Override
                    public void onClick(@NonNull View widget) {
                        UserInfoActivity.navToActivity(mContext, dynamicComment.author);
                    }
                }).append(dynamicComment.content).setForegroundColor(mContext.getResources().getColor(R.color.green_300)).create());
            }
        });

        TextView tv_zan_name=helper.getView(R.id.tv_zan_name);
        tv_zan_name.setMovementMethod(LinkMovementMethod.getInstance());
        //点赞处理
        if (item.praiseUsers.size() > 0) {
            helper.setGone(R.id.tv_zan_name, true);
            SpanUtils spanUtils = new SpanUtils().appendImage(R.mipmap.icon_blue_zan, SpanUtils.ALIGN_CENTER);
            for (int i = 0; i < item.praiseUsers.size(); i++) {
                int finalI = i;
                spanUtils.append(item.praiseUsers.get(i).getUsername() + (i == item.praiseUsers.size() - 1 ? "" : ",")).setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View view) {
                        UserInfoActivity.navToActivity(mContext, item.praiseUsers.get(finalI));
                    }
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        ds.setColor(ContextCompat.getColor(mContext, R.color.green_200));
                    }
                });
            }
            tv_zan_name.setText(spanUtils.create());
        } else {
            helper.setGone(R.id.tv_zan_name, false);
        }
    }

    private EasyPopup mCirclePop;

    private void showCirclePop(View view, DynamicBean item) {
        mCirclePop = EasyPopup.create()
                .setContentView(this.mContext, R.layout.layout_circle_comment)
                .setAnimationStyle(R.style.RightPopAnim)
                .setFocusAndOutsideEnable(true)
                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, final EasyPopup popup) {
                        TextView tv_praise = view.findViewById(R.id.tv_zan);
                        boolean hasPraise = false;
                        for (CommonBmobUser praiseUser : item.praiseUsers) {
                            if (praiseUser.getObjectId().equals(BmobUser.getCurrentUser(CommonBmobUser.class).getObjectId())) {
                                hasPraise = true;
                            }
                        }
                        if (hasPraise) {
                            tv_praise.setText("取消");
                        } else {
                            tv_praise.setText("赞");
                        }
                        boolean finalHasPraise = hasPraise;
                        tv_praise.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addOrCancelPraise(item, finalHasPraise);
                                popup.dismiss();
                            }
                        });
                        view.findViewById(R.id.tv_comment).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UIHandler.getInstance().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCommentEditPop(item);
                                    }
                                }, 100);
                                popup.dismiss();
                            }
                        });
                    }
                })
                .apply();

        mCirclePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }

        });
        mCirclePop.showAtAnchorView(view, YGravity.CENTER, XGravity.LEFT, 0, 0);
    }

    /**
     * 点赞
     *
     * @param item
     */
    private void addOrCancelPraise(DynamicBean item, boolean hasPraise) {
        CommonBmobUser currentUser = BmobUser.getCurrentUser(CommonBmobUser.class);
        if (hasPraise) {
            //移除当前用户
            for (int i = item.praiseUsers.size() - 1; i >= 0; i--) {
                if (item.praiseUsers.get(i).getObjectId().equals(currentUser.getObjectId())) {
                    item.praiseUsers.remove(i);
                }
            }
        } else {
            //添加当前用户
            item.praiseUsers.add(currentUser);
        }
        item.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtils.showShort(e.getMessage());
                } else {
                    notifyDataSetChanged();
                }
            }
        });
    }


    private EditPopupWindow editPop;

    private void showCommentEditPop(DynamicBean item) {
        KeyboardUtils.showSoftInput(activity);
        editPop = new EditPopupWindow(activity);
        editPop.setOnViewListener(new EditPopupWindow.OnViewListener() {
            @Override
            public void initViews(View view, EditPopupWindow popup) {

            }

            @Override
            public void getEditString(String text) {
                saveComment(item, text);
            }
        });
        editPop.showPopup(activity);
    }

    /**
     * 保存评论
     *
     * @param item
     * @param text
     */
    private void saveComment(DynamicBean item, String text) {
        DynamicComment dynamicComment = new DynamicComment();
        dynamicComment.author = BmobUser.getCurrentUser(CommonBmobUser.class);
        dynamicComment.otherPeople = null;
        dynamicComment.content = text;
        dynamicComment.card = item;
        dynamicComment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                dynamicComment.setObjectId(s);
                dynamicComment.card = null;// 不然评论集合数组保存不了嵌套循环了
                item.dynamicCommentList.add(0, dynamicComment);
                item.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            ToastUtils.showShort("评论成功");
                            notifyDataSetChanged();
                        } else {
                            ToastUtils.showShort(e.getMessage());
                        }
                    }
                });
            }
        });
    }
}