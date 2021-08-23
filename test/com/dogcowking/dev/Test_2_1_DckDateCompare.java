package com.dogcowking.dev;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
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
 * 1 Dud
 * 2 Java 일반코드
 * 3 JodaTime
 * 4 LocalDate
 * 5 Dud2
 * 
 * LocalDate 는 JodaTime 과 클래스명이 겹쳐서 Test_2_2 로 분리함
 *
 */
public class Test_2_1_DckDateCompare {

	
	/**
	 * 
	 * 지정패턴 >> Date 객체
	 * 패턴 지정하여 날짜 파싱
	 * @throws Exception
	 */
	@Test
	public void t_1_parseByPattern() throws Exception {
		String str = "2019-12-15";
		Date date;
		
		// 1. Dud
		date = Dud.toDate_ptrn("yyyy-MM-dd", str);
		
		Dup.ln(date);
		
		// 2. 일반 코드
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		date = sdf.parse(str);
		
		Dup.ln(date);
		
		// 3 Joda time
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dt = DateTime.parse(str, fmt);
		date = dt.toDate();
		
		Dup.ln(date);
		
		// 4 // LocalDate // Test2-2 참고 
		
		// 5 // Dud2
		date = Dud2.fromStr_pat("yyyy-MM-dd", str).toDate();
		
	}
	
	
	/**
	 * yyyy-MM-dd 형태의 날짜 파싱
	 * 
	 * yyyy-MM-dd >> Date
	 * 
	 * @throws Exception
	 */
	@Test
	public void t_1_1_hyphen() throws Exception {
		// 1 // dud
		String str = "2019-12-15";
		Date date;
		
		date= Dud.toDate_hyphen(str);
		
		// 2 // Java 일반 코드
		// 3 // JodaTime
		// 4 // LocalDate
		// 5 // Dud2
		date = Dud2.fromStr_hyphen(str).toDate();
	}
	

	/**
	 * yyyyMMdd 형태의 날짜 파싱
	 * 
	 * yyyyMMdd >> Date
	 * 
	 * @throws Exception
	 */
	@Test
	public void t_1_2_yyyyMMdd() throws Exception {
		// 1 // dud
		String str = "20191215";
		Date date;
		
		date= Dud.toDate_yyyyMMdd(str);
		
		// 2 // Java 일반 코드
		// 3 // JodaTime
		// 4 // LocalDate
		// 5 // Dud2
		date = Dud2.fromStr_yyyyMMdd(str).toDate();
		
	}
	
	
	/**
	 * 자동인식
	 * @throws Exception
	 */
	@Test
	public void t_2_parse_patternAutoDetect() throws Exception {
		String str = "2019-12-15";
		Date d;
		
		// 1. Dud
		d = Dud.strToDate_auto(str);
		Dup.ln(d);
		
		// 2. 일반 코드
		// 없음
		
		// 3 Joda time
		// 없음
		
		// 4 // LocalDate
		// 없음
		
		// 5 // Dud2
	}
	
	
	/**
	 * toStr_bbs ( Date -> 오늘이면 HH:mm / 과거면 yyMMdd HH:mm )
	 * 
	 * 
	 * @throws Exception
	 */
	@Test
	public void t_3_toStr_bbs() throws Exception {
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
		
		Dup.ln(s);
		
		
		// 3 // Joda time
		// 생략
		
		
	}
	
