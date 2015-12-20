package com.main;

public class Test {
	
	public static void main(String[] args) {
		
		Details answers = new Answers();
		ZhihuCollection collection = new ZhihuCollection("https://www.zhihu.com/collection/19803929", "https://www.zhihu.com");
		collection.execute(answers);
	}

}
