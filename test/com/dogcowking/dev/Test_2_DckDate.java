package com.dogcowking.dev;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * 
 * 
 * 
 * @author jshasus
 *
 */
public class Test_2_DckDate {
	
	/**
	 * 
	 * @throws ParseException
	 */
	@Test
	public void t_1_stringToDate() throws ParseException {
		String[] sArrDateStr = { 
		"16/04/10 20:03"
		,"20161210"
		,"2013-04-08 10:10:10"
		,"161210"
		,"160410 20:03"
		,"2016.06.21 08:50" // 티스토리
		,"여기서추출해보시지 2013.12.30 15:45"
		, "2016.10.25 오후 10:36"
		, "2016.10.25 오전 10:36"
		, "2016.10.25 AM 10:36"
		, "2016.10.25 PM 10:36"
		, "2016.10.25 PM 9:36"
		,"2016.06.21 08:50" 
		};
		
		for(String sDate : sArrDateStr)  {
			try {
				Dup.ln("sDate : " + sDate);
				Dup.ln("--> "  + Dud.toDate(sDate));
				
			} catch (Exc_Dck e) {
				e.printStackTrace();
			}
		}
	}
	

	
	/**
	 * 날짜 섞인 문자열에서 날짜 추출
	 * @throws Except_Parse
	 */
	@Test 
	public void t_2_extractDate() throws Exception{ 
		String[] as = {
				"여기서추출해보시지 2013.12.30 15:45"
				, "2016.10.25 오후 10:36"
				, "2016.10.25 오전 10:36"
				, "2016.10.25 AM 10:36"
				, "2016.10.25 PM 10:36"
				, "2016.10.25 PM 9:36"
				,"2016.06.21 08:50" 
		};
		
		for(String src : as ) {
			Dup.ln("src : " + src);
			String sDate  = Dud.extractDateString(src);
			Dup.ln("[extractDateString] => "  +sDate);
			
			try {
				Date d = Dud.toDate(src);
				Dup.ln("[stringToDate] => " +d.toString());
			} catch(Exception e) {
				System.out.println("stringTODate는 패턴이 안맞으면 오류 발생");
				//e.printStackTrace();
			}
		}
	}
	
	
}
