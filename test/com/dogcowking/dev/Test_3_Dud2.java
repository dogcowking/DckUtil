package com.dogcowking.dev;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.Test;

/**
 * @author asusw
 *
 */
public class Test_3_Dud2 {

	@Test
	public void t_1() throws Exception { 
		String sPattern = "yyyy-MM-dd";
		String sDate = "2021-08-01";
		
		Date d = Dud2.fromStr_pat(sPattern, sDate)
		.addDay(10)
		.toDate();
		
		System.out.println(d);
		
		// 2 //
		String s = Dud2.now()
		.addDay(10)
		.toStr_ptrn("yyyy---MM--dd");
		
		System.out.println(s);
	}
	
	@Test
	public void t_2_toStr_6digit() throws Exception { 
		String s=  Dud2.now().toStr_yyMMdd();
		System.out.println(s);
	}
}
