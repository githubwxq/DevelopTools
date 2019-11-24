package com.juziwl.uilibrary.emoji;

import android.content.Context;
import androidx.collection.ArrayMap;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.juziwl.uilibrary.R;
import java.util.ArrayList;

/**
 * @author wxq
 * @version V_5.0.0
 * @date 2016/9/20 0020
 * @description 表情控件封装
 * ExpressionView  emoji=(ExpressionView)findViewById(R.id.text_emoji);
 * EditText  et_msg=(EditText)findViewById(R.id.et_msg);
 * emoji.initEmojiView(this,et_msg,length);
 * emoji.setEmoijVisiable(true);
 */
public class ExpressionView extends LinearLayout implements AdapterView.OnItemClickListener {
    private Context mContext;
    private EditText mEditText;
    private LinearLayout emoji_relative;
    private ViewPager emojipager;
    private LinearLayout indicator;
    private String[] s;
    private ArrayMap<String, Integer> map = new ArrayMap<>(100);
    private ArrayList<GridView> gvList = new ArrayList<>(10);
    private ArrayList<ImageView> imageViews = new ArrayList<>(10);
    private int curPosition = 0;
    private int maxLength;

    public ExpressionView(Context context) {
        super(context);
        initView(context);
    }

    public ExpressionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ExpressionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initEmojiView(Context context, EditText editText, int length) {
        mContext = context;
        mEditText = editText;
        maxLength = length;
        initEmoji();
        dealWithEdit();
    }

    public void setEmoijVisiable(boolean visiable) {
        if (visiable) {
            emoji_relative.setVisibility(View.VISIBLE);
        } else {
            emoji_relative.setVisibility(View.GONE);
        }
    }

    private void initView(Context context) {
        //加载布局
        View view = View.inflate(context, R.layout.common_expression, this);
        emoji_relative = (LinearLayout) view.findViewById(R.id.emoji_relative);
        indicator = (LinearLayout) view.findViewById(R.id.indicator);
        emojipager = (ViewPager) view.findViewById(R.id.emojipager);

    }

    private void dealWithEdit() {
        mEditText.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                return MsgDelete.getInstance(mContext).delete(mEditText, false);
            }
            return false;
        });
    }


    private void initEmoji() {
        s = this.getResources().getStringArray(R.array.default_smiley_texts);
        //屏幕宽度
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int everyitem = screenWidth > 720 ? 23 : 20; // 如果框大于720 3倍的画就每行8个  否者 每行7个
        //表情间的间隔
        int spacing = dp2px(8);
        //表情的宽度（即高度）
        int itemWidth = screenWidth > 720 ? (screenWidth - spacing * 9) / 8 : (screenWidth - spacing * 8) / 7;
        //表情面板的高度
        int gvHeight = itemWidth * 3 + 4 * spacing;
        map = getFaceID();
        ArrayList<String> name = new ArrayList<>();
        int margin = dp2px(5);
        for (int i = 0; i < s.length; i++) {
            name.add(s[i]);
            if (name.size() == everyitem) {
                // 每20个表情为一个grideview控件
                gvList.add(createGridView(screenWidth, spacing, gvHeight, name, itemWidth));
                name = new ArrayList<>();

                ImageView imageView = new ImageView(mContext);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(margin, margin, margin, margin);
                imageView.setLayoutParams(lp);
                if (gvList.size() == 1) {
                    imageView.setBackgroundResource(R.mipmap.ads_point_pre);
                } else {
                    imageView.setBackgroundResource(R.mipmap.ads_point);
                }
                imageViews.add(imageView);
                indicator.addView(imageView);
            }
        }

        if (name.size() > 0) {
            gvList.add(createGridView(screenWidth, spacing, gvHeight, name, itemWidth));

            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(margin, margin, margin, margin);
            imageView.setLayoutParams(lp);
            imageView.setBackgroundResource(R.mipmap.ads_point);
            imageViews.add(imageView);
            indicator.addView(imageView);
        }
        EmojiPagerAdapter pagerAdapter = new EmojiPagerAdapter(gvList);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, gvHeight);
        emojipager.setLayoutParams(params);
        emojipager.setAdapter(pagerAdapter);
        emojipager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                imageViews.get(curPosition).setBackgroundResource(R.mipmap.ads_point);
                imageViews.get(position).setBackgroundResource(R.mipmap.ads_point_pre);
                curPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    private GridView createGridView(int screenWidth, int spacing, int gvHeight, ArrayList<String> name, int itemWidth) {
        GridView gv = new GridView(mContext);
        int columns = screenWidth > 720 ? 8 : 7; // 如果框大于720 3倍的画就每行8个  否者 每行7个
        gv.setNumColumns(columns);
        gv.setSelector(android.R.color.transparent);
        gv.setHorizontalSpacing(spacing);
        gv.setVerticalSpacing(spacing);
        gv.setPadding(spacing, spacing, spacing, spacing);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(screenWidth, gvHeight);
        gv.setLayoutParams(params);
        EmojiGridViewAdapter adapter = new EmojiGridViewAdapter(mContext, itemWidth, name, map);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(this);
        return gv;
    }


    public ArrayMap<String, Integer> getFaceID() {
        // 用反射把资源中的表情图片找出来，通过图片的名字来找
        ArrayMap<String, Integer> map = new ArrayMap<>(100);
        int[] defaultSmileyResIds = SmileyParser.DEFAULT_SMILEY_RES_IDS;
        for (int i = 0; i < s.length; i++) {
            map.put(s[i], defaultSmileyResIds[i]);
        }
        return map;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 处理emogji表情
        Object itemAdapter = parent.getAdapter();
        if (itemAdapter instanceof EmojiGridViewAdapter) {
            EmojiGridViewAdapter adapter = (EmojiGridViewAdapter) itemAdapter;
            if (adapter.getCount() - 1 == position) {
                MsgDelete.getInstance(mContext).delete(mEditText, true);
            } else {
                if (position <= adapter.getEmojiName().size() - 1) {
                    String name = adapter.getItem(position);
                    if (mEditText.getText().toString().length() + name.length() > maxLength) {
                        return;
                    }
                    int index = mEditText.getSelectionStart();
                    mEditText.getText().insert(index, name);
                    mEditText.setSelection(index + name.length());
                }
            }
        }
    }

    public  int dp2px(final float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
