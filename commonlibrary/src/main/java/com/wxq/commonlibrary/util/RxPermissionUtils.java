//package com.wxq.commonlibrary.util;
//
//import android.Manifest;
//import android.app.Activity;
//
//import com.tbruyelle.rxpermissions2.RxPermissions;
//
//import java.util.function.Consumer;
//
//
///**
// * @author Army
// * @version V_1.0.0
// * @date 2018/4/3
// * @description
// */
//public class RxPermissionUtils {
//
//    public static void requestExternalPermission(Activity activity, RxPermissions rxPermissions, Consumer<Object> consumer) {
//        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .subscribe(permission -> {
//                    if (permission.granted) {
//                        if (consumer != null) {
//                            consumer.accept(0);
//                        }
//                    } else {
////                        CommonDialog.getInstance().showPermissionDialog(activity,
////                                activity.getString(R.string.open_external_storage));
//                    }
//                });
//    }
//
//    public static void requestPhotoPermission(Activity activity, RxPermissions rxPermissions, Consumer<Object> consumer) {
//        rxPermissions.requestEach(Manifest.permission.CAMERA)
//                .subscribe(permission -> {
//                    if (permission.granted) {
//                        if (consumer != null) {
//                            consumer.accept(0);
//                        }
//                    } else {
////                        CommonDialog.getInstance().showPermissionDialog(activity, activity.getString(R.string.open_camera));
//                    }
//                });
//    }
//
//    public static void requestAudioPermission(Activity activity, RxPermissions rxPermissions, Consumer<Object> consumer) {
//        rxPermissions.requestEach(Manifest.permission.RECORD_AUDIO)
//                .subscribe(permission -> {
//                    if (permission.granted) {
//                        if (consumer != null) {
//                            consumer.accept(0);
//                        }
//                    } else {
//                        CommonDialog.getInstance().showPermissionDialog(activity, activity.getString(R.string.open_record_audio));
//                    }
//                });
//    }
//
//    public static void requestPhotoAudioPermission(Activity activity, RxPermissions rxPermissions, Consumer<Object> consumer) {
//        rxPermissions.requestEach(Manifest.permission.CAMERA)
//                .flatMap(permission -> {
//                    if (permission.granted) {
//                        return rxPermissions.requestEach(Manifest.permission.RECORD_AUDIO);
//                    }
//                    throw new Exception(activity.getString(R.string.open_camera));
//                })
//                .subscribe(permission -> {
//                    if (permission.granted) {
//                        if (consumer != null) {
//                            consumer.accept(0);
//                        }
//                    } else {
//                        CommonDialog.getInstance().showPermissionDialog(activity, activity.getString(R.string.open_record_audio));
//                    }
//                }, throwable -> CommonDialog.getInstance().showPermissionDialog(activity, throwable.getMessage()));
//    }
//
//    public static void requestExternalContactPermission(Activity activity, RxPermissions rxPermissions, Consumer<Object> consumer) {
//        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .flatMap(permission -> {
//                    if (permission.granted) {
//                        return rxPermissions.requestEach(Manifest.permission.READ_CONTACTS);
//                    }
//                    throw new Exception(activity.getString(R.string.open_external_storage));
//                })
//                .subscribe(permission -> {
//                    if (permission.granted) {
//                        if (consumer != null) {
//                            consumer.accept(0);
//                        }
//                    } else {
//                        CommonDialog.getInstance().showPermissionDialog(activity, activity.getString(R.string.open_read_contacts));
//                    }
//                }, throwable -> CommonDialog.getInstance().showPermissionDialog(activity, throwable.getMessage()));
//    }
//
//    public static void requestExternalPhotoPermission(Activity activity, RxPermissions rxPermissions, Consumer<Object> consumer) {
//        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .flatMap(permission -> {
//                    if (permission.granted) {
//                        return rxPermissions.requestEach(Manifest.permission.CAMERA);
//                    }
//                    throw new Exception(activity.getString(R.string.open_external_storage));
//                })
//                .subscribe(permission -> {
//                    if (permission.granted) {
//                        if (consumer != null) {
//                            consumer.accept(0);
//                        }
//                    } else {
//                        CommonDialog.getInstance().showPermissionDialog(activity, activity.getString(R.string.open_camera));
//                    }
//                }, throwable -> CommonDialog.getInstance().showPermissionDialog(activity, throwable.getMessage()));
//    }
//
//    public static void requestExternalPhotoAudioPermission(Activity activity, RxPermissions rxPermissions, Consumer<Object> consumer) {
//        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .flatMap(permission -> {
//                    if (permission.granted) {
//                        return rxPermissions.requestEach(Manifest.permission.CAMERA);
//                    }
//                    throw new Exception(activity.getString(R.string.open_external_storage));
//                })
//                .flatMap(permission -> {
//                    if (permission.granted) {
//                        return rxPermissions.requestEach(Manifest.permission.RECORD_AUDIO);
//                    }
//                    throw new Exception(activity.getString(R.string.open_camera));
//                })
//                .subscribe(permission -> {
//                    if (permission.granted) {
//                        if (consumer != null) {
//                            consumer.accept(0);
//                        }
//                    } else {
//                        CommonDialog.getInstance().showPermissionDialog(activity, activity.getString(R.string.open_record_audio));
//                    }
//                }, throwable -> CommonDialog.getInstance().showPermissionDialog(activity, throwable.getMessage()));
//    }
//}
