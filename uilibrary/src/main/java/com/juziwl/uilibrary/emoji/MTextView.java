package com.juziwl.uilibrary.emoji;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import com.juziwl.uilibrary.R;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @author huangwei
 * @功能 图文混排TextView
 */
@SuppressLint("ResourceAsColor")
public class MTextView extends AppCompatTextView {
    /**
     * 缓存测量过的数据
     */
    private HashMap<String, SoftReference<MeasuredData>> measuredData = new HashMap<>();
    private int hashIndex = 0;
    /**
     * 存储当前文本内容，每个item为一行
     */
    ArrayList<LINE> contentList = new ArrayList<LINE>();
    private Context context;
    /**
     * 用于测量字符宽度
     */
    private TextPaint paint = new TextPaint();

    //	private float lineSpacingMult = 0.5f;
//    private int textColor = getResources().getColor(R.color.gray);

    //行距
    private float lineSpacing;
    /**
     * 最大宽度
     */
    private int maxWidth;
    /**
     * 只有一行时的宽度
     */
    private int oneLineWidth = -1;
    /**
     * 已绘的行中最宽的一行的宽度
     */
    private float lineWidthMax = -1;
    /**
     * 存储当前文本内容,每个item为一个字符或者一个SpanObject
     */
    private ArrayList<Object> obList = new ArrayList<Object>();
    /**
     * 是否使用默认{@link #onMeasure(int, int)}和{@link #onDraw(Canvas)}
     */
    private boolean useDefault = false;
    private CharSequence text = "";

    private int minHeight = -1;
    /**
     * 用以获取屏幕高宽
     */
    private DisplayMetrics displayMetrics;
    /**
     * {@link BackgroundColorSpan}用
     */
    private Paint textBgColorPaint = new Paint();
    /**
     * {@link BackgroundColorSpan}用
     */
    private Rect textBgColorRect = new Rect();

    /**
     * //如果需要展开收起，会用到这个变量
     */
    private boolean isExpandable = false;

    /**
     * //如果需要显示不同的颜色，会用到这些变量
     */
    private int whichline = -1, start = -1, end = -1, color = -1;
    private int start2 = -1, end2 = -1, color2 = -1;
    private boolean isMatchParent = false;
    private boolean isShowName = false;

    private int extraWidth = 0;
    private String atText = "";
    private int atTextColor;
    private StringBuilder sb = new StringBuilder("");

    /**
     * 用户点击姓名
     */
    private ClickName clickName;
    private String name1, name2;
    private int start1X, start1Y;
    private int start2X, start2Y;
    private int rx, ry;

    public void setClickName(ClickName clickName) {
        this.clickName = clickName;
    }

    public MTextView(Context context) {
        this(context, null);
    }

    public MTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint.setAntiAlias(true);
        lineSpacing = 0;
        setLineSpacingDP(1.5f);

        displayMetrics = new DisplayMetrics();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.common_MTextView);
        isMatchParent = typedArray.getBoolean(R.styleable.common_MTextView_common_isMatchParent, false);
        typedArray.recycle();
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void setMaxWidth(int maxpixels) {
        super.setMaxWidth(maxpixels);
        maxWidth = maxpixels;
    }

    @Override
    public void setMinHeight(int minHeight) {
        super.setMinHeight(minHeight);
        this.minHeight = minHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (useDefault) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int width = 0, height = 0;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
                width = widthSize;
                break;
            case MeasureSpec.UNSPECIFIED:

                ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                width = displayMetrics.widthPixels;
                break;
            default:
                break;
        }
        if (maxWidth > 0 && !isMatchParent) {
            width = Math.min(width, maxWidth);
        }
        paint.setTextSize(this.getTextSize());
        paint.setColor(getCurrentTextColor());
        int realHeight = measureContentHeight(width);
//        MeasuredData cacheData = getCacheData(text.toString() + atText);
//        if(cacheData != null){
//            contentList = cacheData.contentList;
//        }
        //如果实际行宽少于预定的宽度，减少行宽以使其内容横向居中
        int leftPadding = getCompoundPaddingLeft();
        int rightPadding = getCompoundPaddingRight();

        if (!isMatchParent) {
            width = Math.min(width, (int) lineWidthMax + leftPadding + rightPadding);

            if (oneLineWidth > -1) {
                width = oneLineWidth;
            }
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                height = realHeight;
                break;
            case MeasureSpec.UNSPECIFIED:
                height = realHeight;
                break;
            default:
                break;
        }

        height += getCompoundPaddingTop() + getCompoundPaddingBottom();

        if (minHeight > 0) {
            height = Math.max(height, minHeight);
        }
