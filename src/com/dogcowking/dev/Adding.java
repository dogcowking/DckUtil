package com.dogcowking.dev;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/** 
 * 배열
 * 리스트
 * 오브젝트 테스트 해봐야 super 도
 * 
 * @author jshasus
 *
 */
public class Adding {

	/**
	 * 
	 * @param o
	 * @return
	 */
	public static String toString(Object o) {
		String r = "";
		Class c = o.getClass();
		
		r+=c.getName()+"(";
		int i=0;
		for(Field f : c.getDeclaredFields() ) {
			
			f.setAccessible(true);
			
			try {
				r += f.getName()+"="+f.get(o);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			
			f.setAccessible(false);
			
			r += ", ";
		}
		r+=")";
		
		return r;
	}
	
	
	
	/**
	 * 
	 */
	public static String snakeToCamel(String s) {
		StringBuilder sb = new StringBuilder();
		
		Pattern p = Pattern.compile("_([a-z])");
		Matcher m = p.matcher(s);
		
		int last=0;
		while(m.find()) { 
			sb.append(s.substring(last, m.start()));
			sb.append(m.group(1).toUpperCase());
			last = m.end();
		}
		sb.append(s.substring(last));
		
		
		return sb.toString();
	}
	
	
	@Test
	public void test_snakeToCamel() { 
		String s="mst_data_id,\r\n" + 
				"			mst_data_nm,\r\n" + 
				"			mst_data_tbl_nm,\r\n" + 
				"			mst_data_tbl_id,\r\n" + 
				"			mst_data_desc,\r\n" + 
				"			data_tp_cd,\r\n" + 
				"			data_dtl_tp_cd,\r\n" + 
				"			sub_sys_cd,\r\n" + 
				"			mngr_eno,\r\n" + 
				"			data_bank_path,\r\n" + 
				"			data_bank_file_nm,\r\n" + 
				"			data_bank_tbl_nm,\r\n" + 
				"			rltn_rcv_data_id";
		System.out.println(snakeToCamel(s));
	}
	
	public static class A {
		int a ;
		String b ="d";
		String c;
	}
	
	public static void main(String[] args) throws Exception {
		String r;
		A a = new A();
		r = toString(a);
		
		System.out.println(r);
		
		
		TestA testA = new TestA();
		r = toString(testA);
		System.out.println(r);
		
		System.out.println("c: "  + testA.c);
		
		
	}
}
