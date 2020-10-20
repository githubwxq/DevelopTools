package com.example.uitestdemo.fragment;

import com.example.uitestdemo.R;
import com.juziwl.uilibrary.customview.view.SeatTable;
import com.wxq.commonlibrary.base.BaseFragment;
import com.wxq.commonlibrary.base.BasePresenter;

import butterknife.BindView;


public class SeatTableFragment extends BaseFragment {

    @BindView(R.id.seatView)
    SeatTable seatTableView;


    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_seat_table;
    }

    @Override
    protected void initViews() {

        seatTableView.setScreenName("8号厅荧幕");//设置屏幕名称
        seatTableView.setMaxSelected(3);//设置最多选中
        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {
            @Override
            public boolean isValidSeat(int row, int column) {
//                if(column==2) {
//                    return false;
//                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
//                if(row==6&&column==6){
//                    return true;
//                }
                return false;
            }

            @Override
            public void checked(int row, int column) {

            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10,15);

    }

}
