package com.juziwl.uilibrary.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.dialog.DialogViewHolder;
import com.juziwl.uilibrary.dialog.XXDialog;
import com.juziwl.uilibrary.viewpage.adapter.BaseViewPagerAdapter;
import com.luck.picture.lib.widget.LoadingCircleView;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.glide.ImageSize;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.glide.OnLoadProgressListener;
import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.commonlibrary.util.FileUtils;
import com.wxq.commonlibrary.util.StringUtils;
import com.wxq.commonlibrary.util.ThreadExecutorManager;
import com.wxq.commonlibrary.util.TimeUtils;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.commonlibrary.util.UIHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * @author Army
 * @version V_5.0.0
 * @date 2016年02月24日
 * @description 浏览图片
 */
public class WatchImagesActivity extends BaseActivity {

    public static final String FIRST_POSITION = "firstPosition";
    public static final String IMAGES = "images";
    public static final String IS_SHOW_HEAD_ICON = "isShowHeadIcon";
    public static final String CAN_IMAGE_SAVE = "canImageSave";
    public static final String ACTION_FINISH_SELF = "com.juziwl.modellibrary.action_finish_self";
    public static final String ACTION_LOCAL_URL = "com.juziwl.modellibrary.ACTION_Local_url";
    public static final String ACTION_TITLE_ATTENDACNE = "com.juziwl.modellibrary.action_title_attendacne";
    public static final String ACTION_BACK = "action_back";

    ViewPager hackyPager;

    ImageView back;

    TextView tvTitle;

    RelativeLayout top;

    TextView pageNumber;

    LinearLayout llNumber;

    TextView watchOriginalImage;

    private String[] piclist;

    List<String> picList = new ArrayList<>();


    private boolean canImageSave = true;
    private boolean isShowHeadIcon = false;

    public static final String IMAGELONGCLICK = "WatchImagesActivity.imagelongclick",
            ACTIVITYFINISH = "WatchImagesActivity.activityfinish";
    /**
     * 当前展示的图片的链接，聊天要用到
     */
    public static String currentImageUrl = "";


    public static void navToWatchImages(Context context, String imgs, int firstPosition) {
        navToWatchImages(context, imgs, firstPosition, false, "", true);
    }




    public static void navToWatchImages(Context context, String imgs, int firstPosition, boolean isShowHeadIcon) {
        navToWatchImages(context, imgs, firstPosition, isShowHeadIcon, "", true);
    }


