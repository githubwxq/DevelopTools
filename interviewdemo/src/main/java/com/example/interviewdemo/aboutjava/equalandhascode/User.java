package com.example.interviewdemo.aboutjava.equalandhascode;

import java.util.HashMap;
import java.util.Map;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/05/31
 * desc:
 *  在Java中，问什么说重写了equals方法都要进而重写Hashcode方法呢？
 *
 * 原因如下：当equals此方法被重写时，通常有必要重写 hashCode 方法，以维护 hashCode 方法的常规协定，该协定声明相等对象必须具有相等的哈希码。如下：
 * (1)当obj1.equals(obj2)为true时，obj1.hashCode() == obj2.hashCode()必须为true
 * (2)当obj1.hashCode() == obj2.hashCode()为false时，obj1.equals(obj2)必须为false
 *
 * hashcode是用于散列数据的快速存取，如利用HashSet/HashMap/Hashtable类来存储数据时，都是根据存储对象的hashcode值来进行判断是否相同的。
 *
 * 这样如果我们对一个对象重写了euqals，意思是只要对象的成员变量值都相等那么euqals就等于true，但不重写hashcode，那么我们再new一个新的对象，
 * 当原对象.equals（新对象）等于true时，两者的hashcode却是不一样的，由此将产生了理解的不一致。
 *

 * version:1.0
 */
public class User {
    private String name;
    private int age;
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @Override
    public boolean equals( Object obj) {
        if(this == obj) {
            return true;
        }
        if(null == obj) {
            return false;
        }
        if(this.getClass() != obj.getClass()) {
            return false;
        }



        User user = (User) obj;
        if(this.name.equals(user.name)&&this.age == user.age) {
            return true;
        }
        return false;

    }



    public static void main(String[] args){
        test1();
//        test2();
//          test3();

//        test4();

    }

    private static void test4() {

        // TODO Auto-generated method stub
        Student s1 = new Student("阿坤",21);
        Student s2 = new Student("阿坤",21);
        Student s3 = new Student();
        Student s4 = new Student();
        Student s5 = s1;
        System.out.print("普通类对象的==非默认构造：");
        System.out.println(s1 == s2);
        System.out.println(s1 == s5);
        System.out.println("-----");

        System.out.print("普通类对象的equals非默认构造：");
        System.out.println(s1.equals(s2));
        System.out.println(s1.equals(s5));
        System.out.println("-----");

        System.out.print("普通类对象的==默认构造：");
        System.out.println(s3 == s4);
        System.out.println("-----");

        System.out.print("普通类对象的equals默认构造：");
        System.out.println(s3.equals(s4));
        System.out.println("-----");

        System.out.print("对普通对象的属性进行比较equals：");
        System.out.println(s1.name.equals(s2.name));
        System.out.print("对普通对象的属性进行比较==：");
        System.out.println(s1.name == s2.name);

    }

    private static void test3() {

        // TODO Auto-generated method stub
        System.out.println("基本类型没有equals方法");
        System.out.println("-----");

        String s1 = "abc";
        String s2 = "abc";
        System.out.print("String类型的equals方法:");
        System.out.println(s1.equals(s2));
        System.out.println("-----");

        String s3 = new String("abc");
        String s4 = new String("abc");//可以看出比较equals方法比较的是堆里的值是否相同
        System.out.print("String类型的new String()的equals方法：");
        System.out.println(s3.equals(s4));
        System.out.println("-----");

        System.out.print("String用==赋值和用new String()赋值的比较：");
        System.out.println(s1.equals(s3));
        System.out.println("-----");

        Integer i1 = 1;
        Integer i2 = 1;
        System.out.print("包装类的equals方法：");
        System.out.println(i1.equals(i2));
        System.out.println("-----");

        Integer i3 = new Integer(1);
        Integer i4 = new Integer(1);
        System.out.print("包装类的new Integer()用equals方法：");
        System.out.println(i3.equals(i4));
        System.out.println("******"+i3.hashCode()+"******"+i4.hashCode());
        System.out.println("-----");

        System.out.print("Integer用==赋值和用new Integer()赋值的比较：");
        System.out.println(i1.equals(i3));
        System.out.println("-----");






    }

