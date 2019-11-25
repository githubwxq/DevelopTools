package com.juziwl.uilibrary.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;


import com.juziwl.uilibrary.R;


/**
 * http://blog.csdn.net/lmj623565791/article/details/41967509
 *
 * @author zhy
 * @modify Neil
 */
public class RectImageView extends AppCompatImageView {
    private Region mAreaRegion;
    /**
     * 图片的类型
     * 0 圆形
     * 1 矩形
     */
    private int type;
    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_ROUND = 1;
    /**
     * top-left, top-right, bottom-right, bottom-left
     */
    private float[] radii = new float[8];
    /**
     * 圆角大小的默认值
     */
    private static final int BODER_RADIUS_DEFAULT = 0;
    /**
     * 外边是否添加边框的默认值。
     */
    private static final boolean ISNEEDBORDER = false;
    /**
     * 边框默认颜色
     * 边框默认宽度
     */
    private static final int BODER_COLOR_DEFAULT = Color.TRANSPARENT;
    private static final int BODER_WIDTH_DEFAULT = 0;
    /**
     * 圆角的大小
     */
    private int mBorderRadius;
    /**
     * 是否需要添加外边的边框
     */
    private boolean mIsNeddBorder = ISNEEDBORDER;
    /**
     * ImageView边框有关的属性
     * mBorderColor  边框颜色
     * mBorderWith   边框宽度
     */
    private int mBorderColor = BODER_COLOR_DEFAULT;
    private int mBorderWith = BODER_WIDTH_DEFAULT;
    private boolean diffBodder = false;
    /**
     * 绘图的Paint
     */
    private Paint mBitmapPaint;
    private Paint mBorderPaint;
    /**
     * 圆角的半径
     */
    private int mRadius;
    /**
     * 3x3 矩阵，主要用于缩小放大
     */
    private Matrix mMatrix;
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private BitmapShader mBitmapShader;
    /**
     * view的宽度
     */

    private int mWidth;
    private RectF mRoundRect;
    /**
     * 剪裁区域
     */
    private Path mClipPath;

    private static final String STATE_INSTANCE = "state_instance";
    private static final String STATE_TYPE = "state_type";
    private static final String STATE_BORDER_RADIUS = "state_border_radius";

    public RectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRectImageView(context, attrs);
    }

    public RectImageView(Context context) {
        this(context, null);
    }

    /**
     * 设置圆角大小
     *
     * @param borderRadius 圆角dp值
     */
    public void setBorderRadius(int borderRadius) {
        int pxVal = dp2px(borderRadius);
        if (this.mBorderRadius != pxVal) {
            this.mBorderRadius = pxVal;
            invalidate();
        }
    }

    /**
     * 设置图片类型
     * 如果类型不在范围内默认设置为圆形
     *
     * @param type 图片类型
     */
    public void setType(int type) {
        if (this.type != type) {
            this.type = type;
            if (this.type != TYPE_ROUND && this.type != TYPE_CIRCLE) {
                this.type = TYPE_CIRCLE;
            }
            requestLayout();
        }
    }

    /**
     * dp转px
     *
     * @param dpVal dp值
     * @return 像素值
     */
    public int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 如果类型是圆形，则强制改变view的宽高一致，以小值为准
         */
