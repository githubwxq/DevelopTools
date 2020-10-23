package com.example.uitestdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.example.uitestdemo.R;
import com.juziwl.uilibrary.popupwindow.easypopup.EasyPopup;
import com.luck.picture.lib.utils.DisplayUtils;
import com.wxq.commonlibrary.util.ToastUtils;

import java.util.List;

public class ItemDragAdapter extends BaseItemDraggableAdapter<PostData, PostPublishViewHolder> {


    public ItemDragAdapter(List<PostData> data) {
        super(R.layout.toast_item, data);
    }

    @Override
    protected void convert(PostPublishViewHolder helper, PostData item) {
        helper.setText(R.id.toast_text, item.content);
        helper.setVisible(R.id.toast_image, item.isShowAdd);
        helper.getView(R.id.toast_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow(view, mContext,item);
            }
        });
    }

    private void showPopupWindow(View view, Context mContext,PostData item) {
        //view背景改变
        EasyPopup classPopupWindow;
        View subjectView = LayoutInflater.from(mContext).inflate(R.layout.adapter_item, null);
        classPopupWindow = new EasyPopup(mContext)
                .setContentView(subjectView)
                .setFocusAndOutsideEnable(true)
                .setWidth(DisplayUtils.getScreenWidth(mContext))
                .apply();
        subjectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("内容为"+item.content);
            }
        });
        classPopupWindow.showAsDropDown(view);
        classPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // view 的背景还原
            }
        });
    }
}