    /**
     * 跳转到查看大图界面
     *
     * @param imgs           多个图片路径以分号拼在一起的字符串
     * @param firstPosition  一开始显示哪张图片
     * @param isShowHeadIcon 默认图是否是默认头像
     * @param canImageSave   图片是否能保存（通常直接显示默认图的不能保存）
     */
    public static void navToWatchImages(Context context, String imgs, int firstPosition,
                                        boolean isShowHeadIcon, String titles, boolean canImageSave) {
        Bundle bundle = new Bundle();
        bundle.putString(IMAGES, imgs);
        bundle.putInt(FIRST_POSITION, firstPosition);
        bundle.putBoolean(IS_SHOW_HEAD_ICON, isShowHeadIcon);
        bundle.putBoolean(CAN_IMAGE_SAVE, canImageSave);
        bundle.putString(ACTION_TITLE_ATTENDACNE, titles);
        Intent intent = new Intent(context, WatchImagesActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (!(context instanceof Activity)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }


    private XXDialog xxDialog = null;

    private void showDialog() {
        if (xxDialog == null) {
            xxDialog = new XXDialog(WatchImagesActivity.this, R.layout.popmenulongbtn) {
                @Override
                public void convert(DialogViewHolder holder) {
                    holder.getView(R.id.btn_keep).setOnClickListener(view -> {
                        save();
                    });
                    holder.getView(R.id.btn_shutdown).setOnClickListener(view -> {
                        finishBrowse();
                    });
                    holder.getView(R.id.btn_cancel).setOnClickListener(view -> {
                        cancelDialog();
                    });
                }
            };
        }
        xxDialog.fromBottom().fullWidth().setCanceledOnTouchOutside(true).setCancelAble(true);
        xxDialog.showDialog();
    }

    void finishBrowse() {
        if (xxDialog != null) {
            xxDialog.cancelDialog();
        }
        onBackPressed();
    }

    void save() {
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        savePic();
                    } else {
                        if (xxDialog != null) {
                            xxDialog.cancelDialog();
                        }
                        ToastUtils.showShort(R.string.open_external_storage);
                    }
                });
    }

    void cancelDialog() {
        if (xxDialog != null) {
            xxDialog.cancelDialog();
        }
    }

    private void savePic() {
        LoadingImgUtil.getImageFile(currentImageUrl, new LoadingImgUtil.OnFileImageLoadingListener() {
            @Override
            public void onFileLoadingComplete(File file) {
                ThreadExecutorManager.getInstance().runInThreadPool(() -> {
                    try {
                        File image = new File(GlobalContent.SAVEPICTURE + TimeUtils.getNowString() + ".png");
                        FileUtils.copyFile(file, image);
                        String absolutePath = image.getAbsolutePath();
                        MediaScannerConnection.scanFile(WatchImagesActivity.this,
                                new String[]{absolutePath}, new String[]{"image/png"}, (path, uri) -> {
                                    int start = path.indexOf("/savepictures/");
                                    int end = path.lastIndexOf("/");
                                    ToastUtils.showShort("图片已保存至" + path.substring(start + 1, end) + "/文件夹");
                                });
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtils.showShort("保存失败");
                    }
                });
                if (xxDialog != null) {
                    xxDialog.cancelDialog();
                }
            }

            @Override
            public void onLoadingFailed() {
                ToastUtils.showShort("保存失败");
                if (xxDialog != null) {
                    xxDialog.cancelDialog();
                }
            }

            @Override
            public void onLoadingComplete(Bitmap bitmap) {
            }
        });
    }

    @Override
    protected void initViews() {
        BarUtils.setStatusBarVisibility(this, false);
        BarUtils.setNavBarVisibility(this, false);
        hackyPager = findViewById(R.id.hacky_pager);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());
        pageNumber = findViewById(R.id.page_number);
        int position = getIntent().getIntExtra(FIRST_POSITION, 0);
        String pics = getIntent().getStringExtra(IMAGES);
        isShowHeadIcon = getIntent().getBooleanExtra(IS_SHOW_HEAD_ICON, false);
        canImageSave = getIntent().getBooleanExtra(CAN_IMAGE_SAVE, true);
        piclist = pics.split(";");
        picList.addAll(Arrays.asList(piclist));
        currentImageUrl = picList.get(position);
        pageNumber.setText(String.format(Locale.CHINA, "%d/%d", position + 1, picList.size()));
        hackyPager.setAdapter(new ImagePagerAdapter());
        hackyPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                pageNumber.setText(String.format(Locale.CHINA, "%d/%d", position + 1, picList.size()));
                currentImageUrl = picList.get(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        hackyPager.setCurrentItem(position);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.common_layout_browse_photo;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    private class ImagePagerAdapter extends BaseViewPagerAdapter<String> {

        final ImageSize localImageSize = new ImageSize(480, 800);
        final ImageSize networkImageSize = new ImageSize(720, 1280);

        public ImagePagerAdapter() {
            super(WatchImagesActivity.this.picList);
        }

        @Override
        public View newView(int position) {
            View viewGroup = LayoutInflater.from(WatchImagesActivity.this).inflate(
                    R.layout.layout_pager_watch_image_item, null, false);
            PhotoView photoView = viewGroup.findViewById(R.id.photoview);
            LoadingCircleView loading = viewGroup.findViewById(R.id.loading);
            if (StringUtils.isEmpty(picList.get(position))) {
                photoView.setImageResource(isShowHeadIcon ? R.mipmap.common_default_head : R.mipmap.common_falseimg);
            } else {
                if (picList.get(position).startsWith("http")) {
                    LoadingImgUtil.displayImageWithoutPlaceholder(picList.get(position),
                            photoView, networkImageSize, isShowHeadIcon, new OnLoadProgressListener() {
                                @Override
                                public void onLoadStart() {
                                    loading.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onProgress(long total, int progress) {
                                    UIHandler.getInstance().post(() -> loading.setProgerss(progress, true));
                                }

                                @Override
                                public void onLoadFinish() {
                                    loading.setVisibility(View.GONE);
                                }
                            });
                } else {
                    LoadingImgUtil.displayLocalImage(picList.get(position), photoView, localImageSize);
                }
            }
            photoView.setOnLongClickListener(view -> {
                if (canImageSave) {
                    showDialog();
                }
                return true;
            });
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    finish();
                }
            });
//            photoView.setOnClickListener(view -> finish());

            return viewGroup;
        }
    }

    @Override
    public boolean isNeedHeardLayout() {
        return false;
    }

}
