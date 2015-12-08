
public class CrawlerThread extends Thread {
	private Crawler crawler;
	
	private String name;
	
	public CrawlerThread(Crawler crawler,String name) {
		this.crawler = crawler;
		this.name = name;
	}
	
	public CrawlerThread(Crawler crawler){		
		this(crawler,String.valueOf((int)(Math.random() * 10)));
	}

	

	@Override
	public void run() {
		while(true){
			try {
				String html = crawler.getHtml(crawler.getInitUrl());
				crawler.addUrl(html,name);
			} catch (Exception e) {
				continue;
			}
		}
	}
}
