package com.example.design_pattern.mudule;

public abstract class Postman {//抽象快递员类

        //派送流程
        public final void post() {//这里申明为final，不希望子类覆盖这个方法，防止更改流程的执行顺序
            prepare();//准备派送
            call();//联系收货人
            if (isSign())//是否签收
                sign();//签收
            else refuse();//拒签
        }

        protected void prepare() {//准备操作，固定流程，父类实现
            System.out.println("快递已达到，准备派送");
        }

        protected abstract void call();//联系收货人，联系人不一样，所以为抽象方法，子类实现

        protected boolean isSign() {//是否签收,这个是钩子方法，用来控制流程的走向
            return true;
        }

        protected void sign() {//签收，这个是固定流程，父类实现
            System.out.println("客户已签收，上报系统");
        }

        protected void refuse() {//拒签，空实现，这个也是钩子方法，子类可以跟进实际来决定是否去实现这个方法
        }
    }



//            抽象方法、具体方法和钩子方法。
//            抽象方法：需要子类去实现。如上面的call()。
//            具体方法：抽象父类中直接实现。如上面的prepare()和sign()。
//            钩子方法：有两种，第一种，它是一个空实现的方法，子类可以视情况来决定是否要覆盖它，如上面的refuse()；第二种，它的返回类型通常是boolean类型的，一般用于对某个条件进行判断，如果条件满足则执行某一步骤，否则将不执行，如上面的isSign()。

//       一次性实现算法的执行顺序和固定不变部分，可变部分则交由子类来实现。
//        多个子类中拥有相同的行为时，可以将其抽取出来放在父类中，避免重复的代码。
//        使用钩子方法来让子类决定父类的某个步骤是否执行，实现子类对父类的反向控制。
//        控制子类扩展。模板方法只在特定点调用钩子方法，这样就只允许在这些点进行扩展。
