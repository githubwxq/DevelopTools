package com.example.kotlintestdemo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestMvvmViewModel extends AndroidViewModel {
//    public MutableLiveData<String> getmCurrentApiStr() {
//        return mCurrentApiStr;
//    }
//
//    public void setmCurrentApiStr(MutableLiveData<String> mCurrentApiStr) {
//        this.mCurrentApiStr = mCurrentApiStr;
//    }

    public MutableLiveData<String> mCurrentApiStr = new MutableLiveData<>();
    public MutableLiveData<Person> personMutableLiveData = new MutableLiveData<>();


    public TestMvvmViewModel(@NonNull Application application) {
        super(application);
        mCurrentApiStr.setValue("test11111111111");
        Person person=  new Person();
        person.name="wxq";
        person.age=110;
        personMutableLiveData.setValue(person);

    }


    public void changPersonName() {
        Person person = new Person();
        person.name="changeName";
        person.age=11;
        mCurrentApiStr.setValue("2222");
        personMutableLiveData.getValue().name="45564564";
        personMutableLiveData.setValue(personMutableLiveData.getValue());

        //調用接口


    }
}
