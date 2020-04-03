package com.dogcowking.dev;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;


/**
 * 
 * @author jshasus
 *
 */
public class Test_3_OKKY_EMAIL {
	public static String connect(String sUrl) throws Exception {
		System.out.println("시작 "+sUrl);
		URL u = new URL(sUrl);

		HttpURLConnection con = (HttpURLConnection) u.openConnection();
		con.setRequestMethod("GET");
		System.out.println(con.getHeaderFields());

		System.out.println("접속시작");
		con.connect();

		Charset charset = Charset.forName("UTF-8");

		InputStream is = con.getInputStream();

		StringBuffer sb = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = is.read(b)) != -1;) {
			sb.append(new String(b, 0, n));
		}

		System.out.println("끝");
		return sb.toString();

	}


	@Test
	public void t_6_httpConnection() throws Exception{ 
		String sUrl = "https://okky.kr/articles/jobs";
		Set<String> articles = new HashSet<String>();

		for(int i=0 ; i<5000 ; i+=20) {
			String addUrl = "?offset="+i+"&max=20&sort=id&order=desc";
			String sTargetUrl = sUrl+(i>0?addUrl:"");
			System.out.println(sTargetUrl);
		
			String r=  connect(sTargetUrl);
	//		System.out.println(r);
			
			Document d  = Jsoup.parse(r); 
			Elements es = d.select(".list-group-item-evaluate a");
			
			System.out.println("--- URL 분리 --");
			es.forEach(e -> {
				System.out.println(e.attr("href"));
				articles.add(e.attr("href"));
			});
		}
		
		// 1-1 URL 출력
		String sPath = "C:\\eGovFrameDev-3.8.0\\workspace\\DckUtil\\test\\com\\dogcowking\\dev";
		FileOutputStream fos = new FileOutputStream(sPath+"\\test.txt");
		DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));

		for(String s : articles) {
			System.out.println(s);
			outStream.write((s+ "\r\n").getBytes("utf-8") );
			outStream.flush();
		}
		outStream.close();
		
		System.out.println("결과 : " + articles.size());
		

	}
	
	@Test
	public void t_7_fileWrite() throws Exception { 
		String sPath = "C:\\eGovFrameDev-3.8.0\\workspace\\DckUtil\\test\\com\\dogcowking\\dev";
		FileOutputStream fos = new FileOutputStream(sPath+"\\test.txt");
		DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
		outStream.writeUTF("아냐\r\n정말\r\n왜맨날\r\n"); 
		outStream.close();
	}
	
	
	@Test
	public void t_8_getUrlFromFile() throws Exception {
		//detail-info-row
		
		String sPath = "C:\\eGovFrameDev-3.8.0\\workspace\\DckUtil\\test\\com\\dogcowking\\dev";
		// 1 // 파일 읽어서 URL 가져오기
		FileInputStream is = new FileInputStream(sPath+"\\test.txt");
		StringBuilder sb = new StringBuilder();
		byte[] b = new byte[4096];
		for (int n; (n = is.read(b)) != -1;) {
			sb.append(new String(b, 0, n));
		}
		
		String[] as = sb.toString().split("\r\n");
		
		// 1-3 저장 준비 
		Set<String> tels = new HashSet<String>();
		Set<String> emails = new HashSet<String>();
			

		// 2 // 접속
		for(int i=0 ; i<as.length ; i++) {
			String sUrl = "https://okky.kr"+as[i];
			String sHtml = connect(sUrl);
			
//			System.out.println(sHtml);
			
			Document d = Jsoup.parse(sHtml);
			Elements es = d.select(".detail-info-row");
			
			for(int j=0 ; j<es.size() ; j++) {
				Element e = es.get(j);
				System.out.println(e);
				
				// 1 // 전번 찾기
				Pattern p;
				Matcher m;
				 p = Pattern.compile("\\d{3}-\\d{3,4}-\\d{4}");
				 m = p.matcher(e.toString());
				while(m.find()) {
					System.out.println(" 번호 : " +m.group());
					tels.add(m.group());
					
					
				} 
				
				// 2 // email 찾기
				// @ -> &#64;
				p = Pattern.compile("([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})");
				m = p.matcher(e.toString());
				while(m.find()) {
					System.out.println("이메일 : " + m.group());
					emails.add(m.group());
				
				} 
				
				
			}
		}
		
		// 4-1 파일 준비
		FileOutputStream fos1 = new FileOutputStream(sPath+"\\tel.txt");
		DataOutputStream os1 = new DataOutputStream(new BufferedOutputStream(fos1));
		
		FileOutputStream fos2 = new FileOutputStream(sPath+"\\email.txt");
		DataOutputStream os2 = new DataOutputStream(new BufferedOutputStream(fos2));
		
		
		// 4-2 // 파일로 저장 
		for(String s : tels) {
			os1.write((s+"\r\n").getBytes("utf-8"));
			os1.flush();
		}
		
		for(String s: emails) {
			os2.write((s+"\r\n").getBytes("utf-8"));
			os2.flush();
		}
		
		
		os1.close();
		os2.close();
	}
	
	@Test
	public void t_9() {
		Pattern p = Pattern.compile("([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})");
		Matcher m = p.matcher("010-459-1978 hiohiohoihoi abc@email.com tykim95&#64;nate.com"
				+ "<div class=\"detail-info-row\"> <span class=\"info-label\">이메일 :</span> tykim95@nate.com ");
//		System.out.println(m.matches());
		while(m.find()) {
			System.out.println(m.group());
		} 
	}
}
