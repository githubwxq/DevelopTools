package com.example.uitestdemo.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.uitestdemo.R;
import com.orhanobut.logger.Logger;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;
import com.wxq.commonlibrary.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link TwoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwoFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.rv_one)
    RecyclerView rvOne;
    @BindView(R.id.rv_two)
    RecyclerView rvTwo;
    @BindView(R.id.rv_three)
    RecyclerView rvThree;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public TwoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TwoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TwoFragment newInstance(String param1, String param2) {
        TwoFragment fragment = new TwoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_two;
    }

    @Override
    protected void initViews() {
        rvOne.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTwo.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvThree.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<User> list=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user=new User();
            list.add(user);
            list.add(user);
            list.add(user);
        }






        rvOne.setAdapter(new BaseQuickAdapter<User,BaseViewHolder>(R.layout.poiitem_item,list) {

            @Override
            protected void convert(BaseViewHolder helper, User item) {

            }
        });


          rvTwo.setAdapter(new BaseQuickAdapter<User,BaseViewHolder>(R.layout.poiitem_item,list) {

            @Override
            protected void convert(BaseViewHolder helper, User item) {

            }
        });


          rvThree.setAdapter(new BaseQuickAdapter<User,BaseViewHolder>(R.layout.poiitem_item,list) {

            @Override
            protected void convert(BaseViewHolder helper, User item) {

            }
        });









    }

    @Override
    public void lazyLoadData(View view) {
        super.lazyLoadData(view);
        Logger.d(getClass().getSimpleName() + " twofragment 对用户第一次可见");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d(getClass().getSimpleName() + " twofragment onResume");
    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        Logger.d(getClass().getSimpleName() + " twofragment onFragmentResume");
    }


}
