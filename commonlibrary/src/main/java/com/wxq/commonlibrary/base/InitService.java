package com.wxq.commonlibrary.base;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * 在子线程中初始化一些东西
 */
public class InitService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public InitService(String name) {
        super("InitService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


    }


}
//    startService(new Intent(this, InitService.class));