	/**
	 * 2020-01-01 >> 20200101
	 * 
	 * @throws Exception
	 */
	@Test
	public void t_4_strToStr() throws Exception {
		String str = "2019-12-15";
		String str2;
		Date date;
		
		// 1 // Dud
		date = Dud.toDate_ptrn("yyyy-MM-dd", str);
		str2 = Dud.toStr_yyyyMMdd(date);
		Dup.ln(str2);
		
		// 2 // Java 일반 코드
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		str2 = sdf2.format(sdf.parse(str));
		Dup.ln(str2);
		
		// 3 // JodaTime
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTimeFormatter fmt2 = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = DateTime.parse(str, fmt);
		str2 = dt.toString(fmt2);
		Dup.ln(str2);
		
		// 4 // LocalDate
		// 5 // Dud2
		str2 = Dud2.fromStr_hyphen(str).toStr_yyyyMMdd();
		Dup.ln(str2);
	}
	
	
	@Test
	public void t_5_dateToStr() throws Exception {
		String str;
		Date date = new Date();
		
		// 1 // Dud
		str = Dud.toStr_ptrn("yyyy-MM-dd", date);
		Dup.ln(str);
		
		// 2 // Java 일반 코드
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		str = sdf.format(date );
		Dup.ln(str);
		
		// 3 // JodaTime
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dt = new DateTime(date);
		str = dt.toString(fmt);
		Dup.ln(str);
		
		// 4 // LocalDate
		
		// 5 // Dud2
		str = Dud2.fromDate(date).toStr_ptrn("yyyy-MM-dd");
		Dup.ln(str);
	}
	
	@Test
	public void t_6_dateToStr_yyyyMMdd() throws Exception {
		String str;
		Date date = new Date();
		
		// 1 // Dud
		str = Dud.toStr_yyyyMMdd(date);
		Dup.ln(str);
		
		// 2 // Java 일반 코드
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		str = sdf.format(date );
		Dup.ln(str);
		
		// 3 // JodaTime
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dt = new DateTime(date);
		str = dt.toString(fmt);
		
		// 4 // LocalDate
		
		// 5 // Dud2
		str = Dud2.fromDate(date).toStr_yyyyMMdd();
		Dup.ln(str);
	}
	
	@Test
	public void t_7_dateToStr_bbs_beforeType() throws Exception {
		String str;
		Date date = new Date();
		
		// 1 // Dud
		str = Dud.toStr_bbs_2(date);
		Dup.ln(str);
		
		// 2 // Java 일반 코드
		// 생략 // Dud.toStr_bbs_2 참고 
		
		// 3 // JodaTime
		// 4 // LocalDate
		// 5 // Dud2
		str = Dud2.now().toStr_bbs_2_before();
		Dup.ln(str);
	}
	
	@Test
	public void t_8_addDay() throws Exception {
		String str;
		Date date = new Date();
		
		// 1 // Dud
		date = Dud.addDay(date, 1);
		
		// 2 // Java 일반 코드
		Calendar c = Calendar.getInstance();
		
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		
		date = c.getTime();
		
		// 3 // JodaTime
		DateTime dt = new DateTime(date);
		dt.plusDays(1);
		date = dt.toDate();
		
		// 4 // LocalDate
		// 5 // Dud2
		date = Dud2.fromDate(date).addDay(1).toDate();
	}
	
	@Test
	public void t_9_addMonth() throws Exception {
		String str;
		Date date = new Date();
		
		// 1 // Dud
		date = Dud.addMonth(date, 1);
		
		// 2 // Java 일반 코드
		Calendar c = Calendar.getInstance();
		
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		
		date = c.getTime();
		
		
		// 3 // JodaTime
		DateTime dt = new DateTime(date);
		dt.plusMonths(1);
		date = dt.toDate();
		
		// 4 // LocalDate
		// 5 // Dud2
		date = Dud2.now().addMonth(1).toDate();
		
	}
	
	
	@Test
	public void t_10_addYear() throws Exception {
		String str;
		Date date = new Date();
		
		// 1 // Dud
		date = Dud.addYear(date, 1);
		
		// 2 // Java 일반 코드
		Calendar c = Calendar.getInstance();
		
		c.setTime(date);
		c.add(Calendar.YEAR, 1);
		
		date = c.getTime();
		
		// 3 // JodaTime
		DateTime dt = new DateTime(date);
		dt.plusYears(1);
		date = dt.toDate();
		
		// 4 // LocalDate
		// 5 // Dud2
		date = Dud2.fromDate(date).addYear(1).toDate();
		
		str = Dud2.now().toStr_bbs_2_before();
		Dup.ln(str);
	}
	
	@Test
	public void tmpMethod() throws Exception{ 
		// 1 // Dud
		// 2 // Java 일반 코드
		// 3 // JodaTime
		// 4 // LocalDate
		// 5 // Dud2
	}
}
