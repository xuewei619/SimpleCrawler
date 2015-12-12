import java.util.Map;

public class CrawlerThread extends Thread {
	private Crawler crawler;
	
	private SomeElse someElse;
	
	private String name;
	
	public CrawlerThread(Crawler crawler,SomeElse someElse,String name) {
		this.crawler = crawler;
		this.someElse = someElse;
		this.name = name;
	}
	
	public CrawlerThread(Crawler crawler,SomeElse someElse){		
		this(crawler,someElse,String.valueOf((int)(Math.random() * 10)));
	}
	
	public CrawlerThread(Crawler crawler){
		this(crawler,null,String.valueOf((int)(Math.random() * 10)));
	}

	

	@Override
	public void run() {
		while(true){
			try {
				String html = crawler.getHtml(crawler.getInitUrl());
				if(someElse != null){
					Map<String, Object> map = someElse.getHead(html);
					System.out.println(map.toString());
				}
				crawler.addUrl(html,name);
			} catch (Exception e) {
				continue;
			}
		}
	}
}
