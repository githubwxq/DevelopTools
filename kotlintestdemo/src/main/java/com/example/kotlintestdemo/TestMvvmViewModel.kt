package com.example.kotlintestdemo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.example.kotlintestdemo.bean.PopularRepository

typealias Block<T> = suspend () -> T
typealias Error = suspend (e: Exception) -> Unit
typealias Cancel = suspend (e: Exception) -> Unit



class TestMvvmViewModel(application: Application) : AndroidViewModel(application) {
    //    public MutableLiveData<String> getmCurrentApiStr() {
//        return mCurrentApiStr;
//    }
//
//    public void setmCurrentApiStr(MutableLiveData<String> mCurrentApiStr) {
//        this.mCurrentApiStr = mCurrentApiStr;
//    }

    var mCurrentApiStr = MutableLiveData<String>()

    var personMutableLiveData = MutableLiveData<Person?>()


    fun changPersonName() {
        val person = Person()
        person.name = "changeName"
        person.age = 11
        mCurrentApiStr.value = "2222"
        personMutableLiveData.value!!.name = "45564564"
        personMutableLiveData.value = personMutableLiveData.value
        //調用接口
        println("changPersonName"+Thread.currentThread().name)

        launch(
                block = {

//                    val apiData =   PopularRepository().getTopArticleList()
                    println("blockbefore"+Thread.currentThread().name)
                    val apiData =   PopularRepository().getMydata()
                    println("blockafter"+Thread.currentThread().name)
                    Log.e("data",apiData.toString())

//                    val apkLink = apiData.get(0).author
                    val apkLink = apiData
                    mCurrentApiStr.value = apkLink

                }
        ,
                error = {
                    Log.e("data","无数据")
                }


        )




    }

    init {
        mCurrentApiStr.value = "test11111111111"
        val person = Person()
        person.name = "wxq"
        person.age = 110
        personMutableLiveData.value = person
    }




    /**
     * 创建并执行协程
     * @param block 协程中执行
     * @param error 错误时执行
     * @return Job
     */
    protected fun launch(block: Block<Unit>, error: Error? = null, cancel: Cancel? = null): Job {
        return viewModelScope.launch {
            try {
                block.invoke()
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> {
                        cancel?.invoke(e)
                    }
                    else -> {
                        Log.e("error",e.message)
//                        onError(e)
//                        error?.invoke(e)
                    }
                }
            }
        }
    }


}