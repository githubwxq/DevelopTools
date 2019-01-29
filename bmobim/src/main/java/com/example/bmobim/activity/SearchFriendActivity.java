package com.example.bmobim.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bmobim.R;
import com.example.bmobim.contract.SearchFriendContract;
import com.example.bmobim.model.BmobUserModel;
import com.example.bmobim.presenter.SearchFriendActivityPresenter;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.bmob.CommonBmobUser;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 创建日期：
 * 描述:
 *
 * @author:wxq
 */
public class SearchFriendActivity extends BaseActivity{

    public static final String TITLE = "搜索用户";
    @BindView(R.id.et_find_name)
    EditText etFindName;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.recyclerView)
    PullRefreshRecycleView recyclerView;

    public List<CommonBmobUser> users=new ArrayList<>();

    int currentPage=0;

    public static void navToActivity(Context context) {
        Intent intent = new Intent(context, SearchFriendActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_search_friend;
    }



    @Override
    protected void initViews() {
        topHeard.setRightText("").setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        }).setTitle(TITLE);

        recyclerView.setAdapter(new BaseQuickAdapter<CommonBmobUser, BaseViewHolder>(R.layout.item_search_user, users) {
            @Override
            protected void convert(BaseViewHolder helper, CommonBmobUser item) {
                ImageView avatar = helper.getView(R.id.avatar);
                Button btn_add = helper.getView(R.id.btn_add);
                TextView name = helper.getView(R.id.name);
                LoadingImgUtil.loadimg(item.avatar, avatar, true);
                name.setText(item.getUsername());
                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context,UserInfoActivity.class);
                        intent.putExtra("user",item);
                        startActivity(intent);

                    }
                });
            }
        }, new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                queryUsers(2);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                queryUsers(1);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryUsers(0);
            }
        });
    }

    /**
     *
     * @param type 0 初始化 1刷新 2價值更多
     */
    public void queryUsers(int type) {
        if (type==0||type==1) {
          currentPage=0;
        }else {
            currentPage++;
        }
        String name = etFindName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("请填写用户名");
            return;
        }
        BmobUserModel.getInstance().queryUsers(name, currentPage,
                new FindListener<CommonBmobUser>() {
                    @Override
                    public void done(List<CommonBmobUser> list, BmobException e) {
                        if (e == null) {
                            if (type==0||type==1) {
                                users.clear();
                            }
                            users.addAll(list);
                        } else {
                            ToastUtils.showShort(e.getMessage());
                        }
                        recyclerView.notifyDataSetChanged();
                    }
                }
        );
    }


    @Override
    public boolean isNeedHeardLayout() {
        return true;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

}
