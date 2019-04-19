package com.wxq.commonlibrary.rxjavaimitate.imitate2;


/**
 * com.sxdsf.async.imitate2.TelephonerOperator
 *
 * @author SXDSF
 * @date 2017/11/12 下午10:27
 * @desc 操作符接口
 */

public interface CallerOperator<T, R> {

    Callee<R> call(Callee<T> callee);
}
