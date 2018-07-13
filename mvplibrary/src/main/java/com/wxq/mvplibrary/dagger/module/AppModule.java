package com.wxq.mvplibrary.dagger.module;

import android.app.Application;

import com.wxq.mvplibrary.model.PublicPreference;
import com.wxq.mvplibrary.model.UserPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wxq on 2018/7/12.
 */
@Module
public class AppModule {


    private Application application;


    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public Application getApplication() {
        return application;
    }


    @Provides
    @Singleton
    public PublicPreference providePublicPreference() {
        return new PublicPreference(application);
    }

  @Provides
    @Singleton
    public UserPreference provideUserPreference() {
        return new UserPreference(providePublicPreference());
    }


}
