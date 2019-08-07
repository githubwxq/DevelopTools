package com.wxq.commonlibrary.base;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * 在子线程中初始化一些东西
 */
public class InitService extends IntentService {


    public static void init(Context context) {
        Intent intent = new Intent(context, InitService.class);
        context.startService(intent);
    }

    public InitService() {
        super("InitService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
