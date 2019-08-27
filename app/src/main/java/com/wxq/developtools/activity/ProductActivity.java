package com.wxq.developtools.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juziwl.uilibrary.activity.WatchImagesActivity;
import com.juziwl.uilibrary.ninegridview.NewNineGridlayout;
import com.juziwl.uilibrary.ninegridview.NineGridlayout;
import com.juziwl.uilibrary.recycler.PullRefreshRecycleView;
import com.luck.picture.lib.utils.DisplayUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxq.commonlibrary.base.BaseActivity;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.baserx.ResponseTransformer;
import com.wxq.commonlibrary.baserx.RxSubscriber;
import com.wxq.commonlibrary.baserx.RxTransformer;
import com.wxq.commonlibrary.glide.LoadingImgUtil;
import com.wxq.commonlibrary.http.common.Api;
import com.wxq.commonlibrary.map.navigation.MapNavigationUtils;
import com.wxq.commonlibrary.util.BarUtils;
import com.wxq.commonlibrary.util.ConvertUtils;
import com.wxq.commonlibrary.util.ToastUtils;
import com.wxq.developtools.R;
import com.wxq.developtools.api.KlookApi;
import com.wxq.developtools.model.CommentBean;
import com.wxq.developtools.model.ProductCommentData;
import com.wxq.developtools.model.ProductDetailBean;
import com.wxq.developtools.model.ProductPackageVosBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class ProductActivity extends BaseActivity implements AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener {
    List<CommentBean> commentBeanList = new ArrayList<>();
    int page = 1;
    int rows = 10;
    String id;

    PullRefreshRecycleView commen_list;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.iv_shop_car)
    ImageView ivShopCar;
    @BindView(R.id.tv_add_card)
    TextView tvAddCard;
    @BindView(R.id.tv_go_buy)
    TextView tvGoBuy;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    ImageView iv_cover_pic;
    TextView tv_title;
    TextView tv_price;
    TextView tv_describe;
    TextView tv_include;
    TextView tv_not_include;
    TextView tv_you_know;
    TextView tv_question;
    RecyclerView recycler_view;
    ProductDetailBean productDetailBean;
    @BindView(R.id.ll_connect_kefu)
    LinearLayout llConnectKefu;


    public static void navToActivity(Context context, String id) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    private void updateHeard(ProductDetailBean productDetailBean) {
        LoadingImgUtil.loadimg(productDetailBean.cover, iv_cover_pic, false);
        tv_title.setText(productDetailBean.name);
        tv_price.setText("¥" + productDetailBean.price);
        tv_describe.setText(productDetailBean.description);
        tv_include.setText(productDetailBean.priceInclude);
        tv_not_include.setText(productDetailBean.priceUninclude);
        tv_you_know.setText(productDetailBean.mustUnderstand);
        tv_question.setText(productDetailBean.problem);
        ivCollect.setSelected(productDetailBean.isCollection());
        List<ProductPackageVosBean> packageVos = productDetailBean.productPackageVos;
        if (packageVos.size() > 0) {
            packageVos.get(0).isSelect = true;
        }
        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler_view.setAdapter(new BaseQuickAdapter<ProductPackageVosBean, BaseViewHolder>(R.layout.product_taocan, packageVos) {
            @Override
            protected void convert(BaseViewHolder helper, ProductPackageVosBean item) {
                helper.setText(R.id.tv_taocan, item.name);
                helper.getView(R.id.tv_taocan).setSelected(item.isSelect);
                helper.getView(R.id.tv_taocan).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (ProductPackageVosBean packageVo : packageVos) {
                            packageVo.isSelect = false;
                        }
                        item.isSelect = true;
                        notifyDataSetChanged();
                    }
                });
            }
        });
