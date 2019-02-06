package com.example.bmobim.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobim.R;
import com.example.bmobim.activity.PublishDynamicActivity;
import com.example.bmobim.bean.DynamicBean;
import com.juziwl.uilibrary.activity.WatchImagesActivity;
import com.juziwl.uilibrary.emoji.MTextView;
import com.juziwl.uilibrary.ninegridview.NewNineGridlayout;
import com.juziwl.uilibrary.ninegridview.NineGridlayout;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.luck.picture.lib.PictureVideoPlayActivity;
import com.luck.picture.lib.utils.DisplayUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.bmob.SimpleFindListener;
import com.wxq.commonlibrary.constant.GlobalContent;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.util.ConvertUtils;
import com.wxq.commonlibrary.util.ScreenUtils;
import com.wxq.commonlibrary.util.StringUtils;
import com.wxq.commonlibrary.util.TimeUtils;
import com.wxq.commonlibrary.util.VideoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;

/**
 * 创建日期：2019 2 2
 * 描述:动态fragment页面
 *
 * @author:wxq
 */
public class DynamicFragment extends BaseFragment {
    public static final String PARMER = "parmer";
    public String currentType = "";
    @BindView(R.id.tv_publish)
    TextView tvPublish;
    @BindView(R.id.recyclerView)
    PullRefreshRecycleView recyclerView;
    Unbinder unbinder;
    int currentPage=0;
    public List<DynamicBean> dynamicBeanList=new ArrayList<>();

    public static DynamicFragment getInstance(String parmer) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("PARMER", parmer);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initViews() {
        recyclerView.setAdapter(new BaseQuickAdapter<DynamicBean, BaseViewHolder>(R.layout.item_dynamic, dynamicBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, DynamicBean item) {
                ImageView image=helper.getView(R.id.iv_img);
                LoadingImgUtil.loadimg(item.publishUser.avatar,image,true);
                helper.setText(R.id.tv_name,item.publishUser.getUsername());
                helper.setText(R.id.tv_time,TimeUtils.getFriendlyTimeSpanByNow(item.getCreatedAt()));
                MTextView content=helper.getView(R.id.expandable_text);
                ImageView iv_video_pic=helper.getView(R.id.iv_video_pic);

                content.setMText(item.content);
                if (StringUtils.isEmpty(item.pics)) {
                    helper.setGone(R.id.ngl_pics,false);
                }else {
                   helper.setGone(R.id.ngl_pics,true);
                   NewNineGridlayout piclayout= helper.getView(R.id.ngl_pics);
                    piclayout.showPic(ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(100), item.pics, new NineGridlayout.onNineGirdItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            WatchImagesActivity.navToWatchImages(getActivity(),item.pics,position);
                        }
                    });
                }
                if (StringUtils.isEmpty(item.video)||StringUtils.isEmpty(item.videoImage)) {
                    helper.setGone(R.id.rl_video,false);
                }else {
                    helper.setGone(R.id.rl_video,true);
                    LoadingImgUtil.loadimg(item.videoImage,iv_video_pic,false);
                    iv_video_pic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PictureVideoPlayActivity.navToVideoPlay(helper.getConvertView().getContext(), item.video, item.videoImage, true, GlobalContent.VIDEOPATH);
                        }
                    });
                }
            }
        }, new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getDynamicList(2);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getDynamicList(1);
            }
        });
        getDynamicList(0);
    }

    private void getDynamicList(int type) {
        if (type==0||type==1) {
            currentPage=0;
        }else {
            currentPage++;
        }
        BmobQuery<DynamicBean> query = new BmobQuery<>();
        query.include("publishUser").setLimit(10).setSkip(10*currentPage).order("-createdAt")
                .findObjects(new SimpleFindListener<DynamicBean>(getActivity()) {
                    @Override
                    public void success(List<DynamicBean> list) {
                        if (type==0||type==1) {
                            dynamicBeanList.clear();
                        }
                        dynamicBeanList.addAll(list);
                        recyclerView.notifyDataSetChanged();
                    }

                    @Override
                    protected void error(BmobException e) {
                        super.error(e);
                        recyclerView.notifyDataSetChanged();
                    }
                });
    }


    @OnClick({R.id.tv_publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_publish:
                PublishDynamicActivity.navToActivity(getActivity());
                break;
            case R.id.recyclerView:
                break;
        }
    }
}