//        if (contentList.size() == 1 && getCompoundPaddingBottom() == 0 && getCompoundPaddingTop() == 0) {
//            height = Math.min(height, minHeight);
//        } else {
//            height = Math.max(height, minHeight);
//        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (useDefault) {
            super.onDraw(canvas);
            return;
        }
//        MeasuredData cacheData = getCacheData(text.toString() + atText + (isExpandable ? ";getMaxLine=" + getMaxLine() : ""));
//        if (cacheData == null) {
//            return;
//        }
//        ArrayList<LINE> contentList = cacheData.contentList;
        if (contentList.isEmpty()) {
            return;
        }
        int width;

        Object ob;

        int leftPadding = getCompoundPaddingLeft();
        int topPadding = getCompoundPaddingTop();

        float height = topPadding;
        String startStr = null;
//        float height = 0 + topPadding + lineSpacing;
        //只有一行时
//        if (oneLineWidth != -1) {
//            height = getMeasuredHeight() / 2 - contentList.get(0).height / 2;
//        }
//        LogUtil.e("onDraw  text == " + text.toString(), false);
        for (int i = 0; i < contentList.size(); i++) {
            LINE aContentList = contentList.get(i);
            //绘制一行
            float realDrawedWidth = leftPadding;
            for (int j = 0; j < aContentList.line.size(); j++) {
                ob = aContentList.line.get(j);
                width = aContentList.widthList.get(j);
                float y = height + aContentList.height - paint.getFontMetrics().descent;
                if (ob instanceof String) {
                    String str = (String) ob;
                    if (whichline == i && j == 0 && start < end && start < str.length() && end <= str.length()) {
                        if (start != 0) {
                            startStr = str.substring(0, start);
                            name1 = startStr;
                            drawText(canvas, realDrawedWidth, y, startStr);
                            realDrawedWidth += paint.measureText(startStr);
                        }
                        paint.setColor(color);
                        String substring1 = str.substring(start, end);
                        canvas.drawText(substring1, realDrawedWidth, y, paint);
                        start1X = (int) paint.measureText(substring1);
                        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
                        start1Y = (int) ((y + fm.bottom) - (y + fm.top));

                        if (start2 < end2 && start2 < str.length() && end2 <= str.length()) {
                            if (end == start2) {
                                paint.setColor(color2);
                                canvas.drawText(str.substring(start2, end2), realDrawedWidth
                                        + paint.measureText(substring1), y, paint);
                                //画文字最后是带@的文字
                                if (contentList.get(i).array.size() > 0) {
                                    int atStart = contentList.get(i).array.keyAt(0);
                                    int len = contentList.get(i).array.valueAt(0);
                                    drawAtText(canvas, realDrawedWidth + paint.measureText(str.substring(start, end2)),
                                            y, str.substring(end2), atStart, len);
                                } else {
                                    drawText(canvas, realDrawedWidth + paint.measureText(str.substring(start, end2)),
                                            y, str.substring(end2));
                                }
                            } else {
                                drawText(canvas, realDrawedWidth + paint.measureText(str.substring(start, end)),
                                        y, str.substring(end, start2));
                                paint.setColor(color2);
                                name2 = str.substring(start2, end2);
                                canvas.drawText(str.substring(start2, end2), realDrawedWidth
                                        + paint.measureText(str.substring(start, start2)), y, paint);
                                start2X = (int) paint.measureText(str.substring(start2, end2));
                                Paint.FontMetricsInt fm2 = paint.getFontMetricsInt();
                                start2Y = (int) ((y + fm2.bottom) - (y + fm2.top));
                                drawText(canvas, realDrawedWidth + paint.measureText(str.substring(start, end2)),
                                        y, str.substring(end2));
                            }
                        } else {
                            //画文字最后是带@的文字
                            if (contentList.get(i).array.size() > 0) {
                                int atStart = contentList.get(i).array.keyAt(0);
                                int len = contentList.get(i).array.valueAt(0);
                                drawAtText(canvas, realDrawedWidth + paint.measureText(str.substring(start, end)),
                                        y, str.substring(end), atStart, len);
                            } else {
                                drawText(canvas, realDrawedWidth + paint.measureText(substring1),
                                        y, str.substring(end));
                            }
                        }
                    } else {
                        if (!TextUtils.isEmpty(atText)) {
                            //一行最后一个字符串
                            if (j == aContentList.line.size() - 1) {
                                //倒数第二行，atText换行了
                                if (i == contentList.size() - 2 && contentList.get(i).array.size() > 0) {
                                    int start = contentList.get(i).array.keyAt(0);
                                    int len = contentList.get(i).array.valueAt(0);
                                    drawAtText(canvas, realDrawedWidth, y, str, start, len);
                                    //最后一行
                                } else if (i == contentList.size() - 1) {
                                    //atText换行的第二行
                                    if (contentList.get(i).array.size() > 0) {
                                        int start = contentList.get(i).array.keyAt(0);
                                        int len = contentList.get(i).array.valueAt(0);
                                        drawAtText(canvas, realDrawedWidth, y, str, start, len);
                                        //atText包含在最后一个str里
                                    } else if (atText.length() <= str.length()) {
                                        int endIndex = str.length() - atText.length();
                                        drawAtText(canvas, realDrawedWidth, y, str, endIndex, str.length() - endIndex);
                                    } else {
                                        drawText(canvas, realDrawedWidth, y, str);
                                    }
                                } else {
                                    drawText(canvas, realDrawedWidth, y, str);
                                }
                            } else {
                                drawText(canvas, realDrawedWidth, y, str);
                            }
                        } else {
                            drawText(canvas, realDrawedWidth, y, str);
                        }
                    }
                    realDrawedWidth += width;
                } else if (ob instanceof SpanObject) {
                    Object span = ((SpanObject) ob).span;
                    if (span instanceof ImageSpan) {
                        ImageSpan is = (ImageSpan) span;
                        Drawable d = is.getDrawable();
//                        if (!StringUtils.isEmpty(startStr)){
//                            realDrawedWidth = (int) (realDrawedWidth - paint.measureText(startStr));
//                        }
                        int left = (int) (realDrawedWidth - (i!=0?0:(TextUtils.isEmpty(startStr) ? 0 : paint.measureText(startStr))));
                        int top = (int) height;
                        int right = (int) (realDrawedWidth - (i!=0?0:(TextUtils.isEmpty(startStr) ? 0 : paint.measureText(startStr))) + width);
                        int bottom = (int) (height + width);//表情是正方形的
//                        int bottom = (int) (height + aContentList.height);
                        d.setBounds(left, top, right, bottom);
                        d.draw(canvas);
                        realDrawedWidth += width;
                    } else if (span instanceof BackgroundColorSpan) {
                        textBgColorPaint.setColor(((BackgroundColorSpan) span).getBackgroundColor());
                        textBgColorPaint.setStyle(Style.FILL);
                        textBgColorRect.left = (int) realDrawedWidth;
                        int textHeight = (int) getTextSize();
                        textBgColorRect.top = (int) (height + aContentList.height - textHeight - paint.getFontMetrics().descent);
                        textBgColorRect.right = textBgColorRect.left + width;
                        textBgColorRect.bottom = (int) (height + aContentList.height + lineSpacing - paint.getFontMetrics().descent);
                        canvas.drawRect(textBgColorRect, textBgColorPaint);
                        canvas.drawText(((SpanObject) ob).source.toString(), realDrawedWidth, y, paint);
                        realDrawedWidth += width;
                    } else//做字符串处理
                    {
                        canvas.drawText(((SpanObject) ob).source.toString(), realDrawedWidth, y, paint);
                        realDrawedWidth += width;
                    }
                }
            }
            height += aContentList.height + lineSpacing;
        }
    }

    private void drawText(Canvas canvas, float realDrawedWidth, float y, String str) {
        paint.setColor(getCurrentTextColor());
        canvas.drawText(str, realDrawedWidth, y, paint);
    }

    private void drawAtText(Canvas canvas, float realDrawedWidth, float y, String str, int start, int len) {
        drawText(canvas, realDrawedWidth, y, str.substring(0, start));
        paint.setColor(atTextColor);
        canvas.drawText(str.substring(start, start + len), realDrawedWidth + paint.measureText(str, 0, start), y, paint);
    }

    /**
     * 用于带ImageSpan的文本内容所占高度测量
     *
     * @param width 预定的宽度
     * @return 所需的高度
     */
    private int measureContentHeight(int width) {
        int maxLine = getMaxLine();
//        if (!isExpandable) {
        int cachedHeight = getCachedData(text + atText + (isExpandable ? ";getMaxLine=" + maxLine : ""), width);
        if (cachedHeight > 0) {
            return cachedHeight;
        }
//        }

        // 已绘的宽度
        float obWidth = 0;
        float obHeight = 0;

        float textSize = this.getTextSize();
        FontMetrics fontMetrics = paint.getFontMetrics();
        //行高
        float lineHeight = fontMetrics.bottom - fontMetrics.top;
        //计算出的所需高度
        float height = 0;
//        float height = lineSpacing;

        int leftPadding = getCompoundPaddingLeft();
        int rightPadding = getCompoundPaddingRight();

        float drawedWidth = 0, cloneDrawedWidth = 0;

        boolean splitFlag = false;//BackgroundColorSpan拆分用

        width = width - leftPadding - rightPadding;

        oneLineWidth = -1;

        contentList.clear();
//        ArrayList<LINE> contentList = new ArrayList<>();
        float atTextWidth = paint.measureText(atText);
        boolean isStringEndWithSpace = false;

        LINE line = new LINE();
        int length = obList.size();
        for (int i = 0; i < length; i++) {
            Object ob = obList.get(i);

            if (ob instanceof String) {
                obWidth = paint.measureText((String) ob);
                obHeight = textSize;
                if ("\n".equals(ob)) {
                    if (i == length - 1) {
                        isStringEndWithSpace = true;
                    }
                    splitFlag = true;
                }
            } else if (ob instanceof SpanObject) {
                Object span = ((SpanObject) ob).span;
                if (span instanceof ImageSpan) {
                    Rect r = ((ImageSpan) span).getDrawable().getBounds();
                    obWidth = r.right - r.left;
                    obHeight = r.bottom - r.top;
                    if (obHeight > lineHeight) {
                        lineHeight = obHeight;
                    }
                } else if (span instanceof BackgroundColorSpan) {
                    String str = ((SpanObject) ob).source.toString();
                    obWidth = paint.measureText(str);
                    obHeight = textSize;

                    //如果太长,拆分
                    int k = str.length() - 1;
                    while (width - extraWidth - drawedWidth < obWidth) {
                        obWidth = paint.measureText(str.substring(0, k--));
                    }
                    if (k < str.length() - 1) {
                        splitFlag = true;
                        SpanObject so1 = new SpanObject();
                        so1.start = ((SpanObject) ob).start;
                        so1.end = so1.start + k;
                        so1.source = str.substring(0, k + 1);
                        so1.span = ((SpanObject) ob).span;

                        SpanObject so2 = new SpanObject();
                        so2.start = so1.end;
                        so2.end = ((SpanObject) ob).end;
                        so2.source = str.substring(k + 1, str.length());
                        so2.span = ((SpanObject) ob).span;

                        ob = so1;
                        obList.set(i, so2);
                        i--;
                    }
                }//做字符串处理
                else {
                    String str = ((SpanObject) ob).source.toString();
                    obWidth = paint.measureText(str);
                    obHeight = textSize;
                }
            }

            //这一行满了，存入contentList,新起一行
            if (width - extraWidth - drawedWidth < obWidth || splitFlag) {
                splitFlag = false;
                contentList.add(line);

                if (drawedWidth > lineWidthMax) {
                    lineWidthMax = drawedWidth;
                }
                drawedWidth = 0;
                height += line.height + lineSpacing;

                lineHeight = obHeight;

                line = new LINE();
                if (maxLine > 0 && contentList.size() >= maxLine) {
                    if (i < obList.size() && !isExpandable && TextUtils.isEmpty(atText)) {
                        ArrayList<Object> lineList = contentList.get(contentList.size() - 1).line;
                        ArrayList<Integer> widthList = contentList.get(contentList.size() - 1).widthList;
                        if (lineList != null && !lineList.isEmpty()) {
                            if (lineList.get(lineList.size() - 1) instanceof String) {
                                String str = (String) lineList.get(lineList.size() - 1);
                                if (str.length() >= 2) {
                                    sb.delete(0, sb.length());
                                    sb.append(str);
                                    sb.delete(0, 2);
                                    sb.append("...");
                                    lineList.set(lineList.size() - 1, sb.toString());
                                } else {
                                    if (lineList.size() > 1) {
                                        lineList.remove(lineList.size() - 1);
                                        lineList.set(lineList.size() - 1, "...");
                                    } else {
                                        lineList.set(lineList.size() - 1, "...");
                                    }

                                    if (widthList != null && !widthList.isEmpty()) {
                                        if (widthList.size() > 1) {
                                            widthList.remove(widthList.size() - 1);
                                            widthList.set(widthList.size() - 1, (int) paint.measureText("..."));
                                        } else {
                                            widthList.set(widthList.size() - 1, (int) paint.measureText("..."));
                                        }
                                    }
                                }
                            } else if (lineList.get(lineList.size() - 1) instanceof SpanObject) {
                                lineList.set(lineList.size() - 1, "...");
                            }
                        }
                    }
                    break;
                }
            }
            cloneDrawedWidth = drawedWidth += obWidth;

            int size = line.line.size();
            if (ob instanceof String && line.line.size() > 0 && (line.line.get(size - 1) instanceof String)) {
                sb.delete(0, sb.length());
                sb.append(line.line.get(size - 1));
                sb.append(ob);
                ob = sb.toString();
                obWidth = obWidth + line.widthList.get(size - 1);
                line.line.set(size - 1, ob);
                line.widthList.set(size - 1, (int) obWidth);
                line.height = (int) lineHeight;
            } else {
                if (!"\n".equals(ob)) {
                    line.line.add(ob);
                    line.widthList.add((int) obWidth);
                    line.height = (int) lineHeight;
                }
            }
        }
        if (isStringEndWithSpace) {
            line.line.add(" ");
            line.widthList.add(1);
            line.height = (int) lineHeight;
        }

        if (line.line.size() > 0) {
            contentList.add(line);
            height += lineHeight + lineSpacing;
        }
        try {
            if (!TextUtils.isEmpty(atText)) {
                //最后一行剩余的空间能放得下atText
                LINE lastLine = contentList.get(contentList.size() - 1);
                sb.delete(0, sb.length());
                float remainLen = width - extraWidth - cloneDrawedWidth;
                if (remainLen >= atTextWidth) {
                    int size = lastLine.line.size();
                    if (lastLine.line.size() > 0 && (lastLine.line.get(size - 1) instanceof String)) {
                        sb.append(lastLine.line.get(size - 1));
                        sb.append(atText);
                        String ob = sb.toString();
                        obWidth = atTextWidth + lastLine.widthList.get(size - 1);
                        lastLine.line.set(size - 1, ob);
                        lastLine.widthList.set(size - 1, (int) obWidth);
                    } else {
                        lastLine.line.add(atText);
                        lastLine.widthList.add((int) atTextWidth);
                    }
                    drawedWidth += atTextWidth;
                } else {//放不下
                    float flagLength = remainLen;
                    int count = 0;
                    if (maxLine > 0) {
                        String point = "...";
                        float pointLen = paint.measureText(point);
                        //从后向前遍历获取每个字段的width
                        //不超过最大行数，一行放不下，需要换行
                        if (contentList.size() < maxLine) {
                            int preLen = lastLine.widthList.get(lastLine.widthList.size() - 1);
                            height = newLineHandle(lineHeight, height, sb, lastLine, flagLength, count, contentList);
                            int afterLen = lastLine.widthList.get(lastLine.widthList.size() - 1);
                            drawedWidth += afterLen - preLen;
                        } else {//刚好等于最大行数
                            sb.append(point).append(atText);
                            for (int k = lastLine.line.size() - 1; k >= 0; k--) {
                                int len = lastLine.widthList.get(k);
                                flagLength += len;
                                count++;
                                if (flagLength > atTextWidth + pointLen) {
                                    //最后一个字符串的长度很长，替换后面一部分
                                    if (count == 1) {
                                        String str = (String) lastLine.line.get(k);
                                        float flagLen = 0;
                                        int flagCount = 0;
                                        //从后往前遍历
                                        while (flagLen < atTextWidth + pointLen - remainLen && flagCount < str.length()) {
                                            flagLen = paint.measureText(str, str.length() - (++flagCount), str.length());
                                        }
                                        sb.insert(0, str.substring(0, str.length() - flagCount));
                                        lastLine.line.set(lastLine.line.size() - 1, sb.toString());
                                        lastLine.widthList.set(lastLine.line.size() - 1, (int) paint.measureText(sb.toString()));
                                    } else {
                                        //能满足条件的item是不是字符串，是的话要截掉后面的一部分再加上atText
                                        if (lastLine.line.get(lastLine.line.size() - count) instanceof String) {
                                            String str = (String) lastLine.line.get(lastLine.line.size() - count);
                                            float needLen = pointLen + atTextWidth - (flagLength - lastLine.widthList.get(lastLine.widthList.size() - count));
                                            float flagLen = 0;
                                            int flagCount = 0;
                                            while (flagLen < needLen && flagCount < str.length()) {
                                                flagLen = paint.measureText(str, str.length() - (++flagCount), str.length());
                                            }
                                            for (int i = 0; i < count - 1; i++) {
                                                lastLine.line.remove(lastLine.line.size() - 1);
                                                lastLine.widthList.remove(lastLine.widthList.size() - 1);
                                            }
                                            sb.insert(0, str.substring(0, str.length() - flagCount));
                                            lastLine.line.set(lastLine.line.size() - 1, sb.toString());
                                            lastLine.widthList.set(lastLine.line.size() - 1, (int) paint.measureText(sb.toString()));
                                        } else {
                                            for (int i = 0; i < count; i++) {
                                                lastLine.line.remove(lastLine.line.size() - 1);
                                                lastLine.widthList.remove(lastLine.widthList.size() - 1);
                                            }
                                            if (lastLine.line.get(lastLine.line.size() - 1) instanceof String) {
                                                String str = (String) lastLine.line.get(lastLine.line.size() - 1);
                                                sb.insert(0, str);
                                                lastLine.line.set(lastLine.line.size() - 1, sb.toString());
                                                lastLine.widthList.set(lastLine.widthList.size() - 1, (int) paint.measureText(sb.toString()));
                                            } else {
                                                lastLine.line.add(sb.toString());
                                                lastLine.widthList.add((int) paint.measureText(sb.toString()));
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    } else {//一行放不下，需要换行
                        int preLen = lastLine.widthList.get(lastLine.widthList.size() - 1);
                        height = newLineHandle(lineHeight, height, sb, lastLine, flagLength, count, contentList);
                        int afterLen = lastLine.widthList.get(lastLine.widthList.size() - 1);
                        drawedWidth += afterLen - preLen;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (drawedWidth > lineWidthMax) {
            lineWidthMax = drawedWidth;
        }
        if (contentList.size() <= 1) {
            oneLineWidth = (int) drawedWidth + leftPadding + rightPadding;
            height = lineHeight;
//            height = lineSpacing + lineHeight + lineSpacing;
        } else {
            height -= lineSpacing;
        }
        cacheData(isExpandable, maxLine, width, (int) height);
        return (int) height;
    }

    private float newLineHandle(float lineHeight, float height, StringBuilder sb, LINE lastLine,
                                float flagLength, int count, ArrayList<LINE> contentList) {
        //如果剩余的空间能够放atText的一个字符
        if (flagLength > paint.measureText(atText, 0, 1)) {
            while (flagLength > paint.measureText(atText, 0, count)) {
                count++;
                if (count > atText.length()) {
                    count = atText.length();
                    break;
                }
            }
            count--;
            if (lastLine.line.get(lastLine.line.size() - 1) instanceof String) {
                String str = (String) lastLine.line.get(lastLine.line.size() - 1);
                sb.append(str).append(atText.substring(0, count));
                lastLine.line.set(lastLine.line.size() - 1, sb.toString());
                lastLine.widthList.set(lastLine.widthList.size() - 1, (int) paint.measureText(sb.toString()));
                lastLine.array.put(str.length(), count);
            } else {
                String subStr = atText.substring(0, count);
                lastLine.line.add(subStr);
                lastLine.widthList.add((int) paint.measureText(subStr));
                lastLine.array.put(0, count);
            }
            height = addNewLine(lineHeight, height, atText.substring(count), contentList);
        } else {
            height = addNewLine(lineHeight, height, atText, contentList);
        }
        return height;
    }

    private float addNewLine(float lineHeight, float height, String text, ArrayList<LINE> contentList) {
        LINE newLine = new LINE();
        newLine.line.add(text);
        newLine.widthList.add((int) paint.measureText(text));
        newLine.height = (int) lineHeight;
        newLine.array.put(0, text.length());
        contentList.add(newLine);
        height += lineHeight + lineSpacing;
        return height;
    }

    public int getLineCounts() {
        return contentList.size();
    }

    /**
     * 获取缓存的测量数据，避免多次重复测量
     *
     * @param text
     * @param width
     * @return height
     */
    @SuppressWarnings("unchecked")
    private int getCachedData(String text, int width) {
        SoftReference<MeasuredData> cache = measuredData.get(text);
        if (cache == null) {
            return -1;
        }
        MeasuredData md = cache.get();
        if (md != null && md.textSize == this.getTextSize() && width == md.width) {
            lineWidthMax = md.lineWidthMax;
            contentList = (ArrayList<LINE>) md.contentList.clone();
            oneLineWidth = md.oneLineWidth;
            return md.measuredHeight;
        } else {
            return -1;
        }
    }

    /**
     * 缓存已测量的数据
     *
     * @param isExpandable
     * @param maxLine
     * @param width
     * @param height
     */
    @SuppressWarnings("unchecked")
    private void cacheData(boolean isExpandable, int maxLine, int width, int height) {
        MeasuredData md = new MeasuredData();
        md.contentList = (ArrayList<LINE>) contentList.clone();
        md.textSize = this.getTextSize();
        md.lineWidthMax = lineWidthMax;
        md.oneLineWidth = oneLineWidth;
        md.measuredHeight = height;
        md.width = width;
        md.hashIndex = ++hashIndex;
        SoftReference<MeasuredData> cache = new SoftReference<>(md);
        measuredData.put(text + (isExpandable ? ";getMaxLine=" + maxLine : ""), cache);
    }

    /**
     * 处理一行有两种颜色的文字的情况
     * isHeadStart : 是否只显示内容,不显示名字 true:只有内容,false含有名字
     */
    public void setMText(String cs, int whichline, int start, int end, int color,boolean isShowName) {
        this.whichline = whichline;
        this.start = start;
        this.end = end;
        this.color = color;
        this.isShowName = isShowName;
        setMText(cs, false);
    }

    /**
     * 处理一行有三种颜色的文字的情况，通常end和start2一样
     */
    public void setMText(String cs, int whichline, int start, int end, int color, int start2, int end2, int color2) {
        this.whichline = whichline;
        this.start = start;
        this.end = end;
        this.color = color;
        this.start2 = start2;
        this.end2 = end2;
        this.color2 = color2;
        setMText(cs, false);
    }

    public void setMText(String cs, int whichline, int start, int end, int color, String atText, int atTextColor) {
        this.whichline = whichline;
        this.start = start;
        this.end = end;
        this.color = color;
        this.atText = atText;
        this.atTextColor = atTextColor;
        setMText(cs, true);
    }

    /**
     * 显示@字符串
     *
     * @param text   整个字符串
     * @param atText 例如@Army
     */
    public void setMTextWithAT(String text, String atText, int atTextColor) {
        this.atText = atText;
        this.atTextColor = atTextColor;
        setMText(text, true);
    }

    public void setMTextWithAtCanClick(CharSequence text, String atText, int atTextColor) {

    }

    /**
     * 用本函数代替{@link #setText(CharSequence)}
     *
     * @param cs
     */
    public void setMText(String cs, boolean... isAtText) {
        if (isAtText == null || isAtText.length == 0 || !isAtText[0]) {
            atText = "";
        }

        text = SmileyParser.getInstance().replace(cs, this);
        useDefault = false;
        obList.clear();
        if (getCachedData(cs + atText + (isExpandable ? ";getMaxLine=" + getMaxLine() : ""), 0) < 0) {
            ArrayList<SpanObject> isList = new ArrayList<>();
            if (text instanceof SpannableString) {
                SpannableString ss = (SpannableString) text;
                CharacterStyle[] spans = ss.getSpans(0, ss.length(), CharacterStyle.class);
                for (int i = 0; i < spans.length; i++) {
                    int s = ss.getSpanStart(spans[i]);
                    int e = ss.getSpanEnd(spans[i]);
                    SpanObject iS = new SpanObject();
                    iS.span = spans[i];
                    iS.start = s;
                    iS.end = e;
                    iS.source = ss.subSequence(s, e);
                    isList.add(iS);
                }
            } else if (text instanceof SpannableStringBuilder) {
                SpannableStringBuilder ssb = (SpannableStringBuilder) text;
                CharacterStyle[] spans = ssb.getSpans(0, ssb.length(), CharacterStyle.class);
                for (int i = 0; i < spans.length; i++) {
                    int s = ssb.getSpanStart(spans[i]);
                    int e = ssb.getSpanEnd(spans[i]);
                    SpanObject spanObject = new SpanObject();
                    spanObject.span = spans[i];
                    spanObject.start = s;
                    spanObject.end = e;
                    spanObject.source = ssb.subSequence(s, e);
                    isList.add(spanObject);
                }
            }
            //对span进行排序，以免不同种类的span位置错乱
            SpanObject[] spanArray = new SpanObject[isList.size()];
            isList.toArray(spanArray);
            Arrays.sort(spanArray, 0, spanArray.length, new SpanObjectComparator());
            isList.clear();
            for (int i = 0; i < spanArray.length; i++) {
                isList.add(spanArray[i]);
            }
            String str = text.toString();

            for (int i = 0, j = 0; i < str.length(); ) {
                if (j < isList.size()) {
                    SpanObject is = isList.get(j);
                    if (i < is.start) {
                        Integer cp = str.codePointAt(i);
                        //支持增补字符
                        if (Character.isSupplementaryCodePoint(cp)) {
                            i += 2;
                        } else {
                            i++;
                        }
                        obList.add(new String(Character.toChars(cp)));
                    } else if (i >= is.start) {
                        obList.add(is);
                        j++;
                        i = is.end;
                    }
                } else {
                    Integer cp = str.codePointAt(i);
                    if (Character.isSupplementaryCodePoint(cp)) {
                        i += 2;
                    } else {
                        i++;
                    }
                    obList.add(new String(Character.toChars(cp)));
                }
            }
        }
        requestLayout();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (measuredData != null) {
            measuredData.clear();
        }
    }

    public void setUseDefault(boolean useDefault) {
        this.useDefault = useDefault;
        if (useDefault) {
            this.setText(text);
            this.setTextColor(getCurrentTextColor());
        }
    }

    public void setIsExpandable(boolean isExpandable) {
        this.isExpandable = isExpandable;
    }

    /**
     * 设置行距
     *
     * @param lineSpacingDP 行距，单位dp
     */
    public void setLineSpacingDP(float lineSpacingDP) {
        lineSpacing = dip2px(context, lineSpacingDP);
    }


    public int getMaxLine() {
        int mMaximum = -1;
        int mMaxMode = 1;
        try {
            Class clazz = Class.forName("android.widget.TextView");
            Field field = clazz.getDeclaredField("mMaximum");
            field.setAccessible(true);
            mMaximum = field.getInt(this);

            field = clazz.getDeclaredField("mMaxMode");
            field.setAccessible(true);
            mMaxMode = field.getInt(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mMaxMode == 1 ? mMaximum : -1;
    }

    @Override
    public int getMaxHeight() {
        int mMaximum = -1;
        int mMaxMode = 1;
        try {
            Class clazz = Class.forName("android.widget.TextView");
            Field field = clazz.getDeclaredField("mMaximum");
            field.setAccessible(true);
            mMaximum = field.getInt(this);

            field = clazz.getDeclaredField("mMaxMode");
            field.setAccessible(true);
            mMaxMode = field.getInt(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mMaxMode == 2 ? mMaximum : -1;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        rx = (int) event.getX();
        ry = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isShowName){
                    if (rx <= (start!=0?paint.measureText("回复"):0)+start1X + 20 && ry <= start1Y + 20) {
                        return true;
                    } else if (rx > start1X && rx <= start2X + start1X + paint.measureText("回复") + 20 && ry <= start2Y + 20) {
                        return true;
                    }
                }else{
                    return super.onTouchEvent(event);
                }
            case MotionEvent.ACTION_MOVE:
                return super.onTouchEvent(event);
            case MotionEvent.ACTION_UP:
                if (start != -1 && start2 == -1) {
                    if (start == 0 && start != -1){
                        if (rx <= (start!=0?paint.measureText("回复"):0)+start1X + 20 && ry <= start1Y + 20) {
                            if (!isShowName){
                                if (clickName != null){
                                    clickName.click1();
                                }
                            }else{
                                return super.onTouchEvent(event);
                            }
                        } else {
                            return super.onTouchEvent(event);
                        }
                    }else if(start == 2 && start != -1){
                        if (rx <= (start!=0?paint.measureText("回复"):0)+start1X + 20 && ry <= start1Y + 20) {
                            if (!isShowName){
                                if (clickName != null){
                                    clickName.click2();
                                }
                            }else{
                                return super.onTouchEvent(event);
                            }
                        } else {
                            return super.onTouchEvent(event);
                        }
                    }

                } else {
                    if (rx <= start1X + 20 && ry <= start1Y + 20) {
                        if (clickName != null){
                            clickName.click1();
                        }
                    } else if (rx > start1X && rx <= start2X + start1X + paint.measureText("回复") + 20 && ry <= start2Y + 20) {
                        if (clickName != null){
                            clickName.click2();
                        }
                    } else {
                        return super.onTouchEvent(event);
                    }
                }
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * @author huangwei
     * @功能: 存储Span对象及相关信息
     */
    class SpanObject {
        public Object span;
        public int start;
        public int end;
        public CharSequence source;
    }

    /**
     * @author huangwei
     * @功能: 对SpanObject进行排序
     */
    class SpanObjectComparator implements Comparator<SpanObject> {
        @Override
        public int compare(SpanObject lhs, SpanObject rhs) {

            return lhs.start - rhs.start;
        }

    }

    /**
     * @author huangwei
     * @功能: 存储测量好的一行数据
     */
    class LINE {
        public ArrayList<Object> line = new ArrayList<>();
        public ArrayList<Integer> widthList = new ArrayList<>();
        public int height;
        public SparseIntArray array = new SparseIntArray();

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("height:" + height + "   ");
            for (int i = 0; i < line.size(); i++) {
                sb.append(line.get(i)).append(":").append(widthList.get(i));
            }
            return sb.toString();
        }

    }

    /**
     * @author huangwei
     * @功能: 缓存的数据
     */
    class MeasuredData {
        public int measuredHeight;
        public float textSize;
        public int width;
        public float lineWidthMax;
        public int oneLineWidth;
        public int hashIndex;
        public ArrayList<LINE> contentList = new ArrayList<>();

    }

    public interface ClickName {
        void click1();

        void click2();
    }

}