package com.dogcowking.dev;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.Test;

/**
 * Java 8 부터 도입된 LocalDate 로 구현
 * @author asusw
 *
 */
public class Test_2_2_DckDate_localDate {

	@Test
	public void t_1_parseByPattern() throws Exception { 
		String str = "2019-12-15";
		Date date;
		
		// 4 LocalDate
		LocalDate ldt = LocalDate.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		date = java.sql.Date.valueOf(ldt);
		
	}
	
	@Test
	public void t_1_1_hyphen() throws Exception {
		String str = "2019-12-15";
		Date date;
		
		LocalDate ldt = LocalDate.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		date = java.sql.Date.valueOf(ldt);
		
		Dup.ln(ldt);
		Dup.ln(date);
	}
	
	public void t_1_2_yyyyMMdd() throws Exception {
		
	}
	
	public void t_2_parse_patternAutoDetect() throws Exception {
		
	}
	
	public void t_3_toStr_bbs() throws Exception {
		
	}
	
	public void t_4_strToStr()throws Exception {
		
	}
	
	public void t_5_dateToStr() throws Exception {
		
	}
	public void t_6_dateToStr_yyyyMMdd() throws Exception {
		
	}
	public void t_7_dateToStr_bbs_beforeType() throws Exception {
		
	}
	public void t_8_addDay() throws Exception {
		
	}
	public void t_9_addMonth() throws Exception {
		
	}
	public void t_10_addYear() throws Exception {
		
	}
}
