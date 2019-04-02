package com.example.design_pattern.state.simple_two;

import com.example.design_pattern.state.simple_one.TvContext;

public class Client {
		public static void main(String[] args) {
			Context context = new Context();
			context.fallInLove();
			context.shopping();
			context.movies();
			context.disappointmentInLove();
			context.shopping();
			context.movies();
		}
}
