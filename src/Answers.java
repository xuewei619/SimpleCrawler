package com.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class Answers implements Details {
	
	private Connection conn = JDBCUtil.getConnection();
	
	private void insertToDataBase(List<Object> params){		
		try {
			String sql = "insert into ANSWERS(collection_no,question_no,answer_no,question_title,answer_content,insert_time) values(?,?,?,?,?,?)";
			JDBCUtil.insertInfo(sql, params, conn);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	
	
	private void matchString(String regex,String text,List<Object> params){
		String aid = new String();
		String content = new String();
		String q_title = new String();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		
		String title_regex = "<title>([\\s\\S]+?)</title>";
		Pattern title_pattern = Pattern.compile(title_regex);
		Matcher title_matcher = title_pattern.matcher(text);	
		
		if(title_matcher.find()){
			q_title = title_matcher.group(1);
		}
		while(matcher.find()){
			List<Object> answer = new ArrayList<Object>();
			answer.addAll(params);
			aid = matcher.group(1);
			content = matcher.group(2);
			
			answer.add(aid);
			answer.add(q_title);
			answer.add(content);
			answer.add(new Date());	
			
			insertToDataBase(answer);
			
		}
		
	}


	private int getId(String url){		
		String num_str = url.substring(url.lastIndexOf("/") + 1,url.length());
		
		return Integer.parseInt(num_str);		
	}


	@Override
	public void getDetails(Crawler crawler, String html) {
		List<Object> params = new ArrayList<Object>();	
		
		String init_Url = crawler.getInitUrl();
		String current_Url = crawler.getCurrentUrl();
		
		int collection_no = getId(init_Url);	
		
		int question_no = getId(current_Url);
		
		params.add(collection_no);
		
		params.add(question_no);
		
		if(current_Url.indexOf("question") > -1){
			String  aid_regex = "<div[\\s\\S]*?data-aid=\"([\\s\\S]+?)\"[\\s\\S]*?>[\\s\\S]*?<div[\\s]*?class=\"zm-editable-content clearfix\"[\\s]*?>([\\s\\S]+?)</div>[\\s\\S]*?</div>";
			
			matchString(aid_regex, html, params);
		}
		
		//System.out.println(params);
	}

}
