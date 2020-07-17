package com.example.kotlintestdemo

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlintestdemo.bean.Course
import kotlinx.android.synthetic.main.activity_test_kotlin.*
import me.chen_wei.samples.dialog.showDialog
import kotlin.contracts.Returns

class TestKotlinActivity : AppCompatActivity() {


    lateinit var name: String;
    private var name2: String? = null //不报

    lateinit var context:Context;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_kotlin)
        context=this;
        tv_button.setOnClickListener(View.OnClickListener { view: View ->
            Toast.makeText(this, "onClick", Toast.LENGTH_SHORT).show()


        }
        )

        tv_button.setOnClickListener(fun(view: View) {
            Toast.makeText(this, "two", Toast.LENGTH_SHORT).show()


        })
        tv_button.setOnClickListener { it.setVisibility(View.INVISIBLE) }

        tv_button2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
//                Toast.makeText(TestKotlinActivity.,"onClick",Toast.LENGTH_SHORT).show()
                showDialog {
                    cancelOutside = false
                    title = "Dialog Fragment"
                    message = "A fragment that displays a dialog window, floating on top of its activity's window. "
                    rightClicks(key = "Yes") {
//                        toast("Right Button Clicked!")
                        Toast.makeText(context, "rightClicks", Toast.LENGTH_SHORT).show()
                    }
                    leftClicks {   Toast.makeText(context, "leftClicks", Toast.LENGTH_SHORT).show() }
                }
            }
        })
//        tv_button.setOnTouchListener(object : OnTouchListener {
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                TODO("Not yet implemented")
//            }
//
//        })

        tv_button.setOnTouchListener { v, event -> false };

    var ll=    { x :Int, y :Int -> {
            x+y
        }}

        tv_button.apply {
            text="123"
            setOnClickListener {  }
        }


        var current: Int = Color.GREEN

        var color = when (1) {
            Color.RED -> "red"
            else -> "black"

        }



//        Intent(this, TestKotlinActivity::class.java).apply {
//            action = "actiona"
//            putExtras(Bundle().apply {
//                putString("124", "1234")
//            })
//            startActivity(this)
//        }
//
//

//        ViewUtil.addExtraAnimClickListener(tv_button, ValueAnimator.ofFloat(1f,1.5f).apply {addUpdateListener {
//         var flo=  it.animatedValue as Float
//            tv_button.scaleX=flo;
//            tv_button.scaleY=flo;
//        }  }, object: View.OnClickListener{
//            override fun onClick(v: View?) {
//                TODO("Not yet implemented")
////                Toast.makeText(TestKotlinActivity.t, "spring anim", Toast.LENGTH_LONG).show()
//            }
//
//        });

        tv_button.extraAnimClickListener(ValueAnimator.ofFloat(1.0f,2.0f).apply {
            interpolator=AccelerateInterpolator();
            duration=1000
            addUpdateListener {
                tv_button.scaleX = it.animatedValue as Float
                tv_button.scaleY = it.animatedValue as Float
            }
        },{

        })
        val listOf = listOf(Course("wxq", 1),Course("22222", 1),Course("33333", 1))
        listOf.forEach {
            it.name
        }

        data class Person(val age:Int,val name:String)
        val persons = listOf(Person(17,"daqi"),Person(20,"Bob"))
//寻找年龄最大的Person对象
//花括号的代码片段代表lambda表达式，作为参数传递到maxBy()方法中。
        persons.maxBy( { person: Person -> person.age } )



    }


    fun add(a: Int, b: Int): Int = a + b
    fun add2(a: Int, b: Int): Int {

        return a + b;
    }


    fun getCountry(): String? {

        return name?.toString()
    }


    infix fun Int.ride(num: Int): Int{
        println("num= $num")
        return 2 * num
    }



    fun View.extraAnimClickListener(animator:ValueAnimator,action:(View)->Unit){
     setOnTouchListener { v, event ->
           when (event.action) {
               MotionEvent.ACTION_DOWN -> {
                   animator.start()
               }
               MotionEvent.ACTION_UP,MotionEvent.ACTION_CANCEL-> {
                   animator.reverse()
               }

           }

         false
     }

    }


}