/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/01
 * desc: 练习groovy语法
 * version:1.0
 */
class PractiseGroovyDemo {
    String name

    void setName(String name) {
        this.name = name
    }

    String getName() {
        return name
    }
//     Groovy中的方法就像使用变量一样，你无须为你的方法定义一个特定的返回类型，但如果为了方法能够更清新，也可以指定返回对象，
//     在Groovy中，方法的最后一行通常默认返回，即使没有return关键字。
    def square(def number) {
        number * number
    }

    def aClosure = {
            //闭包是一段代码，所以需要用花括号括起来..
        String param1, int param2 ->  //这个箭头很关键。箭头前面是参数定义，箭头后面是代码
            println param2 * param2
            println param1 //这是代码，最后一句是返回值，

            //也可以使用return，和Groovy中普通函数一样
    }

    def close = {
        String param1, int param2 ->
            println param2 * param2
            println param1 //这是代码，最后一句是返回值，
    }
    def closeWithOutParmer = {
        println it
    }


    static void main(String[] args) {
        def name = "wxq"
        println "双引号的变量计算:${name}"
        println "this is MyGroovy"
        def instance = new PractiseGroovyDemo()
        instance.setName("测试set方法")
        println instance.getName()
        println instance.square(100)
        instance.close.call("有call的情况", 100)
        instance.close("无call的情况", 100)
        instance.closeWithOutParmer("我是空参数")
        instance.closeWithOutParmer()

        //测试集合
        testList()
        //测试map
        testMap()

    }

    private static void testList() {
        List list = [1, 2, 3, 4, 5]
        list.each {
            println it
        }
        list.each({ def parmer ->
            println "获取参数${parmer}"

        })
    }

    private static void testMap() {
        Map pizza = [one: 100, two: 200]
        println pizza.get("one")
        println pizza.get("two")
        println pizza.one
    }
}

