package com.juziwl.uilibrary.media;

import android.os.Parcel;
import android.os.Parcelable;

import com.luck.picture.lib.entity.LocalMedia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地文件夹类
 */
public class LocalMediaFolderEntity implements Serializable {
    /**
     * Folder name
     */
    private String name;
    /**
     * Folder first path
     */
    private String firstImagePath;
    /**
     * Folder media num
     */
    private int imageNum;
    /**
     * If the selected num
     */
    private int checkedNum;
    /**
     * If the selected
     */
    private boolean isChecked;

    /**
     * type
     */
    private int ofAllType = -1;
    /**
     * Whether or not the camera
     */
    private boolean isCameraFolder;

    private List<LocalMedia> images = new ArrayList<LocalMedia>();

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public int getImageNum() {
        return imageNum;
    }

    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }

    public List<LocalMedia> getImages() {
        return images == null ? new ArrayList<>() : images;
    }

    public void setImages(List<LocalMedia> images) {
        this.images = images;
    }

    public int getCheckedNum() {
        return checkedNum;
    }

    public void setCheckedNum(int checkedNum) {
        this.checkedNum = checkedNum;
    }

    public int getOfAllType() {
        return ofAllType;
    }

    public void setOfAllType(int ofAllType) {
        this.ofAllType = ofAllType;
    }

    public boolean isCameraFolder() {
        return isCameraFolder;
    }

    public void setCameraFolder(boolean cameraFolder) {
        isCameraFolder = cameraFolder;
    }

    public LocalMediaFolderEntity() {
    }

}
