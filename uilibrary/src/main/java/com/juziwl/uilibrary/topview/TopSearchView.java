package com.juziwl.uilibrary.topview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.juziwl.uilibrary.R;
import com.wxq.commonlibrary.util.KeyboardUtils;


/**
 * @author 王晓清
 * @version V_5.0.0
 * @date 2017年06月21日
 * @description 顶部搜索控件封装
 */

public class TopSearchView extends LinearLayout implements View.OnClickListener {

    ImageView ivDelete;
    /**
     * 外部的输入框
     */
    EditText etCcontent;

    int totalNumber;

    int maxPic;

    public TopSearchView(Context context) {
        super(context);
        initView(context);
    }

    public TopSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TopSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化相关控件
     * @param context
     */
    private void initView(Context context) {
        //加载布局
        View view = View.inflate(context, R.layout.common_top_search_edit_layout
                , this);
        ivDelete=(ImageView)view.findViewById(R.id.iv_delete);
        etCcontent=(EditText)view.findViewById(R.id.et_content);
        etCcontent.addTextChangedListener(new MyTextWatcher());
        etCcontent.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    KeyboardUtils.hideSoftInput(etCcontent);
                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                    if(searchListener!=null) {
                        searchListener.search(etCcontent.getText().toString());
                    }
                }
                return false;
            }
        });
        ivDelete.setOnClickListener(this);
    }

    private void search(Editable text) {
        
        
        
    }



    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(etCcontent.getText().length()==0){
                ivDelete.setVisibility(GONE);
            }else{
                ivDelete.setVisibility(VISIBLE);
            }
            if (searchListener!=null){
                searchListener.textChange(etCcontent.getText().toString());
            }
        }
    }


    private void initEmoji() {


    }



    @Override
    public void onClick(View view) {
            etCcontent.setText("");
    }

    public void setSearchClickAndChangeListener(SearchClickAndChangeListener SearchClickAndChangeListener) {
        this.searchListener = SearchClickAndChangeListener;
    }

    public SearchClickAndChangeListener searchListener;

    public  interface SearchClickAndChangeListener{


        /**
         * 搜索
         * @param value
         */
        void search(String value);


        /**
         * 文本改变
         * @param value
         */
        void textChange(String value);



    }




}
