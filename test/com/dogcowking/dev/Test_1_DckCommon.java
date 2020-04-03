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
public class Test_1_DckCommon {

	@Test
	public void t_1_() throws Exception { 
	}




	@Test
	public void t_4_callAddScheduleToBatch() throws Exception {
		//		String sUrl = "http://192.168.0.120:6080/bat/addBatchSchdul.do?resultCnt=0&batchSchdul=";
		String sUrl = "http://192.168.0.120:6080/bat/UrlTest.do?jobId=";
		String jobId = "JOB_ID_0190";
		String USER_AGENT = "Mozilla/5.0"; //(Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36";


		sUrl += jobId;
		//		sUrl = "http://192.168.0.120:6080/bat/UrlTest.do?jobId=JOB_ID_0190";


		URL obj = new URL(sUrl);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		//전송방식
		con.setRequestMethod("GET");
		//Request Header 정의
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setConnectTimeout(10000);       //컨텍션타임아웃 10초
		con.setReadTimeout(5000);           //컨텐츠조회 타임아웃 5총
		//        con.addRequestProperty("Accept-Language","ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + sUrl);
		System.out.println("Response Code : " + responseCode);

		Charset charset = Charset.forName("UTF-8");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),charset));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		System.out.println("조회결과 : " + response.toString());

	}
	// 출처: https://bobr2.tistory.com/entry/HttpConnection-Get-Post-사용법 [나만의공간]


	@Test
	public void t_5() {
		String r = "ABC00001,BCD0001";
		Matcher m = Pattern.compile("([A-Z0-9]+),([A-Z0-9]+)").matcher(r);

		System.out.println(m.matches());
		System.out.println(m.group(0));
		System.out.println(m.group(1));
		System.out.println(m.group(2));
	}


}
