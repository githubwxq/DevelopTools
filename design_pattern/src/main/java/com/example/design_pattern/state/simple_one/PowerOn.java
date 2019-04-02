package com.example.design_pattern.state.simple_one;

public class PowerOn  implements TVState {
		@Override
		public void nextChannel() {
			System.out.println("下一频道");
		}

		@Override
		public void preChannel() {
			System.out.println("上一频道");
		}

		@Override
		public void turnOn() {
			System.out.println("正在开机");
		}

		@Override
		public void turnOff() {
			System.out.println("关机");
		}
	}

