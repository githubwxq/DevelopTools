package com.juziwl.uilibrary.popupwindow.easypopup;

import android.content.Context;
import android.view.View;
/**
 * Created by zyyoona7 on 2017/8/3.
 * <p>https://github.com/zyyoona7/EasyPopup
 * PopupWindow封装
 */
public class EasyPopup extends BasePopup<EasyPopup> {

    private OnViewListener mOnViewListener;

    public static EasyPopup create() {
        return new EasyPopup();
    }

    public static EasyPopup create(Context context) {
        return new EasyPopup(context);
    }

    public EasyPopup() {

    }

    public EasyPopup(Context context) {
        setContext(context);
    }

    @Override
    protected void initAttributes() {

    }

    @Override
    protected void initViews(View view, EasyPopup popup) {
        if (mOnViewListener != null) {
            mOnViewListener.initViews(view, popup);
        }
    }

    public EasyPopup setOnViewListener(OnViewListener listener) {
        this.mOnViewListener = listener;
        return this;
    }

    public interface OnViewListener {

        void initViews(View view, EasyPopup popup);
    }
}

//、、调用方法一
//    if (classPopupWindow == null) {
//            View subjectView = LayoutInflater.from(this).inflate(R.layout.popup_space_choose_class, null);
//            classPopupWindow = new EasyPopup(this)
//            .setContentView(subjectView)
//            .setFocusAndOutsideEnable(true)
//            .setWidth(DisplayUtils.getScreenWidth())
//            .createPopup();
//            classPopupWindow.setOnDismissListener(() -> {
//            viewDelegate.showShadow(false);
//            viewDelegate.setClassNameSelected(false);
//            });
//            RecyclerView recyclerView = (RecyclerView) subjectView.findViewById(R.id.recycler);
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
//            recyclerView.setLayoutManager(gridLayoutManager);
//            ClassSubjectPopupAdapter adapter = new ClassSubjectPopupAdapter(this, classes);
//            adapter.setOnItemClickListener((viewHolder, view, position) -> {
//            classes.get(adapter.prePosition).isSelected = false;
//            classes.get(position).isSelected = true;
//            adapter.notifyDataSetChanged();
//            classId = classes.get(position).classId;
//            className = classes.get(position).className;
//            viewDelegate.setClassName(className);
//            classPopupWindow.dismiss();
//            page = 1;
//            listWrongBook(true);
//            });
//            recyclerView.setAdapter(adapter);
//            }
//            classPopupWindow.showAsDropDown(viewDelegate.getLine());