//        if (type == TYPE_CIRCLE) {
//            mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
////            Log.e("getMeasuredWidth==",getMeasuredWidth()+"");
////            Log.e("getMeasuredHeight==",getMeasuredHeight()+"");
////            Log.e("mWidth====",mWidth+"");
//            mRadius = mWidth / 2;
//            setMeasuredDimension(mWidth, mWidth);
//        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }
        setUpShader();
        if (type == TYPE_ROUND) {
            mBitmapPaint.setColor(Color.WHITE);
            if (diffBodder) {
                canvas.drawPath(mClipPath, mBitmapPaint);
            } else {
                if (mRoundRect == null) {
                    mRoundRect = new RectF(0, 0, getWidth(), getHeight());
                }
                canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius, mBitmapPaint);
            }
        } else {
            if (mIsNeddBorder) {
                mBorderPaint.setColor(mBorderColor);
                mBorderPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawCircle(mRadius, mRadius, mRadius, mBorderPaint);
                canvas.drawCircle(mRadius, mRadius, mRadius - mBorderWith, mBitmapPaint);
            } else {
                canvas.drawCircle(mRadius, mRadius, mRadius, mBitmapPaint);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 圆角图片的范围
        mClipPath.reset();
        if (type == TYPE_ROUND && mRoundRect == null) {
            mRoundRect = new RectF(0, 0, w, h);
            if (diffBodder) {
                mClipPath.addRoundRect(mRoundRect, radii, Path.Direction.CW);
            }
        } else if (type == TYPE_CIRCLE && mWidth == 0) {
            mWidth = w;
            mRadius = w / 2;
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(STATE_TYPE, type);
        bundle.putInt(STATE_BORDER_RADIUS, mBorderRadius);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(((Bundle) state)
                    .getParcelable(STATE_INSTANCE));
            this.type = bundle.getInt(STATE_TYPE);
            this.mBorderRadius = bundle.getInt(STATE_BORDER_RADIUS);
        } else {
            super.onRestoreInstanceState(state);
        }

    }

    /**
     * 初始化RectImageView
     */
    private void initRectImageView(Context context, AttributeSet attrs) {
        mMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RectImageView);
        // 默认为10dp
        mBorderRadius = a.getDimensionPixelSize(
                R.styleable.RectImageView_borderRadius, (int) TypedValue
                        .applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                BODER_RADIUS_DEFAULT, getResources()
                                        .getDisplayMetrics()));
        mIsNeddBorder = a.getBoolean(R.styleable.RectImageView_needBorder, ISNEEDBORDER);
        mBorderWith = a.getDimensionPixelSize(R.styleable.RectImageView_borderWith, BODER_WIDTH_DEFAULT);
        mBorderColor = a.getColor(R.styleable.RectImageView_borderColor, BODER_COLOR_DEFAULT);
        diffBodder = a.getBoolean(R.styleable.RectImageView_diffBorder, diffBodder);
        // 默认为Round
        type = a.getInt(R.styleable.RectImageView_type, TYPE_ROUND);
        int roundCorner = a.getDimensionPixelSize(R.styleable.RectImageView_borderRadius, 0);
        int roundCornerTopLeft = a.getDimensionPixelSize(
                R.styleable.RectImageView_round_corner_top_left, roundCorner);
        int roundCornerTopRight = a.getDimensionPixelSize(
                R.styleable.RectImageView_round_corner_top_right, roundCorner);
        int roundCornerBottomLeft = a.getDimensionPixelSize(
                R.styleable.RectImageView_round_corner_bottom_left, roundCorner);
        int roundCornerBottomRight = a.getDimensionPixelSize(
                R.styleable.RectImageView_round_corner_bottom_right, roundCorner);
        radii[0] = roundCornerTopLeft;
        radii[1] = roundCornerTopLeft;
        radii[2] = roundCornerTopRight;
        radii[3] = roundCornerTopRight;
        radii[4] = roundCornerBottomRight;
        radii[5] = roundCornerBottomRight;
        radii[6] = roundCornerBottomLeft;
        radii[7] = roundCornerBottomLeft;
        mClipPath = new Path();
        a.recycle();
    }

    /**
     * 初始化BitmapShader
     */
    private void setUpShader() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        Bitmap bmp;
        if (drawable instanceof ColorDrawable) {
            bmp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bmp);
            canvas.drawColor(((ColorDrawable) drawable).getColor());
        } else {
            bmp = drawable2Bitmap(drawable);
        }
        // 将bmp作为着色器，就是在指定区域内绘制bmp
        try {
            mBitmapShader = new BitmapShader(bmp, TileMode.CLAMP, TileMode.CLAMP);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        float scale = 1.0f;
        if (type == TYPE_CIRCLE) {
            // 拿到bitmap宽或高的小值
            int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
            scale = mWidth * 1.0f / bSize;
            // shader的变换矩阵，我们这里主要用于放大或者缩小
            mMatrix.setScale(scale, scale);
        } else if (type == TYPE_ROUND) {
            if (!(bmp.getWidth() == getWidth() && bmp.getHeight() == getHeight())) {
                // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
                scale = Math.max(getWidth() * 1.0f / bmp.getWidth(), getHeight() * 1.0f / bmp.getHeight());

                float scaleWidth = scale * bmp.getWidth();
                float scaleHeight = scale * bmp.getHeight();

                float dx = 0, dy = 0;
                if (scaleWidth > getWidth()) {
                    dx = (scaleWidth - getWidth()) / 2;
                }
                if (scaleHeight > getHeight()) {
                    dy = (scaleHeight - getHeight()) / 2;
                }
                // shader的变换矩阵，我们这里主要用于放大或者缩小
                mMatrix.setScale(scale, scale);
                mMatrix.postTranslate(-dx, -dy);
            } else {
                mMatrix.setScale(scale, scale);
            }
        }
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(mMatrix);
        // 设置shader
        mBitmapPaint.setShader(mBitmapShader);
    }


    /**
     * Drawable to bitmap.
     *
     * @param drawable The drawable.
     * @return bitmap
     */
    public static Bitmap drawable2Bitmap(final Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE
                            ? Bitmap.Config.ARGB_8888
                            : Bitmap.Config.RGB_565);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}