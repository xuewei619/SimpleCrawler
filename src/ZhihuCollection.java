package com.main;

public class ZhihuCollection extends Crawler {

	public ZhihuCollection(String Url, String srcUrl) {
		super(Url, srcUrl);		
	}
	
	@Override
	public String preHandler(String url) {
		if(url.indexOf("question") > -1 || url.indexOf("collection") > -1){
			if(url.indexOf("answer") == -1){
				return url;
			}			
		}
		return "";
	}	
	
	
}
