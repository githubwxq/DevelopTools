package com.wxq.mvplibrary.dagger.component;

import android.app.Application;

import com.wxq.mvplibrary.dagger.module.AppModule;
import com.wxq.mvplibrary.model.PublicPreference;
import com.wxq.mvplibrary.model.UserPreference;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by wxq on 2018/7/12.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {


    Application getApplication();

    PublicPreference getPublicPerference();

    UserPreference getUserPreference();

}
