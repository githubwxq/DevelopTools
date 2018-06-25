/*
 * Copyright (C) 2016 venshine.cn@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wxq.commonlibrary.util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

/**
 * 动画效果，包含alpha, translation, rotation, scale.
 *
 * @author venshine
 */
public class AnimatorUtils {

    public static final float ALPHA_MIN = 0.0f;
    public static final float ALPHA_MAX = 1.0f;

    /**
     * alpha
     *
     * @param v
     * @param fromAlpha
     * @param toAlpha
     * @param duration
     */
    public static void alpha(View v, float fromAlpha, float toAlpha, int duration) {
        alpha(v, fromAlpha, toAlpha, duration, null);
    }

    /**
     * alpha
     *
     * @param v
     * @param fromAlpha
     * @param toAlpha
     * @param duration
     * @param animatorListener
     */
    public static void alpha(View v, float fromAlpha, float toAlpha, int duration, Animator.AnimatorListener
            animatorListener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.ALPHA, fromAlpha, toAlpha);
        animator.setDuration(duration);
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.start();
    }

    /**
     * translation x
     *
     * @param v
     * @param fromX
     * @param toX
     * @param duration
     */
    public static void translationX(View v, float fromX, float toX, int duration) {
        translationX(v, fromX, toX, duration, null);
    }

    /**
     * translation x
     *
     * @param v
     * @param fromX
     * @param toX
     * @param duration
     * @param animatorListener
     */
    public static void translationX(View v, float fromX, float toX, int duration, Animator.AnimatorListener
            animatorListener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.TRANSLATION_X, fromX, toX);
        animator.setDuration(duration);
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.start();
    }

    /**
     * translation y
     *
     * @param v
     * @param fromY
     * @param toY
     * @param duration
     */
    public static void translationY(View v, float fromY, float toY, int duration) {
        translationY(v, fromY, toY, duration, null);
    }

    /**
     * translation y
     *
     * @param v
     * @param fromY
     * @param toY
     * @param duration
     * @param animatorListener
     */
    public static void translationY(View v, float fromY, float toY, int duration, Animator.AnimatorListener
            animatorListener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.TRANSLATION_Y, fromY, toY);
        animator.setDuration(duration);
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.start();
    }

    /**
     * rotation x
     *
     * @param v
     * @param fromX
     * @param toX
     * @param duration
     */
    public static void rotationX(View v, float fromX, float toX, int duration) {
        rotationX(v, fromX, toX, duration, null);
    }

    /**
     * rotation x
     *
     * @param v
     * @param fromX
     * @param toX
     * @param duration
     * @param animatorListener
     */
    public static void rotationX(View v, float fromX, float toX, int duration, Animator.AnimatorListener
            animatorListener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.ROTATION_X, fromX, toX);
        animator.setDuration(duration);
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.start();
    }

    /**
     * rotation y
     *
     * @param v
     * @param fromY
     * @param toY
     * @param duration
     */
    public static void rotationY(View v, float fromY, float toY, int duration) {
        rotationY(v, fromY, toY, duration, null);
    }

    /**
     * rotation y
     *
     * @param v
     * @param fromY
     * @param toY
     * @param duration
     * @param animatorListener
     */
    public static void rotationY(View v, float fromY, float toY, int duration, Animator.AnimatorListener
            animatorListener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.ROTATION_Y, fromY, toY);
        animator.setDuration(duration);
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.start();
    }

    /**
     * scale x
     *
     * @param v
     * @param fromX
     * @param toX
     * @param duration
     */
    public static void scaleX(View v, float fromX, float toX, int duration) {
        scaleX(v, fromX, toX, duration, null);
    }

    /**
     * scale x
     *
     * @param v
     * @param fromX
     * @param toX
     * @param duration
     * @param animatorListener
     */
    public static void scaleX(View v, float fromX, float toX, int duration, Animator.AnimatorListener
            animatorListener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.SCALE_X, fromX, toX);
        animator.setDuration(duration);
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.start();
    }

    /**
     * scale y
     *
     * @param v
     * @param fromY
     * @param toY
     * @param duration
     */
    public static void scaleY(View v, float fromY, float toY, int duration) {
        scaleY(v, fromY, toY, duration, null);
    }

    /**
     * scale y
     *
     * @param v
     * @param fromY
     * @param toY
     * @param duration
     * @param animatorListener
     */
    public static void scaleY(View v, float fromY, float toY, int duration, Animator.AnimatorListener
            animatorListener) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.SCALE_Y, fromY, toY);
        animator.setDuration(duration);
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.start();
    }

    /**
     * shake x
     *
     * @param v
     */
    public static void shakeX(View v) {
        shakeX(v, 10, 1000, 5.0f);
    }

    /**
     * shake x
     *
     * @param v
     * @param offset
     * @param duration
     * @param times
     */
    public static void shakeX(View v, float offset, long duration, float times) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.TRANSLATION_X, 0, offset);
        animator.setDuration(duration);
        animator.setInterpolator(new CycleInterpolator(5.0f));
        animator.start();
    }

    /**
     * shake y
     *
     * @param v
     */
    public static void shakeY(View v) {
        shakeY(v, 10, 1000, 5.0f);
    }

    /**
     * shake y
     *
     * @param v
     * @param offset
     * @param duration
     * @param times
     */
    public static void shakeY(View v, float offset, long duration, float times) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.TRANSLATION_Y, 0, offset);
        animator.setDuration(duration);
        animator.setInterpolator(new CycleInterpolator(times));
        animator.start();
    }

    /**
     * 呼吸灯效果
     *
     * @param v
     */
    public static void breath(View v) {
        breath(v, 0.0f, 1.0f, 1000);
    }

    /**
     * 呼吸灯效果
     *
     * @param v
     * @param fromRange
     * @param toRange
     * @param duration
     */
    public static void breath(View v, float fromRange, float toRange, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.ALPHA, fromRange, toRange);
        animator.setDuration(duration);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }



    // 非属性动画


    /**
     * 默认动画持续时间
     */
    public static final long DEFAULT_ANIMATION_DURATION = 400;


    /**
     * 获取一个旋转动画
     *
     * @param fromDegrees       开始角度
     * @param toDegrees         结束角度
     * @param pivotXType        旋转中心点X轴坐标相对类型
     * @param pivotXValue       旋转中心点X轴坐标
     * @param pivotYType        旋转中心点Y轴坐标相对类型
     * @param pivotYValue       旋转中心点Y轴坐标
     * @param durationMillis    持续时间
     * @param animationListener 动画监听器
     * @return 一个旋转动画
     */
    public static RotateAnimation getRotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue, long durationMillis, Animation.AnimationListener animationListener) {
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees,
                toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
        rotateAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            rotateAnimation.setAnimationListener(animationListener);
        }
        return rotateAnimation;
    }


    /**
     * 获取一个根据视图自身中心点旋转的动画
     *
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个根据中心点旋转的动画
     */
    public static RotateAnimation getRotateAnimationByCenter(long durationMillis, Animation.AnimationListener animationListener) {
        return getRotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, durationMillis,
                animationListener);
    }


    /**
     * 获取一个根据中心点旋转的动画
     *
     * @param duration 动画持续时间
     * @return 一个根据中心点旋转的动画
     */
    public static RotateAnimation getRotateAnimationByCenter(long duration) {
        return getRotateAnimationByCenter(duration, null);
    }


    /**
     * 获取一个根据视图自身中心点旋转的动画
     *
     * @param animationListener 动画监听器
     * @return 一个根据中心点旋转的动画
     */
    public static RotateAnimation getRotateAnimationByCenter(Animation.AnimationListener animationListener) {
        return getRotateAnimationByCenter(DEFAULT_ANIMATION_DURATION,
                animationListener);
    }


    /**
     * 获取一个根据中心点旋转的动画
     *
     * @return 一个根据中心点旋转的动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static RotateAnimation getRotateAnimationByCenter() {
        return getRotateAnimationByCenter(DEFAULT_ANIMATION_DURATION, null);
    }


    /**
     * 获取一个透明度渐变动画
     *
     * @param fromAlpha         开始时的透明度
     * @param toAlpha           结束时的透明度都
     * @param durationMillis    持续时间
     * @param animationListener 动画监听器
     * @return 一个透明度渐变动画
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, long durationMillis, Animation.AnimationListener animationListener) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            alphaAnimation.setAnimationListener(animationListener);
        }
        return alphaAnimation;
    }


    /**
     * 获取一个透明度渐变动画
     *
     * @param fromAlpha      开始时的透明度
     * @param toAlpha        结束时的透明度都
     * @param durationMillis 持续时间
     * @return 一个透明度渐变动画
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, long durationMillis) {
        return getAlphaAnimation(fromAlpha, toAlpha, durationMillis, null);
    }


    /**
     * 获取一个透明度渐变动画
     *
     * @param fromAlpha         开始时的透明度
     * @param toAlpha           结束时的透明度都
     * @param animationListener 动画监听器
     * @return 一个透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, Animation.AnimationListener animationListener) {
        return getAlphaAnimation(fromAlpha, toAlpha, DEFAULT_ANIMATION_DURATION,
                animationListener);
    }


    /**
     * 获取一个透明度渐变动画
     *
     * @param fromAlpha 开始时的透明度
     * @param toAlpha   结束时的透明度都
     * @return 一个透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha) {
        return getAlphaAnimation(fromAlpha, toAlpha, DEFAULT_ANIMATION_DURATION,
                null);
    }


    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     *
     * @param durationMillis    持续时间
     * @param animationListener 动画监听器
     * @return 一个由完全显示变为不可见的透明度渐变动画
     */
    public static AlphaAnimation getHiddenAlphaAnimation(long durationMillis, Animation.AnimationListener animationListener) {
        return getAlphaAnimation(1.0f, 0.0f, durationMillis, animationListener);
    }


    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     *
     * @param durationMillis 持续时间
     * @return 一个由完全显示变为不可见的透明度渐变动画
     */
    public static AlphaAnimation getHiddenAlphaAnimation(long durationMillis) {
        return getHiddenAlphaAnimation(durationMillis, null);
    }


    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     *
     * @param animationListener 动画监听器
     * @return 一个由完全显示变为不可见的透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getHiddenAlphaAnimation(Animation.AnimationListener animationListener) {
        return getHiddenAlphaAnimation(DEFAULT_ANIMATION_DURATION,
                animationListener);
    }


    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     *
     * @return 一个由完全显示变为不可见的透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getHiddenAlphaAnimation() {
        return getHiddenAlphaAnimation(DEFAULT_ANIMATION_DURATION, null);
    }


    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     *
     * @param durationMillis    持续时间
     * @param animationListener 动画监听器
     * @return 一个由不可见变为完全显示的透明度渐变动画
     */
    public static AlphaAnimation getShowAlphaAnimation(long durationMillis, Animation.AnimationListener animationListener) {
        return getAlphaAnimation(0.0f, 1.0f, durationMillis, animationListener);
    }


    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     *
     * @param durationMillis 持续时间
     * @return 一个由不可见变为完全显示的透明度渐变动画
     */
    public static AlphaAnimation getShowAlphaAnimation(long durationMillis) {
        return getAlphaAnimation(0.0f, 1.0f, durationMillis, null);
    }


    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     *
     * @param animationListener 动画监听器
     * @return 一个由不可见变为完全显示的透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getShowAlphaAnimation(Animation.AnimationListener animationListener) {
        return getAlphaAnimation(0.0f, 1.0f, DEFAULT_ANIMATION_DURATION,
                animationListener);
    }


    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     *
     * @return 一个由不可见变为完全显示的透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getShowAlphaAnimation() {
        return getAlphaAnimation(0.0f, 1.0f, DEFAULT_ANIMATION_DURATION, null);
    }


    /**
     * 获取一个缩小动画
     *
     * @param durationMillis   时间
     * @param animationListener  监听
     * @return 一个缩小动画
     */
    public static ScaleAnimation getLessenScaleAnimation(long durationMillis, Animation.AnimationListener animationListener) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f,
                0.0f, ScaleAnimation.RELATIVE_TO_SELF,
                ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(durationMillis);
        scaleAnimation.setAnimationListener(animationListener);

        return scaleAnimation;
    }


    /**
     * 获取一个缩小动画
     *
     * @param durationMillis 时间
     * @return 一个缩小动画
     */
    public static ScaleAnimation getLessenScaleAnimation(long durationMillis) {
        return getLessenScaleAnimation(durationMillis, null);

    }


    /**
     * 获取一个缩小动画
     *
     * @param animationListener  监听
     * @return 返回一个缩小的动画
     */
    public static ScaleAnimation getLessenScaleAnimation(Animation.AnimationListener animationListener) {
        return getLessenScaleAnimation(DEFAULT_ANIMATION_DURATION,
                animationListener);

    }


    /**
     * 获取一个放大动画
     * @param durationMillis   时间
     * @param animationListener  监听
     *
     * @return 返回一个放大的效果
     */
    public static ScaleAnimation getAmplificationAnimation(long durationMillis, Animation.AnimationListener animationListener) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f,
                1.0f, ScaleAnimation.RELATIVE_TO_SELF,
                ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(durationMillis);
        scaleAnimation.setAnimationListener(animationListener);
        return scaleAnimation;
    }


    /**
     * 获取一个放大动画
     *
     * @param durationMillis   时间
     *
     * @return 返回一个放大的效果
     */
    public static ScaleAnimation getAmplificationAnimation(long durationMillis) {
        return getAmplificationAnimation(durationMillis, null);

    }


    /**
     * 获取一个放大动画
     *
     * @param animationListener  监听
     * @return 返回一个放大的效果
     */
    public static ScaleAnimation getAmplificationAnimation(Animation.AnimationListener animationListener) {
        return getAmplificationAnimation(DEFAULT_ANIMATION_DURATION,
                animationListener);

    }
}
