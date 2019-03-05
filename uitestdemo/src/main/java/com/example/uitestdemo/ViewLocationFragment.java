package com.example.uitestdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.juziwl.uilibrary.customview.view.LocationView;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ViewLocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewLocationFragment extends BaseFragment {

    @BindView(R.id.location_view)
    LocationView locationView;
    Unbinder unbinder;

    public static ViewLocationFragment newInstance() {
        ViewLocationFragment fragment = new ViewLocationFragment();
        return fragment;
    }


    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_view_location;
    }

    @Override
    protected void initViews() {
        int locationViewTop = locationView.getTop();
        int locationViewLeft = locationView.getLeft();
        Log.e("location","locationViewTop"+locationViewTop+"+++++++++++locationViewLeft"+locationViewLeft);
        locationView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                int locationViewTop = locationView.getTop();
                int locationViewLeft = locationView.getLeft();
                final float scale = getActivity().getResources().getDisplayMetrics().density;
                Log.e("location","locationViewTop"
                        +locationViewTop+"+++++++++++locationViewLeft"+locationViewLeft
                        +"scale"+scale
                        +"marginleft"+scale*50);
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
