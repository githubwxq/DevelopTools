import kotlinx.coroutines.*
import java.lang.Thread.sleep
import kotlin.Boolean as Boolean1

fun main() {
    var a = num1AndNum2(1, 2) { a,b -> a + b }

    mapOf<String, Int>("A" to 1, "B" to 2)

    val list = listOf<String>("a", "b", "c")
    if (list.has111("1b") ) {
        //处理逻辑
        println("wxq")
    }


//    GlobalScope.launch {
//        println("hello")
//    }
//
//    GlobalScope.launch {
//        println("world")
//    }

    GlobalScope.launch(Dispatchers.Main) {

        println("img之前")
        //指定Lambda在IO线程执行，withContext方法就是一个挂起函数
        val img = withContext(Dispatchers.IO) {
            //网络操作获取照片
            sleep(3000)
            "wxq"
        }
        println("img之后")
        //返回主线程
        println(img)
    }
}

inline fun num1AndNum2(num1: Int, num2: Int, operation: (Int, Int) -> Int): Int {
    return  operation(num1, num2)
}





 fun <T> Collection<T>.has111(element: T): Boolean1 {
    return contains(element)
 }