//        addMarkersToMap(new LatLng(Double.valueOf(productDetailBean.lat), Double.valueOf(productDetailBean.lng)));
    }

    @Override
    protected void initViews() {
        BarUtils.setStatusBarAlpha(this, 0);
        BarUtils.addMarginTopEqualStatusBarHeight(rlTop);
        View heardView = LayoutInflater.from(this).inflate(R.layout.product_heard, null, false);
        iv_cover_pic = heardView.findViewById(R.id.iv_cover_pic);
        tv_title = heardView.findViewById(R.id.tv_title);
        tv_price = heardView.findViewById(R.id.tv_price);
        recycler_view = heardView.findViewById(R.id.recycler_view);
        tv_describe = heardView.findViewById(R.id.tv_describe);
        tv_include = heardView.findViewById(R.id.tv_include);
        tv_not_include = heardView.findViewById(R.id.tv_not_include);
        tv_you_know = heardView.findViewById(R.id.tv_you_know);
        tv_question = heardView.findViewById(R.id.tv_question);
        commen_list = findViewById(R.id.commen_list);
//        mapView = heardView.findViewById(R.id.map);

        commen_list.setAdapter(new BaseQuickAdapter<CommentBean, BaseViewHolder>(R.layout.product_comment, commentBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, CommentBean item) {
//                helper.setText(R.id.iv_user_pic.)
                LoadingImgUtil.loadimg(item.head, helper.getView(R.id.iv_user_pic), true);
                helper.setText(R.id.tv_comment_content, item.content);
                NewNineGridlayout nineGridlayout = helper.getView(R.id.nine_layout);
                if (item.pics != null) {
                    nineGridlayout.setVisibility(View.VISIBLE);
                    nineGridlayout.showPic(DisplayUtils.getScreenWidth(mContext) - ConvertUtils.dp2px(80), item.pics, new NineGridlayout.onNineGirdItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i = 0; i < item.pics.size(); i++) {
                                if (i == item.pics.size() - 1) {
                                    stringBuilder.append(item.pics.get(i));
                                } else {
                                    stringBuilder.append(item.pics.get(i) + ";");
                                }
                            }
                            WatchImagesActivity.navToWatchImages(mContext, stringBuilder.toString(), position);
                        }
                    });

                } else {
                    nineGridlayout.setVisibility(View.GONE);
                }


            }
        }, new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                rows++;
                getComment();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getComment();
            }
        }).addHeaderView(heardView, true);

        getData();
    }

    public void getComment() {
        Api.getInstance().getApiService(KlookApi.class)
                .pageProductComment(page, rows, id)
                .compose(ResponseTransformer.handleResult())
                .compose(RxTransformer.transformFlow(this))
                .subscribe(new RxSubscriber<ProductCommentData>() {
                    @Override
                    protected void onSuccess(ProductCommentData productCommentData) {
                        if (page == 1) {
                            commentBeanList.clear();
                        } else {
                            if (productCommentData.list.size() < rows) {
                                commen_list.setLoadMoreEnable(false);
                            } else {
                                commen_list.setLoadMoreEnable(true);
                            }
                        }
                        commentBeanList.addAll(productCommentData.list);
                        commen_list.notifyDataSetChanged();
                    }
                });

    }


    private void getData() {
        id = getIntent().getStringExtra("id");
        Flowable<ProductDetailBean> productDetailBeanFlowable = Api.getInstance().getApiService(KlookApi.class)
                .findProductById(id)
                .compose(ResponseTransformer.handleResult());
        Flowable<ProductCommentData> collectionDataFlowable = Api.getInstance().getApiService(KlookApi.class)
                .pageProductComment(page, rows, id)
                .compose(ResponseTransformer.handleResult());

        Flowable.zip(productDetailBeanFlowable, collectionDataFlowable, new BiFunction<ProductDetailBean, ProductCommentData, String>() {
            @Override
            public String apply(ProductDetailBean date, ProductCommentData collectionData) throws Exception {
                commentBeanList.addAll(collectionData.list);
                productDetailBean = date;
                return "";
            }
        }).compose(RxTransformer.transformFlowWithLoading(this)).subscribe(new RxSubscriber<String>() {
            @Override
            protected void onSuccess(String s) {
                commen_list.notifyDataSetChanged();
                updateHeard(productDetailBean);
            }
        });

    }


    @Override
    public boolean isNeedHeardLayout() {
        return false;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_product;
    }


    @Override
    protected BasePresenter initPresent() {
        return null;
    }


    @OnClick({R.id.iv_back, R.id.iv_collect, R.id.iv_shop_car, R.id.tv_add_card, R.id.tv_go_buy, R.id.ll_connect_kefu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_collect:
                if (productDetailBean.isCollection()) {
                    cancelProduct();
                } else {
                    saveProduct();
                }
                break;
            case R.id.ll_connect_kefu:
                // 弹框提示前往页面
                rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            // 地图前往  测试
//                            MapNavigationUtils.goBaidu(context, new LatLng(Double.valueOf(productDetailBean.lat) ,Double.valueOf(productDetailBean.lng)));
                            MapNavigationUtils.goGd(context, new LatLng(Double.valueOf(productDetailBean.lat), Double.valueOf(productDetailBean.lng)));
                        } else {
                            ToastUtils.showShort("打开权限");
                        }
                    }
                });
                break;

            case R.id.iv_shop_car:
                // 查看购物车
                ShopCarListActivity.navToActivity(this);

                break;
            case R.id.tv_add_card:
                // 添加到购物车
                ConfirmOrderActivity.navToActivity(this, productDetailBean, ConfirmOrderActivity.ADDCARD);
                break;
            case R.id.tv_go_buy:
                // 前往购买
                ConfirmOrderActivity.navToActivity(this, productDetailBean, ConfirmOrderActivity.RESERVE);
                break;
        }
    }


    private void saveProduct() {
        if (productDetailBean != null) {
            Api.getInstance()
                    .getApiService(KlookApi.class)
                    .saveProduct(productDetailBean.id)
                    .compose(RxTransformer.transformFlowWithLoading(this))
                    .compose(ResponseTransformer.handleResult())
                    .subscribe(new RxSubscriber<Object>() {
                        @Override
                        protected void onSuccess(Object o) {
                            ToastUtils.showShort("收藏成功");
                            productDetailBean.isCollect = "1";
                            ivCollect.setSelected(productDetailBean.isCollection());
                        }
                    });
        }
    }

    private void cancelProduct() {
        Api.getInstance()
                .getApiService(KlookApi.class)
                .deleteProduct(productDetailBean.id)
                .compose(RxTransformer.transformFlowWithLoading(this))
                .compose(ResponseTransformer.handleResult())
                .subscribe(new RxSubscriber<Object>() {
                    @Override
                    protected void onSuccess(Object o) {
                        ToastUtils.showShort("取消收藏成功");
                        productDetailBean.isCollect = "0";
                        ivCollect.setSelected(productDetailBean.isCollection());
                    }
                });


    }


    private MapView mapView;
    private AMap aMap;

    private void initMap() {

        if (aMap == null) {
            aMap = mapView.getMap();
//            aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
//            aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
            aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
            aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
            aMap.getUiSettings().setScrollGesturesEnabled(false);
            aMap.getUiSettings().setZoomGesturesEnabled(false);
            aMap.getUiSettings().setZoomControlsEnabled(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mapView.onCreate(savedInstanceState); // 此方法必须重写

    }

    /**
     * 在地图上添加marker
     */
    private MarkerOptions markerOption;
    private Marker marker;

    private void addMarkersToMap(LatLng latLng) {
        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(latLng)
                .title("标题")
                .snippet("详细信息")
                .draggable(true);
        marker = aMap.addMarker(markerOption);
        marker.showInfoWindow();
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(productDetailBean.lat), Double.valueOf(productDetailBean.lng)), 14));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        ToastUtils.showShort("你点击了mark111");
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        ToastUtils.showShort("你点击了mark222");
        return false;
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
//        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
//        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mapView.onDestroy();
    }

}



