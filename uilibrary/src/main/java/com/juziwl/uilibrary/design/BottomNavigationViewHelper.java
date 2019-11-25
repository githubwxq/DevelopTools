package com.juziwl.uilibrary.design;

import android.annotation.SuppressLint;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;
@Deprecated
public class BottomNavigationViewHelper {
  @SuppressLint("RestrictedApi")
  public static void disableShiftMode(BottomNavigationView navigationView) {
    BottomNavigationMenuView menuView =
        (BottomNavigationMenuView) navigationView.getChildAt(0);
    try {
      Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
      shiftingMode.setAccessible(true);
      shiftingMode.setBoolean(menuView, false);
      shiftingMode.setAccessible(false);
      
      for (int i = 0; i < menuView.getChildCount(); i++) {
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
        itemView.setShifting(false);
        itemView.setChecked(itemView.getItemData().isChecked());
      }
    } catch (NoSuchFieldException e) {
      // Log
    } catch (IllegalAccessException e) {
      // Log
    }
  }
}

//  BottomNavigationView bottomNavigationView =
//          (BottomNavigationView) findViewById(R.id.bnv_bottom_menu);
//BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
//
//        作者：ChristmasJason
//        链接：https://www.jianshu.com/p/e2a8791e80d6
//        来源：简书
//        简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。