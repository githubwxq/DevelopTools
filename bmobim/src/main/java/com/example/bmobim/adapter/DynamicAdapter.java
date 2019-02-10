package com.example.bmobim.adapter;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobim.R;
import com.example.bmobim.bean.DynamicBean;
import com.example.bmobim.bean.DynamicComment;
import com.juziwl.uilibrary.activity.WatchImagesActivity;
import com.juziwl.uilibrary.emoji.MTextView;
import com.juziwl.uilibrary.ninegridview.NewNineGridlayout;
import com.juziwl.uilibrary.ninegridview.NineGridlayout;
import com.juziwl.uilibrary.popupwindow.EasyPopup;
import com.juziwl.uilibrary.popupwindow.EditPopup;
import com.juziwl.uilibrary.popupwindow.XGravity;
import com.juziwl.uilibrary.popupwindow.YGravity;
import com.juziwl.uilibrary.tools.UiUtils;
import com.luck.picture.lib.PictureVideoPlayActivity;
import com.wxq.commonlibrary.bmob.CommonBmobUser;
import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.util.ConvertUtils;
import com.wxq.commonlibrary.util.ScreenUtils;
import com.wxq.commonlibrary.util.StringUtils;
import com.wxq.commonlibrary.util.TimeUtils;
import com.wxq.commonlibrary.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class DynamicAdapter extends BaseQuickAdapter<DynamicBean, BaseViewHolder> {

     Activity activity;
    public DynamicAdapter(List<DynamicBean> dynamicBeanList,   Activity activity) {
        super(R.layout.item_dynamic, dynamicBeanList);
        this.activity=activity;

    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicBean item) {
        ImageView image = helper.getView(R.id.iv_img);
        LoadingImgUtil.loadimg(item.publishUser.avatar, image, true);
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
    }


    private void showCirclePop(View view, DynamicBean item) {
        EasyPopup mCirclePop = EasyPopup.create()
                .setContentView(this.mContext, R.layout.layout_circle_comment)
                .setAnimationStyle(R.style.RightPopAnim)
                .setFocusAndOutsideEnable(true)
                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, final EasyPopup popup) {
                        view.findViewById(R.id.tv_zan).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShort("赞");
                                showEditPop(v,item);
                                popup.dismiss();
                            }
                        });
                        view.findViewById(R.id.tv_comment).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 弹出评论popupwindow


                                ToastUtils.showShort("评论");
                                DynamicComment dynamicComment = new DynamicComment();
                                dynamicComment.author = BmobUser.getCurrentUser(CommonBmobUser.class);
//                                dynamicComment.otherPeople = null;
                                dynamicComment.content = "我是啦啦77打断点7775";
//                                dynamicComment.card = item;
                                dynamicComment.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        ToastUtils.showShort("评论保存成功");
                                        dynamicComment.setObjectId(s);
                                        item.dynamicCommentList.add(0,dynamicComment);
                                        item.update(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
                                                    ToastUtils.showShort("更新动态item成功");
                                                }
                                            }
                                        });

//                                        BmobRelation commentRelation = new BmobRelation();
//                                        commentRelation.add(dynamicComment);
//                                        item.allComments = commentRelation;
//                                        item.update(new UpdateListener() {
//                                            @Override
//                                            public void done(BmobException e) {
//                                                if (e == null) {
//                                                    ToastUtils.showShort("更新动态item成功");
//                                                }
//                                            }
//                                        });

                                    }
                                });


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





    private void showEditPop(View view, DynamicBean item) {
        EditPopup editPop = EditPopup.create(mContext)
                .setContentView(this.mContext, R.layout.layout_circle_comment)
                .setAnimationStyle(R.style.RightPopAnim)
                .setFocusAndOutsideEnable(true)
                .apply();

        editPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }

        });
        editPop.showAtLocation(UiUtils.getRootView(activity),Gravity.BOTTOM, 0, 0);
    }








}