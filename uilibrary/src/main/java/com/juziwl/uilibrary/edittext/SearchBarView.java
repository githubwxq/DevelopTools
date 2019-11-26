package com.juziwl.uilibrary.edittext;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.juziwl.uilibrary.R;
import com.wxq.commonlibrary.util.StringUtils;

/**
 * @description: 简单的搜索bar
 */
public class SearchBarView extends LinearLayout {

    private Context mContext;
    private EditText mSearchEdt;
    private TextView mSearchTxt;
    private ImageView mCancelImg;
    private ImageView iv_search_back;

    private static final int TYPE_SEARCH = 1000;
    private static final int TYPE_CANCEL = 1001;
    private int mTypeSearch = TYPE_SEARCH;

    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * @param context
     */
    public SearchBarView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    /**
     * @param context
     */
    public SearchBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    /**
     * @description: 初始化
     * @return: void
     */
    private void init() {
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setLayoutParams(lp);

        LayoutInflater.from(mContext).inflate(R.layout.searchbar, this);
        mSearchEdt = (EditText) this.findViewById(R.id.edit);
        mSearchTxt = (TextView) this.findViewById(R.id.search);
        mCancelImg = (ImageView) this.findViewById(R.id.cancel);
        iv_search_back = (ImageView) this.findViewById(R.id.iv_search_back);
        initListener();

    }

    /**
     * @description:
     * @return: void
     */
    private void initListener() {
        mSearchTxt.setText("取消");
        mTypeSearch = TYPE_CANCEL;
        mSearchEdt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (StringUtils.isEmpty(s.toString())) {
                    if (listener != null) {
                        listener.onClean();
                    }
                    mSearchTxt.setText("取消");
                    mTypeSearch = TYPE_CANCEL;
                    mCancelImg.setVisibility(GONE);
                } else {
                    mSearchTxt.setText(R.string.search);
                    mTypeSearch = TYPE_SEARCH;
                    mCancelImg.setVisibility(VISIBLE);
                }
                if (listener != null) {
                    listener.onTextChange(s.toString());
                }


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // 注：此方法会造成搜索两次
//        mSearchEdt.setOnKeyListener(new OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER) {//修改回车键功能|
//                    if (listener != null) {
//                        listener.onSearch(mSearchEdt.getText().toString());
//                    }
//                }
//                return false;
//            }
//        });

        mSearchEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (listener != null) {
                        listener.onSearch(mSearchEdt.getText().toString());
                    }
                }

                return false;
            }
        });

        mCancelImg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mSearchEdt.setText("");
                mSearchTxt.setText("取消");
                mTypeSearch = TYPE_CANCEL;
            }
        });

        mSearchTxt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mTypeSearch == TYPE_SEARCH) {
                    if (StringUtils.isEmpty(mSearchEdt.getText().toString())) {
                        Toast.makeText(mContext, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        if (listener != null) {
                            listener.onSearch(mSearchEdt.getText().toString());
                        }
                        mSearchTxt.setText("取消");
                        mTypeSearch = TYPE_CANCEL;
                    }


                } else {
                    if (activity != null) {
                        activity.finish();
                    }
//                    mSearchEdt.setText("");
//                    mSearchTxt.setText(R.string.search);
//                    mTypeSearch = TYPE_SEARCH;
                }

            }
        });

        iv_search_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (backlistener != null) {
                    backlistener.onBack();
                }
            }
        });
    }

    /**
     * @param text
     * @description:
     * @return: void
     */
    public void setSearchText(String text) {
        mSearchEdt.setText(text);
        mSearchEdt.setSelection(text.length());
        mSearchTxt.setText(R.string.search);
        mTypeSearch = TYPE_SEARCH;
    }

    /**
     * @description:
     * @return: void
     */
    public void setSearchBack(boolean visible) {
        if (visible) {
            iv_search_back.setVisibility(VISIBLE);
        } else {
            iv_search_back.setVisibility(GONE);
        }
    }

    /**
     * @description:
     * @return: void
     */
    public void setSearchMsgCancel() {

        if (mSearchTxt != null) {
            mSearchTxt.setText("取消");
            mTypeSearch = TYPE_CANCEL;
        }
    }


    private OnSearchListener listener;

    public void setOnSearchListener(OnSearchListener listener) {
        this.listener = listener;
    }

    public interface OnSearchListener {
        void onSearch(String text);

        void onClean();

        void onTextChange(String text);
    }

    public String getSearchText() {
        return mSearchEdt.getText().toString();
    }

    public EditText getSearchEditText(){
        return mSearchEdt;
    }

    private OnSearchBackListener backlistener;
    public void setOnSearchBackListener(OnSearchBackListener listener) {
        this.backlistener = listener;
    }
    public interface OnSearchBackListener {
        void onBack();
    }

}
