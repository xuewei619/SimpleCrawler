package com.crawler.core;

import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class Crawler {
	private String initUrl;
	
	private String srcUrl;
	
	private String currentUrl;
	
	public static BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	
	public static HashSet<String> past = new HashSet<String>();
	
	
	
	public Crawler(String Url,String srcUrl){		
		this.initUrl = Url;
		this.srcUrl = srcUrl;
		queue.add(initUrl);
		past.add(initUrl);
	}
	
	public void setInitUrl(String initUrl){
		this.initUrl = initUrl;
	}
	
	public String getInitUrl(){
		return this.initUrl;
	}
	
	
	public String getSrcUrl() {
		return srcUrl;
	}

	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}

	public String getCurrentUrl() {
		return currentUrl;
	}

	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}	

	public static BlockingQueue<String> getQueue() {
		return queue;
	}

	public static void setQueue(BlockingQueue<String> queue) {
		Crawler.queue = queue;
	}

	public String preHandler(String url){
		return url;
	}
	
	
	@SuppressWarnings("resource")
	public  String getHtml(String Url) throws ParseException, IOException{
		setCurrentUrl(Url);
		HttpClient client = new DefaultHttpClient();		
		HttpGet get = new HttpGet(Url);
		
		RequestConfig config = RequestConfig.custom().setSocketTimeout(1000).setConnectTimeout(1000).build();	
		get.setConfig(config);
		HttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
		
		return EntityUtils.toString(entity, "utf-8");
	}
	
	public void addUrl(String html){
		Pattern pattern = Pattern.compile("<a[\\s\\S]+?href=\"([\\s\\S]+?)\"[\\s\\S]*?>[\\s\\S]*?</a>");
		Matcher matcher = pattern.matcher(html);
		while (matcher.find()) {
			String newUrl = matcher.group(1);
			if (past.add(newUrl)) {
				newUrl = preHandler(newUrl);
				if(!"".equals(newUrl)){
					if (newUrl.indexOf("http://") == -1) {
						newUrl = srcUrl + newUrl;
					}
					queue.add(newUrl);
					//System.out.println(newUrl);
				}				
			}
		}

	}
	
	public void execute(Details details){
		
		CrawlerThread thread = new CrawlerThread(this,details);
		thread.start();
	}
	
}
