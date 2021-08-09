package com.dogcowking.dev;

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
		Date d;
		
		// 4 LocalDate
		LocalDate ldt = LocalDate.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		System.out.println(ldt);
	}
}
