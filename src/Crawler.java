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
	private static BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	private static HashSet<String> past = new HashSet<String>();
	
	
	
	public Crawler(String Url){		
		this.initUrl = Url;
		queue.add(initUrl);
		past.add(initUrl);
	}
	
	public void setInitUrl(String initUrl){
		this.initUrl = initUrl;
	}
	
	public String getInitUrl(){
		return this.initUrl;
	}
	
	
	
	@SuppressWarnings("resource")
	public  String getHtml(String Url) throws ParseException, IOException{
		HttpClient client = new DefaultHttpClient();		
		HttpGet get = new HttpGet(Url);
		
		RequestConfig config = RequestConfig.custom().setSocketTimeout(1000).setConnectTimeout(1000).build();	
		get.setConfig(config);
		HttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
		
		return EntityUtils.toString(entity, "utf-8");
	}
	
	public void addUrl(String html,String threadName){
		Pattern pattern = Pattern.compile("<a[\\s\\S]+?href=\"([\\s\\S]+?)\"[\\s\\S]*?>[\\s\\S]*?</a>");
		Matcher matcher = pattern.matcher(html);
		while (matcher.find()) {
			String newUrl = matcher.group(1);
			if (past.add(newUrl)) {
				if (newUrl.indexOf("http://") == -1) {
					newUrl = initUrl + newUrl;
				}
				queue.add(newUrl);
				//System.out.println(threadName + ":" + newUrl);
			}
		}

	}
	
	public void execute(SomeElse someElse){
		CrawlerThread thread = new CrawlerThread(this,someElse);
		thread.start();
	}
	
}
