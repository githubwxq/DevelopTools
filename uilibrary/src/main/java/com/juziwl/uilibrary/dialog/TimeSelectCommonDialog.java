package com.juziwl.uilibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.juziwl.uilibrary.PickerView;
import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


/**
 * @author Army
 * @version V_5.0.0
 * @modify Neil
 * @date 2016/4/20
 * @description desc
 */
public class TimeSelectCommonDialog extends Dialog {

    private final String[] months_big = {"01", "03", "05", "07", "08", "10", "12"};
    private final String[] months_little = {"04", "06", "09", "11"};
    private final String[] max_days = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17",
            "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private final String[] minutes = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33",
            "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51",
            "52", "53", "54", "55", "56", "57", "58", "59"};
    private final String[] hours = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
            "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    private final String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    private int startYear = 1990, endYear = 2020;
    private static final int NUMBER_2 = 2, NUMBER_4 = 4, NUMBER_28 = 28, NUMBER_29 = 29, NUMBER_30 = 30, NUMBER_100 = 100, NUMBER_400 = 400;

    private Context context;

    public TimeSelectCommonDialog(Context context) {
        super(context, R.style.commonDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.common_layout_time_select_dialog, null);

        final PickerView year = (PickerView) view.findViewById(R.id.year);
        final PickerView month = (PickerView) view.findViewById(R.id.month);
        final PickerView day = (PickerView) view.findViewById(R.id.day);
        final PickerView hour = (PickerView) view.findViewById(R.id.hour);
        final PickerView minute = (PickerView) view.findViewById(R.id.minute);
        Calendar calendar = Calendar.getInstance();

        ArrayList<String> years = new ArrayList<String>();
        for (int i = startYear; i <= endYear; i++) {
            years.add(i + "");
        }
        year.setData(years);
        year.setSelected(years.indexOf(calendar.get(Calendar.YEAR) + ""));

        int curMonth = calendar.get(Calendar.MONTH);
        month.setData(new ArrayList<>(Arrays.asList(months)));
        month.setSelected(curMonth);

        final List<String> big = Arrays.asList(months_big);
        List<String> little = Arrays.asList(months_little);

        int curDay = calendar.get(Calendar.DAY_OF_MONTH);

        final ArrayList<String> days = new ArrayList<String>();

        year.setOnSelectListener(new PickerView.MyOnSelectListener() {
            @Override
            public void onSelect(String text) {
                int selectYear = Integer.parseInt(text);
                int mon = Integer.parseInt(month.getSelectStr());
                int selectDay = Integer.parseInt(day.getSelectStr());
                if (mon == NUMBER_2) {
                    if ((selectYear % NUMBER_4 == 0 && selectYear % NUMBER_100 != 0)
                            || selectYear % NUMBER_400 == 0) {
                        days.clear();
                        days.addAll(Arrays.asList(max_days).subList(0, NUMBER_29));
                        day.setData(days);
                        if (selectDay > NUMBER_29) {
                            day.setSelected(days.size() - 1);
                        } else {
                            day.setSelected(days.indexOf(day.getSelectStr()));
                        }
                    } else {
                        days.clear();
                        days.addAll(Arrays.asList(max_days).subList(0, NUMBER_28));
                        day.setData(days);
                        if (selectDay > NUMBER_28) {
                            day.setSelected(days.size() - 1);
                        } else {
                            day.setSelected(days.indexOf(day.getSelectStr()));
                        }
                    }
                }
            }
        });

        month.setOnSelectListener(new PickerView.MyOnSelectListener() {
            @Override
            public void onSelect(String text) {
                int selectYear = Integer.parseInt(year.getSelectStr());
                String selectDayStr = day.getSelectStr();
                int selectDay = Integer.parseInt(selectDayStr);
                int selectMonth = Integer.parseInt(month.getSelectStr());
                if (selectMonth == NUMBER_2) {
                    if ((selectYear % NUMBER_4 == 0 && selectYear % NUMBER_100 != 0)
                            || selectYear % NUMBER_400 == 0) {
                        days.clear();
                        days.addAll(Arrays.asList(max_days).subList(0, NUMBER_29));
                        day.setData(days);
                        if (selectDay > NUMBER_29) {
                            day.setSelected(days.size() - 1);
                        } else {
                            day.setSelected(days.indexOf(selectDayStr));
                        }
                    } else {
                        days.clear();
                        days.addAll(Arrays.asList(max_days).subList(0, NUMBER_28));
                        day.setData(days);
                        if (selectDay > NUMBER_28) {
                            day.setSelected(days.size() - 1);
                        } else {
                            day.setSelected(days.indexOf(selectDayStr));
                        }
                    }
                } else {
                    if (big.contains(text)) {
                        days.clear();
                        days.addAll(Arrays.asList(max_days));
                        day.setData(days);
                        day.setSelected(days.indexOf(selectDayStr));
                    } else {
                        days.clear();
                        days.addAll(Arrays.asList(max_days).subList(0, NUMBER_30));
                        day.setData(days);
                        if (selectDay > NUMBER_30) {
                            day.setSelected(days.size() - 1);
                        } else {
                            day.setSelected(days.indexOf(selectDayStr));
                        }
                    }
                }
            }
        });

        day.setData(days);
        String curMonthStr = (curMonth + 1) > 9 ? (curMonth + 1) + "" : "0" + (curMonth + 1);
        if (big.contains(curMonthStr)) {
            days.addAll(Arrays.asList(max_days));
        } else if (little.contains(curMonthStr)) {
            days.addAll(Arrays.asList(max_days).subList(0, 30));
        } else {
            if ((startYear % NUMBER_4 == 0 && startYear % NUMBER_100 != 0)
                    || startYear % NUMBER_400 == 0) {
                days.addAll(Arrays.asList(max_days).subList(0, NUMBER_29));
            } else {
                days.addAll(Arrays.asList(max_days).subList(0, NUMBER_28));
            }
        }
        day.setSelected(curDay - 1);

        hour.setData(new ArrayList<>(Arrays.asList(hours)));
        hour.setSelected(calendar.get(Calendar.HOUR_OF_DAY));

        minute.setData(new ArrayList<>(Arrays.asList(minutes)));
        minute.setSelected(calendar.get(Calendar.MINUTE));

        setContentView(view);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        // 高度设置为屏幕的0.6
        lp.width = (int) (ScreenUtils.getScreenWidth(context) * 0.8);
        dialogWindow.setAttributes(lp);

        view.findViewById(R.id.cancel).setOnClickListener((View v) -> {
            if (listener != null) {
                listener.cancel();
            }
        });

        view.findViewById(R.id.make_sure).setOnClickListener(v -> {
            if (listener != null) {
                String time = year.getSelectStr() + "-" + month.getSelectStr() + "-" + day.getSelectStr() + " " + hour.getSelectStr() + ":" + minute.getSelectStr();
                listener.makeSure(time);
            }
        });
    }

    private onDialogButtonClickListener listener;

    public void setOnDialogButtonClickListener(onDialogButtonClickListener listener) {
        this.listener = listener;
    }

    public interface onDialogButtonClickListener {
        /**
         * 取消
         */
        void cancel();

        /**
         * 确认
         *
         * @param time
         */
        void makeSure(String time);
    }
}
