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
public class Test_3_DckString {
	
	/**
	 * 
	 */
	@Test
	public void t_1_replaceVar() { 
		String s;
		
		s = Dus.replaceVar("{}는 {}와 같다", "나", "바보");
		Dup.ln(s);
	}
	
}
