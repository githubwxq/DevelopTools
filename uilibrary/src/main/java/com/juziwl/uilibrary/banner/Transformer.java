package com.juziwl.uilibrary.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.juziwl.uilibrary.banner.transformer.AccordionTransformer;
import com.juziwl.uilibrary.banner.transformer.BackgroundToForegroundTransformer;
import com.juziwl.uilibrary.banner.transformer.CubeInTransformer;
import com.juziwl.uilibrary.banner.transformer.CubeOutTransformer;
import com.juziwl.uilibrary.banner.transformer.DefaultTransformer;
import com.juziwl.uilibrary.banner.transformer.DepthPageTransformer;
import com.juziwl.uilibrary.banner.transformer.FlipHorizontalTransformer;
import com.juziwl.uilibrary.banner.transformer.FlipVerticalTransformer;
import com.juziwl.uilibrary.banner.transformer.ForegroundToBackgroundTransformer;
import com.juziwl.uilibrary.banner.transformer.RotateDownTransformer;
import com.juziwl.uilibrary.banner.transformer.RotateUpTransformer;
import com.juziwl.uilibrary.banner.transformer.ScaleInOutTransformer;
import com.juziwl.uilibrary.banner.transformer.StackTransformer;
import com.juziwl.uilibrary.banner.transformer.TabletTransformer;
import com.juziwl.uilibrary.banner.transformer.ZoomInTransformer;
import com.juziwl.uilibrary.banner.transformer.ZoomOutSlideTransformer;
import com.juziwl.uilibrary.banner.transformer.ZoomOutTranformer;

/**
 * @author null
 * @modify Neil
 */
public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
