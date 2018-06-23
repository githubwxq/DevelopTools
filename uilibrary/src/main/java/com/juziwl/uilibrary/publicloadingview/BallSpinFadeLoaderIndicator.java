package com.juziwl.uilibrary.publicloadingview;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;



/**
 * Created by Jack on 2015/10/20.
 *
 * @author Jack
 * @modify Neil
 */
public class BallSpinFadeLoaderIndicator extends BaseIndicatorController {

    public static final float SCALE = 1.0f;
    public static final int ALPHA = 255;
    public static final int NUMBER_8 = 8;
    float[] scaleFloats = new float[]{SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE};

    int[] alphas = new int[]{ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA};

    private ValueAnimator[] valueAnimators = null;

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float radius = getWidth() / 10;
        for (int i = 0; i < NUMBER_8; i++) {
            canvas.save();
            Point point = circleAt(getWidth(), getHeight(), getWidth() / 2 - radius, i * (Math.PI / 4));
            canvas.translate(point.x, point.y);
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            paint.setAlpha(alphas[i]);
            canvas.drawCircle(0, 0, radius, paint);
            canvas.restore();
        }
    }

    /**
     * 圆O的圆心为(a,b),半径为R,点A与到X轴的为角α.
     * 则点A的坐标为(a+R*cosα,b+R*sinα)
     *
     * @param width
     * @param height
     * @param radius
     * @param angle
     * @return
     */
    Point circleAt(int width, int height, float radius, double angle) {
        float x = (float) (width / 2 + radius * (Math.cos(angle)));
        float y = (float) (height / 2 + radius * (Math.sin(angle)));
        return new Point(x, y);
    }

    @Override
    public void createAnimation() {
        int[] delays = {0, 120, 240, 360, 480, 600, 720, 780, 840};
        valueAnimators = new ValueAnimator[NUMBER_8 * 2];
        for (int i = 0; i < NUMBER_8; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.4f, 1);
            scaleAnim.setDuration(1000);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.addUpdateListener(animation -> {
                scaleFloats[index] = (float) animation.getAnimatedValue();
                postInvalidate();
            });
            scaleAnim.start();
            valueAnimators[i * 2] = scaleAnim;

            ValueAnimator alphaAnim = ValueAnimator.ofInt(255, 77, 255);
            alphaAnim.setDuration(1000);
            alphaAnim.setRepeatCount(-1);
            alphaAnim.setStartDelay(delays[i]);
            alphaAnim.addUpdateListener(animation -> {
                alphas[index] = (int) animation.getAnimatedValue();
                postInvalidate();
            });
            alphaAnim.start();
            valueAnimators[i * 2 + 1] = scaleAnim;
        }
    }

    @Override
    public void destory() {
        if (valueAnimators != null) {
            for (ValueAnimator valueAnimator : valueAnimators) {
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    valueAnimator.removeAllUpdateListeners();
                }
            }
        }
    }

    final class Point {
        public float x;
        public float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }


}
