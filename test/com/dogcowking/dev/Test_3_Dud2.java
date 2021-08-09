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
public class Test_3_Dud2 {

	@Test
	public void t_1() throws Exception { 
		Date d = Dud2.fromStr_pat(null, null)
		.addDay(10)
		.toDate();
		
		
		// 2 //
		Dud2.now()
		.addDay(10)
		.toStr_pat();
	}
}
