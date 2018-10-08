package com.juziwl.uilibrary.progressbar;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import com.juziwl.uilibrary.R;
import com.juziwl.uilibrary.utils.ConvertUtils;
import com.juziwl.uilibrary.utils.ScreenUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
/**
 * 创建日期：2018/9/4
 * 描述:
 *
 * @author: zhaoh
 */
public class MyProgressBar extends View {
    private Paint paint = null;
    private Bitmap greenBitmap = null;
    private Bitmap centerBitmap = null, moveBitmap = null;
    private int greenBitmapWith, greenBitmapHeight, centerBitmapWith, centerBitmapHeight, moveBitmapWith, moveBitmapHeight;
    private Rect srcRect, desRect, cSrcRect, cDesRect;

    private int screenWith = 0, halfScreenWihth = 0, wGreenWith = 0, wGreenHeight = 0, viewHeight = 0,
            wCenterWith = 0, wCenterHeight = 0, wMoveWith = 0, wMoveHeight = 0;
    private int textSize = ConvertUtils.sp2px(16,getContext()), halfTextSize = ConvertUtils.sp2px(16,getContext());
    private int marginTop = ConvertUtils.dp2px(20,getContext()), imageLeft = -180, textWith = 0, textHeight = 0;
    private String mContext = "", text = "";

    private ValueAnimator valueAnimator = null;

    public MyProgressBar(Context context) {
        this(context, null);
    }

    public MyProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        screenWith = ScreenUtils.getScreenWidth(getContext());
        halfScreenWihth = screenWith / 2;
//        BitmapFactory.Options bfoOptions = new BitmapFactory.Options();
//        bfoOptions.inScaled = false;
        greenBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_progress_bttom);
        centerBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_progress_center);
        moveBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_progress_move);
        greenBitmapWith = greenBitmap.getWidth();
        greenBitmapHeight = greenBitmap.getHeight(); //61
        centerBitmapWith = centerBitmap.getWidth();
        centerBitmapHeight = centerBitmap.getHeight(); //44
        moveBitmapWith = moveBitmap.getWidth();
        moveBitmapHeight = moveBitmap.getHeight();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(halfScreenWihth - wGreenWith / 2, 0);
        canvas.drawBitmap(greenBitmap, srcRect, desRect, null);
        canvas.translate((wGreenWith - wCenterWith) / 2, (wGreenHeight - wCenterHeight) / 2);
        canvas.drawBitmap(centerBitmap, cSrcRect, cDesRect, null);
        int sc = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        drawDst(canvas);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        drawSrc(canvas);
        //还原
        paint.setXfermode(null);
        canvas.restoreToCount(sc);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.color_609F18));
        paint.setTextSize(textSize);
        text = "加载中···" + mContext + "%";
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        textWith = rect.width();
        textHeight = rect.height();
        int dx = centerBitmapWith / 2 - textWith / 2;
        int dh = wGreenHeight + textHeight + marginTop;
        canvas.translate(dx, dh);
        canvas.drawText(text, 0, 0, paint);
        Log.e("log","w==========" + textWith + ";textHeight==" + textHeight + ";with==" + dx + ";height==" + wGreenHeight + ";dh==" + dh);
    }

    private void drawSrc(Canvas canvas) {
        paint.setFilterBitmap(false);
        canvas.drawBitmap(moveBitmap, imageLeft, 0, paint);
    }

    private void drawDst(Canvas canvas) {
        int bitmapWidth = wMoveWith + imageLeft;
        if (bitmapWidth == 0) {
            return;
        }
        Bitmap bitmap = getSrcBitmap(bitmapWidth, moveBitmapHeight);
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    private Bitmap getSrcBitmap(int bitmapWidth, int moveBitmapHeight) {
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, moveBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        int radius = wMoveHeight / 2;
        RectF rectF = new RectF(0, 0, bitmapWidth, wMoveHeight);
        paint1.setColor(Color.BLACK);
        canvas.drawRoundRect(rectF, radius, radius, paint1);
        return bitmap;
    }


    public void init() {
        valueAnimator = ValueAnimator.ofInt(-moveBitmapWith, 0);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                imageLeft = (int) animation.getAnimatedValue();
                int abs = Math.abs(imageLeft);
                int max = Math.abs(moveBitmapWith);
                float f = (max - abs) * 1.0f / max;
                String end = dealwithDown(Float.toString(f));
                float v = Float.parseFloat(end) * 100;
                String need = String.valueOf(v);
                String[] split = need.split("\\.");
                mContext = split[0];
                Log.e("log","abs==" + abs + ";max==" + max + ";(max-abs)==" + (max - abs) + ";f==" + f + ";end==" + end + ";v==" + v + ";need==" + need + ";split[0]==" + split[0]);
                postInvalidate();
            }
        });
        valueAnimator.start();

    }


    public static String dealwithDown(String str) {
        BigDecimal bd = new BigDecimal(Float.parseFloat(str));
        bd = bd.setScale(2, RoundingMode.HALF_DOWN);
        String need = String.valueOf(bd);
        return need;
    }

    public void reset() {
        if (valueAnimator != null) {
            valueAnimator.cancel();
            valueAnimator = null;
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        srcRect = new Rect(0, 0, greenBitmapWith, greenBitmapHeight);
        desRect = new Rect(0, 0, greenBitmapWith, greenBitmapHeight);
        cSrcRect = new Rect(0, 0, centerBitmapWith, centerBitmapHeight);
        cDesRect = new Rect(0, 0, centerBitmapWith, centerBitmapHeight);
        wGreenWith = greenBitmapWith;
        wGreenHeight = greenBitmapHeight;
        viewHeight = h;
        wCenterWith = centerBitmapWith;
        wCenterHeight = centerBitmapHeight;
        wMoveWith = moveBitmapWith;
        wMoveHeight = moveBitmapHeight;
        Log.e("log","w===" + w + ";h===" + h + ";oldW==" + oldw + ";oldH==" + oldh + ";greenWith==" +
                greenBitmapWith + ";greenHeight==" + greenBitmapHeight + ";centerWiht==" + centerBitmapWith + ";centerHeight=="
                + centerBitmapHeight + ";moveWith==" + wMoveWith + ";wMoveHeight==" + wMoveHeight);
    }
}