    private static void test2() {
        int a = 10;
        int b = 10;
        System.out.print("基本类型a==b:");
        System.out.println(a == b);
        System.out.println("-----");

        String s1 = "abc";
        String s2 = "abc";
        System.out.print("String类型是s1==s2:");
        System.out.println(s1 == s2);
        System.out.println("-----");

        String s3 = new String("abc");
        String s4 = new String("abc");//可以看出==比较的是栈的地址是否相同
        System.out.print("String类型用new String()是s1==s2:");
        System.out.println(s3 == s4);
        System.out.println(s1 == s3);
        System.out.println("-----");

        Integer i1 = 1;
        Integer i2 = 1;
        System.out.print("包装类型是i1==i2:");
        System.out.println(i1 == i2);
        System.out.println("-----");

        Integer i3 = 129;
        Integer i4 = 129;//此时输出false是因为Integer在-128-127之间会缓存，超出这个范围就不会缓存了
        System.out.print("包装类型是i3==i4:");
        System.out.println(i3 == i4);
        System.out.println("-----");

        Integer i5 = new Integer("1");
        Integer i6 = new Integer("1");
        System.out.print("包装类型用new Integer()是i5==i6:");
        System.out.println(i5 == i6);//用new Integer()多少都不会缓存
        System.out.println("-----");

        A a1 = new A(1);
        A a2 = new A(1);
        A a3 = a2;
        System.out.print("普通引用类型a1 == a2：");
        System.out.println(a1 == a2);
        System.out.println(a2 == a3);//对象赋给新对象连地址都是相同的
        System.out.println("-----");
    }

    private static void test1() {
        User userA = new User();
        userA.setName("王明");
        userA.setAge(10);

        User userB = new User();
        userB.setName("王明");
        userB.setAge(10);

        User userC = new User();
        userC.setName("王亮");
        userC.setAge(10);

        Map map=new HashMap();
        map.put(userA,1);
        map.put(userB,2);
        map.put(userC,5);
        System.out.println("map=========" +    map.put(userB,3));


        System.out.println("map.get(userA)" + map.get(userA));
        System.out.println("map.get(userB)" + map.get(userB));
        System.out.println("map.get(userC)" + map.get(userC));


        System.out.println("userA equals userB:" + userA.equals(userB));
        System.out.println(userA);

        System.out.println(userB);
//        也是在比较两个变量指向的对象是否是同一对象
        if (userA==userB) {
            System.out.println("userA == userB:");
        }else {
            System.out.println("userA != userB:");
        }

        System.out.println("userA equals userB hashcode :" + userA.hashCode()+"===="+userB.hashCode());
        System.out.println("userA equals userC:" + userA.equals(userC));
    }


    @Override
    public int hashCode() {
        return 7 * name.hashCode() + 11 * age + 13 * (age);
    }
}

/*
//结论呀！！
hashcode相等两个类一定相当吗？equals呢？相反呢？
不一定相等，都不一定相当。
        equals() 的作用是 用来判断两个对象是否相等。
        equals() 定义在JDK的Object.java中。通过判断两个对象的地址是否相等(即，是否是同一个对象)来区分它们是否相等。源码如下：
public boolean equals(Object obj) {
        return (this == obj);
        }
        既然Object.java中定义了equals()方法，这就意味着所有的Java类都实现了equals()方法，所有的类都可以通过equals()去比较两个对象是否相等。 但是，我们已经说过，使用默认的“equals()”方法，等价于“==”方法。因此，我们通常会重写equals()方法：若两个对象的内容相等，则equals()方法返回true；否则，返回fasle。
        下面根据“类是否覆盖equals()方法”，将它分为2类。
        (01) 若某个类没有覆盖equals()方法，当它的通过equals()比较两个对象时，实际上是比较两个对象是不是同一个对象。这时，等价于通过“==”去比较这两个对象。
        (02) 我们可以覆盖类的equals()方法，来让equals()通过其它方式比较两个对象是否相等。通常的做法是：若两个对象的内容相等，则equals()方法返回true；否则，返回fasle。
        hashCode()和equals()的关系：
        1. 第一种 不会创建“类对应的散列表”
        这里所说的“不会创建类对应的散列表”是说：我们不会在HashSet, Hashtable, HashMap等等这些本质是散列表的数据结构中，用到该类。例如，不会创建该类的HashSet集合。
        在这种情况下，该类的“hashCode() 和 equals() ”没有半毛钱关系的！
        这种情况下，equals() 用来比较该类的两个对象是否相等。而hashCode() 则根本没有任何作用，所以，不用理会hashCode()。
        2. 第二种 会创建“类对应的散列表”
        这里所说的“会创建类对应的散列表”是说：我们会在HashSet, Hashtable, HashMap等等这些本质是散列表的数据结构中，用到该类。例如，会创建该类的HashSet集合。
        在这种情况下，该类的“hashCode() 和 equals() ”是有关系的：
        1)、如果两个对象相等，那么它们的hashCode()值一定相同。
        这里的相等是指，通过equals()比较两个对象时返回true。
        2)、如果两个对象hashCode()相等，它们并不一定相等。
        因为在散列表中，hashCode()相等，即两个键值对的哈希值相等。然而哈希值相等，并不一定能得出键值对相等。补充说一句：“两个不同的键值对，哈希值相等”，这就是哈希冲突。
        此外，在这种情况下。若要判断两个对象是否相等，除了要覆盖equals()之外，也要覆盖hashCode()函数。否则，equals()无效。
*/
