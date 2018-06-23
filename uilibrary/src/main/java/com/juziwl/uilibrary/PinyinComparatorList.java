//package com.juziwl.uilibrary;
//
//import android.text.TextUtils;
//
//import com.juziwl.commonlibrary.model.User;
//
//import java.io.Serializable;
//import java.util.Comparator;
//
//public class PinyinComparatorList implements Comparator<User>, Serializable {
//
//    @Override
//    public int compare(User user1, User user2) {
//        try {
//            //把所有非字母开头的拼音都放到最后，{比z大
//            String p1 = "", p2 = "";
//            if (!TextUtils.isEmpty(user1.pinyin)) {
//                if (user1.pinyin.substring(0, 1).matches("[A-Z]|[a-z]")) {
//                    p1 = user1.pinyin;
//                } else {
//                    p1 = "{" + user1.pinyin;
//                }
//            }
//            if (!TextUtils.isEmpty(user2.pinyin)) {
//                if (user2.pinyin.substring(0, 1).matches("[A-Z]|[a-z]")) {
//                    p2 = user2.pinyin;
//                } else {
//                    p2 = "{" + user2.pinyin;
//                }
//            }
//            return p1.compareTo(p2);
//        } catch (Exception e) {
//        }
//        return 0;
//    }
//}
