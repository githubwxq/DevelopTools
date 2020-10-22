package com.example.uitestdemo.fragment.components.flowlayout;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.uitestdemo.R;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.juziwl.uilibrary.flowlayout.FlowLayout;
import com.juziwl.uilibrary.flowlayout.FlowLayoutManager;
import com.juziwl.uilibrary.flowlayout.SuperFlexboxLayout;
import com.juziwl.uilibrary.flowlayout.SuperFlexboxLayoutAdapter;
import com.juziwl.uilibrary.flowlayout.TagAdapter;
import com.juziwl.uilibrary.flowlayout.TagFlowLayout;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class FlowLayoutFragment extends BaseFragment {

    List<TagModel> tagModels=new ArrayList<>();
    @BindView(R.id.rv_recycle)
    RecyclerView rvRecycle;
    @BindView(R.id.tag_flow)
    TagFlowLayout tagFlow;
    @BindView(R.id.sfl)
    SuperFlexboxLayout sfl;
    @BindView(R.id.flexBoxLayout)
    FlexboxLayout flexBoxLayout;

    public static FlowLayoutFragment newInstance() {
        FlowLayoutFragment fragment = new FlowLayoutFragment();
        return fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_flow_layout;
    }

    @Override
    protected void initViews() {
        initData();
        initRecycleviewFlow();
        initTagFlow();
        initSuperFlexBoxLayout();
    }

    private void initRecycleviewFlow() {
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getActivity());
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);//主轴为水平方向，起点在左端。
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
//        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//按正常方向换行
//        //justifyContent 属性定义了项目在主轴上的对齐方式。
//        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。

//        rvRecycle.setLayoutManager(flexboxLayoutManager);
        //三方FLowLayout
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        rvRecycle.setLayoutManager(flowLayoutManager);
//        rvRecycle.setLayoutManager(new LinearLayoutManager(this));
        FlowLayoutDivider space = new FlowLayoutDivider(Color.parseColor("#f7f7f7"),
                DensityUtil.dip2px(getActivity(), 10), 0);
        rvRecycle.addItemDecoration(space);

        rvRecycle.setAdapter(new BaseQuickAdapter<TagModel, BaseViewHolder>(R.layout.recycle_item,tagModels) {

            @Override
            protected void convert(@NonNull BaseViewHolder helper, TagModel item) {
                helper.setText(R.id.text,item.name);
                TextView view = helper.getView(R.id.text);
                if (item.isSelect) {
                    view.setBackgroundColor(getResources().getColor(R.color.red_200));
                }else {
                    view.setBackgroundColor(getResources().getColor(R.color.yellow_200));
                }
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.isSelect=!item.isSelect;
                        notifyDataSetChanged();
                    }
                });
            }
        });



    }

    private void initSuperFlexBoxLayout() {
        sfl.setMargin(0,0,10,10).setAdapter(new SuperFlexboxLayoutAdapter<TagModel>(R.layout.recycle_item,tagModels) {
            @Override
            public void bindView(View tagView, int i, TagModel item) {
                TextView  view=tagView.findViewById(R.id.text);
                view.setText(item.name);
                if (item.isSelect) {
                    view.setBackgroundColor(getResources().getColor(R.color.red_200));
                }else {
                    view.setBackgroundColor(getResources().getColor(R.color.yellow_200));
                }
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.isSelect=!item.isSelect;
                        notifyDataChanged();
                    }
                });
            }
        });
    }

    private void initTagFlow() {


        tagFlow.setAdapter(new TagAdapter<TagModel>(tagModels) {
            @Override
            public View getView(FlowLayout parent, int position, TagModel tagModel) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item,null);
                TextView textView = view.findViewById(R.id.text);
                textView.setText(tagModels.get(position).name);
                if (tagModels.get(position).isSelect) {
                    textView.setBackgroundColor(getResources().getColor(R.color.red_200));
                }else {
                    textView.setBackgroundColor(getResources().getColor(R.color.yellow_200));
                }
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tagModels.get(position).isSelect=!  tagModels.get(position).isSelect;
                        notifyDataChanged();
                    }
                });
                return view;
            }
        });





    }

    private void initData() {
        TagModel tagModel1=new TagModel("wxq",false);
        TagModel  tagModel2=new TagModel("wxq111",true);
        TagModel  tagModel3=new TagModel("wxq222",false);
        TagModel  tagModel4=new TagModel("wxq3333",true);
        TagModel  tagModel5=new TagModel("wxq3333",true);
        TagModel  tagModel6=new TagModel("wxq3333",true);
        TagModel  tagModel7=new TagModel("wxq3333",true);
        TagModel  tagModel8=new TagModel("wxq3333",true);
        tagModels.add(tagModel1);
        tagModels.add(tagModel2);
        tagModels.add(tagModel3);
        tagModels.add(tagModel4);
        tagModels.add(tagModel5);
        tagModels.add(tagModel6);
        tagModels.add(tagModel7);
        tagModels.add(tagModel8);
    }
}