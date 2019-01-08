package com.luck.picture.lib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.lib
 * describe：for PictureSelector's media selection.
 * email：893855882@qq.com
 * data：2017/5/24
 */

public final class PictureSelector {

    private final WeakReference<Activity> mActivity;
    private final WeakReference<Fragment> mFragment;

    private PictureSelector(Activity activity) {
        this(activity, null);
    }

    private PictureSelector(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private PictureSelector(Activity activity, Fragment fragment) {
        mActivity = new WeakReference<>(activity);
        mFragment = new WeakReference<>(fragment);
    }

    /**
     * Start PictureSelector for Activity.
     *
     * @param activity
     * @return PictureSelector instance.
     */
    public static PictureSelector create(Activity activity) {
        return new PictureSelector(activity);
    }

    /**
     * Start PictureSelector for Fragment.
     *
     * @param fragment
     * @return PictureSelector instance.
     */
    public static PictureSelector create(Fragment fragment) {
        return new PictureSelector(fragment);
    }

    /**
     * @param mimeType Select the type of picture you want，all or Picture or Video .
     * @return LocalMedia PictureSelectionModel
     */
    public PictureSelectionModel openGallery(int mimeType) {
        return new PictureSelectionModel(this, mimeType);
    }

    /**
     * @param mimeType Select the type of picture you want，Picture or Video.
     * @return LocalMedia PictureSelectionModel
     */
    public PictureSelectionModel openCamera(int mimeType) {
        return new PictureSelectionModel(this, mimeType, true);
    }

    public PictureSelectionModel openPreview() {
        return new PictureSelectionModel(this, PictureMimeType.ofImage(), false);
    }

    /**
     * @param data
     * @return Selector Multiple LocalMedia
     */
    public static List<LocalMedia> obtainMultipleResult(Intent data) {
        List<LocalMedia> result = new ArrayList<>();
        if (data != null) {
            result = (List<LocalMedia>) data.getSerializableExtra(PictureConfig.EXTRA_RESULT_SELECTION);
            if (result == null) {
                result = new ArrayList<>();
            }
            return result;
        }
        return result;
    }

    /**
     * @param data
     * @return Put image Intent Data
     */
    public static Intent putIntentResult(List<LocalMedia> data) {
        return new Intent().putExtra(PictureConfig.EXTRA_RESULT_SELECTION, (Serializable) data);
    }

    /**
     * @param bundle
     * @return get Selector  LocalMedia
     */
    public static List<LocalMedia> obtainSelectorList(Bundle bundle) {
        List<LocalMedia> selectionMedias;
        if (bundle != null) {
            selectionMedias = (List<LocalMedia>) bundle
                    .getSerializable(PictureConfig.EXTRA_SELECT_LIST);
            return selectionMedias;
        }
        selectionMedias = new ArrayList<>();
        return selectionMedias;
    }

    /**
     * @param selectedImages
     * @return put Selector  LocalMedia
     */
    public static void saveSelectorList(Bundle outState, List<LocalMedia> selectedImages) {
        outState.putSerializable(PictureConfig.EXTRA_SELECT_LIST, (Serializable) selectedImages);
    }

    /**
     * @return Activity.
     */
    @Nullable
    Activity getActivity() {
        return mActivity.get();
    }

    /**
     * @return Fragment.
     */
    @Nullable
    Fragment getFragment() {
        return mFragment != null ? mFragment.get() : null;
    }

}
