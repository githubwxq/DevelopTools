package com.example.uitestdemo;

import android.view.View;
import android.widget.Button;

import com.juziwl.uilibrary.ninegridview.NineGridlayoutForHaFu;
import com.luck.picture.lib.utils.DisplayUtils;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.util.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;


/**
 * 九宫格布局测试
 */
public class NineGrideFragment extends BaseFragment {

    @BindView(R.id.ninegride)
    NineGridlayoutForHaFu ninegride;
    Unbinder unbinder;
    @BindView(R.id.add)
    Button add;
    @BindView(R.id.delete)
    Button delete;


    public static NineGrideFragment newInstance() {
        NineGrideFragment fragment = new NineGrideFragment();
        return fragment;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_nine_gride;
    }

    List<String> list;

    @Override
    protected void initViews() {
        list = new ArrayList<>();
        list.add("http://test.image.lwbudoo.cn/product_3.jpeg?e=1565758996&token=W9ueoRJIADP_Rn10kjzLNaNCZP7pU5ygG7DmT_B6:YkozeM2S0Xo01EvkJF5WBRctNP8=");
        list.add("http://test.image.lwbudoo.cn/product_3.jpeg?e=1565758996&token=W9ueoRJIADP_Rn10kjzLNaNCZP7pU5ygG7DmT_B6:YkozeM2S0Xo01EvkJF5WBRctNP8=");
        list.add("http://test.image.lwbudoo.cn/product_3.jpeg?e=1565758996&token=W9ueoRJIADP_Rn10kjzLNaNCZP7pU5ygG7DmT_B6:YkozeM2S0Xo01EvkJF5WBRctNP8=");
        list.add("http://test.image.lwbudoo.cn/product_3.jpeg?e=1565758996&token=W9ueoRJIADP_Rn10kjzLNaNCZP7pU5ygG7DmT_B6:YkozeM2S0Xo01EvkJF5WBRctNP8=");
        list.add("http://test.image.lwbudoo.cn/product_3.jpeg?e=1565758996&token=W9ueoRJIADP_Rn10kjzLNaNCZP7pU5ygG7DmT_B6:YkozeM2S0Xo01EvkJF5WBRctNP8=");
        ninegride.setTotalWidth(DisplayUtils.getScreenWidth(getActivity()));
        ninegride.setImagesData(list, DisplayUtils.getScreenWidth(getActivity()), ConvertUtils.dp2px(120));


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add("http://test.image.lwbudoo.cn/product_3.jpeg?e=1565758996&token=W9ueoRJIADP_Rn10kjzLNaNCZP7pU5ygG7DmT_B6:YkozeM2S0Xo01EvkJF5WBRctNP8=");
                ninegride.setImagesData(list, DisplayUtils.getScreenWidth(getActivity()), ConvertUtils.dp2px(120));
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(0);
                ninegride.setImagesData(list, DisplayUtils.getScreenWidth(getActivity()), ConvertUtils.dp2px(120));
            }
        });
    }

}
