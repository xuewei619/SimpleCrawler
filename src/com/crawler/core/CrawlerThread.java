package com.crawler.core;

public class CrawlerThread extends Thread {
	private Crawler crawler;
	
	private Details details;

	
	public CrawlerThread(Crawler crawler,Details details) {
		this.crawler = crawler;
		this.details = details;		
	}
	
	
	

	@Override
	public void run() {
		while(true){
			try {
				String html = crawler.getHtml(crawler.getQueue().poll());
				if(details != null){
					
					details.getDetails(crawler,html);
					
				}
				crawler.addUrl(html);
			} catch (Exception e) {
				continue;
			}
		}
	}
}
