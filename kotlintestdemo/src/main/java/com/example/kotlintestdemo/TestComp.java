package com.example.kotlintestdemo;

import org.jetbrains.annotations.NotNull;

import kotlin.jvm.internal.DefaultConstructorMarker;

public final class TestComp {
   @NotNull
   public static final TestComp.Companion Companion = new TestComp.Companion((DefaultConstructorMarker)null);

   public final void test1() {
   }


   //静态内部类
   public static final class Companion {
      public final void test2() {
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
