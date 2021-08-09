package com.dogcowking.dev;

import java.time.LocalDateTime;
import java.util.Date;

public class Dud2 {
	Date date;
	
	public static Dud2 now() {
		Dud2 r = new Dud2();
		r.date = new Date();
		
		LocalDateTime ldt;
		return r;
	}
	
	public static Dud2 fromStr_pat(String pattern, String str) throws Exc_Dck {
		Date d= Dud.toDate_pat(pattern, str);
		
		Dud2 r = new Dud2();
		r.date = d;
		return r;
	}
	
	public Date toDate() {
		return date;
	}
	
	public String toStr(String pattern) {
		return null;
	}
	
	public Dud2 addDay(int i) {
		return this;
	}
	
	
	public String toStr_8() {
		return null;
	}
	
	public String toStr_6() {
		return null;
	}
	
	public String toStr_pat() { 
		return null;
	}
	
}
