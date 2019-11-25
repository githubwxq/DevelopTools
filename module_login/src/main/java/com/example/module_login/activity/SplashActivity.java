//package com.example.module_login.activity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.widget.ImageView;
//
//import com.example.module_login.R;
//import com.example.module_login.R2;
//import com.example.module_login.bean.Card;
//import com.example.module_login.bean.Comment;
//import com.example.module_login.bean.Material;
//import com.example.module_login.bean.User;
//import com.example.module_login.contract.SplashContract;
//import com.example.module_login.presenter.SplashActivityPresent;
//import com.luck.picture.lib.PictureSelectAdapter;
//import com.luck.picture.lib.PictureSelectorView;
//import com.luck.picture.lib.config.PictureConfig;
//import com.luck.picture.lib.entity.LocalMedia;
//import com.luck.picture.lib.utils.DisplayUtils;
//import com.luck.picture.lib.utils.ImageSeclctUtils;
//import com.orhanobut.logger.Logger;
//import com.wxq.commonlibrary.base.BaseActivity;
//import com.wxq.commonlibrary.constant.GlobalContent;
//import com.wxq.commonlibrary.util.BarUtils;
//import com.wxq.commonlibrary.util.DensityUtil;
//import com.wxq.commonlibrary.util.FileUtils;
//import com.wxq.commonlibrary.util.ToastUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import cn.bmob.v3.BmobQuery;
//import cn.bmob.v3.BmobUser;
//import cn.bmob.v3.datatype.BmobFile;
//import cn.bmob.v3.datatype.BmobPointer;
//import cn.bmob.v3.datatype.BmobRelation;
//import cn.bmob.v3.exception.BmobException;
//import cn.bmob.v3.listener.FindListener;
//import cn.bmob.v3.listener.LogInListener;
//import cn.bmob.v3.listener.SaveListener;
//import cn.bmob.v3.listener.UpdateListener;
//import cn.bmob.v3.listener.UploadFileListener;
//
//public class SplashActivity extends BaseActivity<SplashContract.Presenter> implements SplashContract.View {
//
//    @BindView(R2.id.iv_bg)
//    ImageView ivBg;
//    @BindView(R2.id.pic_select_view)
//    PictureSelectorView picSelectView;
//    private static final int COLUME = 3;
//    private static final int MAXSELECTNUM = 30;
//    int i=0;
//    @Override
//    protected void initViews() {
//        //设置全屏
//        BarUtils.setStatusBarVisibility(this, false);
//        //点击背景如果当前有广告
//        click(ivBg, o -> {
////            if (i==0) {
////                mPresenter.getCode();
////                i++;
////            }else {
////                mPresenter.register();
////            }
//
//            mPresenter.getHomeData();
//
////            GaoDeChooseMapAddressActivity.navToActivity(this);
////            LoginActivity.navToActivity(this);
////            finish();
////            if (selectList.size()>0) {
////                uploadHeardPic();
////            }else{
//////                uploadCard();
//////                upLoadComment();
////
//////                queryMaterialList();
////
//////                ARouter.getInstance().build(RouterContent.BMOB_PUBLISHCARD).navigation();
////                GaoDeChooseMapAddressActivity.navToActivity(this);
////                finish();
////            }
//
//
//        });
//        picSelectView.setOutputCameraPath(GlobalContent.imgPath);
//        picSelectView.setOutputVideoPath(GlobalContent.VIDEOPATH);
//        picSelectView.setLoginType(2);
//        picSelectView.setVideoDuration(30_000L);
//        picSelectView.initData(this, COLUME, MAXSELECTNUM, DisplayUtils.getScreenWidth(this) - DensityUtil.dip2px(this, 30), new PictureSelectAdapter.OnItemShowPicClickListener() {
//            @Override
//            public void onClick() {
//                // 选择图片和视频
//                ImageSeclctUtils.openAlbum(SplashActivity.this, selectList);
//            }
//        });
//    }
//
//    private void queryMaterialList() {
//        BmobQuery<Material> query=new BmobQuery<>();
//        query.setLimit(10).setSkip(1).findObjects(new FindListener<Material>() {
//            @Override
//            public void done(List<Material> list, BmobException e) {
//                for (int i = 0; i < list.size(); i++) {
//
//                }
//            }
//        });
//
//
//
//    }
//
//
//    /**
//     * 上传评论信息
//     */
//    private void upLoadComment() {
//        Comment conmment=new Comment();
//        conmment.content="111111";
//        User user=new User();
//        user.setObjectId("2a5fa02a19");
//        conmment.author=user;
//        Card  card=new Card();
//        card.setObjectId("5c80efbe40");
//        conmment.card=card;
//        conmment.save(new SaveListener<String>() {
//            @Override
//            public void done(String id, BmobException e) {
//                Comment comment=new Comment();
//                comment.setObjectId(id);
////                查询出某个帖子（objectId为ESIt3334）的所有评论,同时将该评论的作者的信息也查询出来
//                BmobQuery<Comment> bmobQuery=new BmobQuery<>();
//                Card  card=new Card();
//                card.setObjectId("5c80efbe40");
//                User user=new User();
//                user.setObjectId("2a5fa02a19");
////                bmobQuery.addWhereEqualTo("card",new BmobPointer(card));
//                bmobQuery.addWhereEqualTo("author",user);
//                bmobQuery.include("author");
//                bmobQuery.findObjects(new FindListener<Comment>() {
//                    @Override
//                    public void done(List<Comment> list, BmobException e) {
//                        if (e==null) {
//                            for (Comment comment1 : list) {
//                                Logger.e(comment1.content+comment1.author.getUsername());
//                            }
//
//                        }
//                    }
//                });
//
//
//            }
//        });
//    }
//
//
//    /**
//     * 上传帖子信息
//     */
//    private void uploadCard() {
//        BmobUser.loginByAccount("wxq123", "123", new LogInListener<User>() {
//            @Override
//            public void done(User user, BmobException e) {
//                if (e == null) {
//                    //  更新帖子
//                    Card card=new Card();
////                    card.setObjectId("5c80efbe40");
//                    card.name="我被修改过了3333";
//                    card.content="我是内容3333";
//                    User likeone = new User();
//                    likeone.setObjectId("0dc0356307");
//                    card.user=likeone;
//
//
//                    User liketwo = new User();
//                    liketwo.setObjectId("cafab4a972");
//
//                    BmobRelation bmobRelation=new BmobRelation();
//                    bmobRelation.add(BmobUser.getCurrentUser(User.class));
//                    bmobRelation.add(liketwo);
//
//
//
////                    bmobRelation.add(liketwo);
//                    card.likes=bmobRelation;
//
//                    card.save(new SaveListener<String>() {
//                        @Override
//                        public void done(String id, BmobException e) {
//                            //更新陈宫
//
//                            //获取喜欢该帖子的说有用户
//                            // 查询喜欢这个帖子的所有用户，因此查询的是用户表
//                            BmobQuery<User> userBmobQuery = new BmobQuery<User>();
//                            Card card1=new Card();
//                            card1.setObjectId(id);
//                            userBmobQuery.addWhereRelatedTo("likes",new BmobPointer(card1));
//                            userBmobQuery.findObjects(new FindListener<User>() {
//                                @Override
//                                public void done(List<User> list, BmobException e) {
//                                    ToastUtils.showShort(list.size());
//                                }
//                            });
//
//                        }
//                    });
//
//
//                } else {
//
//                }
//            }
//        });
//
//    }
//
//    private void uploadHeardPic() {
//        //此处替换为你的用户名密码
//        User user=new User();
////                user.upda
//
//        BmobUser.loginByAccount("wxq123", "123", new LogInListener<User>() {
//            @Override
//            public void done(User user, BmobException e) {
//                if (e == null) {
//
//                    BmobFile bmobFile=new BmobFile(FileUtils.getFileByPath(selectList.get(0).getPath()));
//                    bmobFile.upload(new UploadFileListener() {
//                        @Override
//                        public void done(BmobException e) {
//                            if (e==null) {
//                                User user=BmobUser.getCurrentUser(User.class);
//                                user.avatar=bmobFile;
//                                user.update("0dc0356307",new UpdateListener() {
//                                    @Override
//                                    public void done(BmobException e) {
//                                        if (e==null) {
//                                            //shan
//                                            ToastUtils.showShort("上传头像成功");
//                                        }
//                                    }
//                                });
//                            }
//                        }
//                    });
//
//
//                } else {
//
//                }
//            }
//        });
//    }
//
//    public List<LocalMedia> selectList = new ArrayList<>();
//    private List<LocalMedia> needList = new ArrayList<>();
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        picSelectView.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            switch (requestCode) {
//                case PictureConfig.CHOOSE_REQUEST:
//                    // 图片选择结果回调
//                    needList.clear();
//                    selectList = picSelectView.getSelectList();
//                    ToastUtils.showShort("选择了"+selectList.size()+"张图片");
//                default:
//                    break;
//            }
//        }
//    }
//
//    @Override
//    protected int attachLayoutRes() {
//        return R.layout.activity_splash;
//    }
//
//    @Override
//    protected SplashContract.Presenter initPresent() {
//        return new SplashActivityPresent(this);
//    }
//
//    @Override
//    public boolean isNeedHeardLayout() {
//        return false;
//    }
//
//}
