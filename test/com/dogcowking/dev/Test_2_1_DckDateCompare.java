package com.dogcowking.dev;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

/**
 * Dud 코드 VS 일반 코드 VS Jodatime 코드
 * 
 * ;; Joda Time 에 대한 의존성 필요하여 PRJ 에 Jar 추가했음.
 * 
 *  Maven 사용시 다음과 같음
 *  
 <dependency>
    <groupId>joda-time</groupId>
    <artifactId>joda-time</artifactId>
    <version>2.10.5</version>
</dependency>

 * @author dellw
 *
 */
public class Test_2_1_DckDateCompare {

	
	/**
	 * 정규표현식 의한 날짜 파싱
	 * @throws Exception
	 */
	@Test
	public void t_1_parseByPattern() throws Exception {
		String str = "2019-12-15";
		Date d;
		
		// 1. Dud
		d = Dud.toDate_pat("yyyy-MM-dd", str);
		
		Dup.ln(d);
		
		// 2. 일반 코드
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		d = sdf.parse(str);
		
		Dup.ln(d);
		
		// 3 Joda time
		DateTimeFormatter fmt = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dt = DateTime.parse(str, fmt);
		
		Dup.ln(dt.toDate());
		
	}
	
	
	@Test
	public void t_2_parse_patternAutoDetect() throws Exception {
		String str = "2019-12-15";
		Date d;
		
		// 1. Dud
		d = Dud.toDate(str);
		Dup.ln(d);
		
		// 2. 일반 코드
		// 없음
		
		// 3 Joda time
		// 없음
		
		
	}
	
	
	@Test
	public void t_3_toStrDate() throws Exception {
		Date d = new Date();
		String s;
		
		// 1 // Dud
		s = Dud.toStr_bbs(d);
		Dup.ln(s);
		
		// 2 // 일반
		Date dNow = new Date();
		
		SimpleDateFormat sdfNow = new SimpleDateFormat("YYMMDD");
		String s1 = sdfNow.format(dNow);
		String s2 = sdfNow.format(d);
		
		if(s1.equals(s2)) {
			s = new SimpleDateFormat("HH:mm").format(d);
		}  else {
			s = new SimpleDateFormat("yyMMdd HH:mm").format(d);
		}
		
		Dup.ln(s);;
		
		
		// 3 // Joda time
		// 생략
	}
}
