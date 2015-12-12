import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SomeElseImpl implements SomeElse {
	
	/**
	 * 得到匹配后的字符串
	 * @param regex 正则
	 * @param text	待匹配的字符串
	 * @return		结果字符串
	 */
	private String getMatcherString(String regex,String text){
		String str = new String();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		if(matcher.find()){
			str = matcher.group(1);
		}
		return str;
	}

	@Override
	public Map<String, Object> getHead(String html) {
		Map<String, Object> map = new HashMap<String,Object>();
		
		String head_regex = "<head>[\\s\\S]*?</head>"; 
		Pattern head_pat = Pattern.compile(head_regex);
		Matcher head_matcher = head_pat.matcher(html);
		while(head_matcher.find()){
			String head = head_matcher.group(0);
			
			String title_regex = "<title>([\\s\\S]*?)</title>";			
			String title = getMatcherString(title_regex, head);
			map.put("title", title);
			
			String key_regex = "<meta[\\s]*?name=\"[k|K]eywords\"[\\s]*?content=\"([\\s\\S]*?)\"[\\s]*?/>";			
			String keywords = getMatcherString(key_regex, head);
			map.put("keywords", keywords);
			
			String des_regex = "<meta[\\s]*?name=\"[d|D]escription\"[\\s]*?content=\"([\\s\\S]*?)\"[\\s]*?/>";
			String description = getMatcherString(des_regex, head);
			map.put("description", description);
		}
		return map;
	}

}
