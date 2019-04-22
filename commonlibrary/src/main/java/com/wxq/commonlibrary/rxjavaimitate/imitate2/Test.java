package com.wxq.commonlibrary.rxjavaimitate.imitate2;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/22
 * desc:
 * version:1.0
 */
public class Test {
    public  static  void main(String[] args){
        CallerCreate.create(new CallerOnCall<String>() {
            @Override
            public void call(CallerEmitter<String> callerEmitter) {
                System.out.print("我是最上游String");
                callerEmitter.onReceive("String");
//                callerEmitter.onCompleted();
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer call(String s) {
                System.out.print("我是map转换"+s+"为"+100);
                return 100;
            }
        }).call(new Callee<Integer>() {
            @Override
            public void onCall(Release release) {

            }

            @Override
            public void onReceive(Integer s) {
               System.out.print("收到信息"+s);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }
}
