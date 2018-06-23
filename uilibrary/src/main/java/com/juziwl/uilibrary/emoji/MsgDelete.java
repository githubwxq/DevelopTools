package com.juziwl.uilibrary.emoji;

import android.content.Context;
import android.view.KeyEvent;
import android.widget.EditText;

import com.juziwl.uilibrary.R;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class MsgDelete {
    private Context mContext;
    private String[] stringArray;
    private static MsgDelete msgDelete;

    private MsgDelete(Context context) {
        this.mContext = context;
        stringArray = mContext.getResources().getStringArray(R.array.default_smiley_texts);
    }

    public static MsgDelete getInstance(Context context) {
        if (msgDelete == null) {
            msgDelete = new MsgDelete(context);
        }
        return msgDelete;
    }

    public boolean delete(EditText etMsg, boolean isDispatchKeyEvent) {
        boolean flag = false;
        String s = etMsg.getText().toString();
        if (etMsg.getSelectionStart() != s.length()) {
            String temp = s.substring(0, etMsg.getSelectionStart());
            if (temp.endsWith("]")) {
                int index = temp.lastIndexOf("[");
                if (index > -1) {
                    String delete = temp.substring(index, temp.length());
                    for (String aStringArray : stringArray) {
                        if (delete.equals(aStringArray)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        etMsg.getText().delete(index, temp.length());
                        return true;
                    }
                }
//                public static final String AT_SPECIAL_WORD = " ";
//                public static final String AT_WORD = "@";

            } else if (temp.endsWith("")) {
                int index = temp.lastIndexOf("@");
                if (index > -1) {
                    etMsg.getText().delete(index, temp.length());
                    return true;
                }
            }
        } else {
            if (s.length() > 0) {
                String last = s.substring(s.length() - 1);
                if (last.equals("]")) {
                    int index = s.lastIndexOf("[");
                    if (index > -1) {
                        String a = s.substring(index, s.length());
                        for (String aStringArray : stringArray) {
                            if (a.equals(aStringArray)) {
                                flag = true;
                                break;
                            }
                        }
                        if (flag) {
                            etMsg.getText().delete(index, s.length());
                            return true;
                        }
                    }
                } else if (last.equals(" ")) {
                    int index = s.lastIndexOf("@");
                    if (index > -1) {
                        etMsg.getText().delete(index, s.length());
                        return true;
                    }
                }
            }
        }
        if (isDispatchKeyEvent) {
            etMsg.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
        }
        return false;
    }
}
