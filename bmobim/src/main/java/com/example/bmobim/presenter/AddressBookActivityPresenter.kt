//package com.example.bmobim.presenter
//
//import com.example.bmobim.bean.Friend
//import com.example.bmobim.contract.AddressBookContract
//import com.wxq.commonlibrary.base.RxPresenter
//import java.util.ArrayList
//
//import com.wxq.commonlibrary.util.ToastUtils
//
//import cn.bmob.v3.exception.BmobException
//import cn.bmob.v3.listener.FindListener
//
///**
// * 作者
// *
// *
// * 邮箱：
// */
//class AddressBookActivityPresenter(view: AddressBookContract.View) : RxPresenter<AddressBookContract.View>(view), AddressBookContract.Presenter {
//
//    var friends: MutableList<Friend> = ArrayList()
//
//    override fun initEventAndData() {
//
//    }
//
//    /**
//     * 查询本地会话
//     */
//    override fun queryFriends() {
//        BmobUserModel.getInstance().queryFriends(
//                object : FindListener<Friend>() {
//                    override fun done(list: List<Friend>, e: BmobException?) {
//                        if (e == null) {
//                            friends.clear()
//                            //添加首字母
//                            for (i in list.indices) {
//                                val friend = list[i]
//                                val username = friend.getFriendUser().username
//                                if (username != null) {
//                                    val pinyin = Pinyin.toPinyin(username[0])
//                                    friend.setPinyin(pinyin.substring(0, 1).toUpperCase())
//                                    friends.add(friend)
//                                }
//                            }
//                        } else {
//                            ToastUtils.showShort(e.message)
//                        }
//                    }
//                }
//
//
//        )
//    }
//}
