package com.juziwl.uilibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.edittext.SearchBarView;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.model.BaiduResule;
import com.wxq.commonlibrary.util.StringUtils;
import com.wxq.commonlibrary.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 简单页面不采用mvp了

 */
public class BaiduSearchListActivity extends BaseActivity {


    SearchBarView search_bar;

    PullRefreshRecycleView rv_recycleVidew;

    String cityName;

    public static void navToActivity(Context context, String cityName) {
        Intent intent = new Intent(context, BaiduSearchListActivity.class);
        intent.putExtra("cityName", cityName);

        context.startActivity(intent);
    }


    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_baidu_search_list;
    }

    /**
     * 给关键字设置颜色 遍历所有关键字的位子
     * @param content
     * @param view
     * @param mKeyWord
     */
    private void setKetWordTextColor (String content, TextView view,String mKeyWord) {
        int index = -1;
        SpannableString spannableString = new SpannableString(content);
        do {
            if (index == -1) {
                index = content.indexOf(mKeyWord, 0);
            } else {
                index = content.indexOf(mKeyWord, index + mKeyWord.length());
            }
            if (index != -1) {
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF4081")), index, index + mKeyWord.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } while (index != -1);
        view.setText(spannableString);
    }

    @Override
    protected void initViews() {
        cityName = getIntent().getStringExtra("cityName");
        search_bar = findViewById(R.id.search_bar);
        rv_recycleVidew = findViewById(R.id.rv_recycleVidew);
        rv_recycleVidew.setAdapter(new BaseQuickAdapter<PoiInfo,BaseViewHolder>(R.layout.item_choosepoi) {
            @Override
            protected void convert(BaseViewHolder helper, PoiInfo item) {
//                helper.setText(R.id.poi_info, item.name);
                helper.setText(R.id.poi_location, item.address);
                //包含的关键字需要变颜色
                setKetWordTextColor( item.name,helper.getView(R.id.poi_info),keyWord);
            }
        });

        search_bar.setOnSearchBackListener(new SearchBarView.OnSearchBackListener() {
            @Override
            public void onBack() {
                onBackPressed();
            }
        });

        search_bar.setOnSearchListener(new SearchBarView.OnSearchListener() {
            @Override
            public void onSearch(String text) {
                ToastUtils.showShort("搜索" + text);
                doSearch(text);
            }

            @Override
            public void onClean() {
                rv_recycleVidew.clear();
            }

            @Override
            public void onTextChange(String text) {
                if (StringUtils.isEmpty(cityName)) {
                    Toast.makeText(context,"未获取到当前定位的城市",Toast.LENGTH_LONG).show();
                    return;
                }
                if (!StringUtils.isEmpty(text)) {
                    keyWord = text;
                    doSearch(text);
                }
            }
        });
        initPoiSearch();
    }

    private void initPoiSearch() {
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                    Toast.makeText(context, "未找到结果", Toast.LENGTH_LONG).show();
                    return;
                }
                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    List<PoiInfo> poiInfoList = poiResult.getAllPoi();
                    rv_recycleVidew.updataData(poiInfoList);
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });
    }

    private PoiSearch mPoiSearch;
    private String keyWord;
    private void doSearch(String text) {

        mPoiSearch.searchInCity(new PoiCitySearchOption()
                .city(cityName)
                .keyword(text)
                .pageCapacity(20)   //每页查询个数
                .cityLimit(false)
                // 产品要求只要显示第一页数据，第一页默认是从0开始
                .pageNum(0));
    }


    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @Override
    public boolean isNeedHeardLayout() {
        return false;
    }
}
