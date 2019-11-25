package com.juziwl.uilibrary.imageview;

import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

import com.juziwl.uilibrary.R;


/**
 * @author null
 * @modify Neil
 */
public class SwitchImageView extends AppCompatImageView implements OnClickListener {

    private boolean state;

    private OnChangeListener listener;

    public SwitchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(this);
        this.setImageResource(R.mipmap.common_open);
    }

    public SwitchImageView(Context context) {
        this(context, null);
    }

    public void setOnChangeListener(OnChangeListener listener) {
        this.listener = listener;
    }

    public interface OnChangeListener {
        /**
         * 改变状态监听
         * @param state
         */
        public void onChange(boolean state);
    }

    @Override
    public void onClick(View v) {
        if (state) {
            this.setImageResource(R.mipmap.common_open);
        } else {
            this.setImageResource(R.mipmap.common_close);
        }
        state = !state;
        if (listener != null) {
            listener.onChange(state);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected) {
            this.setImageResource(R.mipmap.common_open);
        } else {
            this.setImageResource(R.mipmap.common_close);
        }
        state = !state;
    }

    public boolean isState() {
        return state;
    }
}
