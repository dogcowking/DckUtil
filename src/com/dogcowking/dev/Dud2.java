package com.dogcowking.dev;

import java.time.LocalDateTime;
import java.util.Date;

public class Dud2 {
	Date date;
	
	public static Dud2 now() {
		Dud2 r = new Dud2();
		r.date = new Date();
		
		return r;
	}
	
	public static Dud2 fromStr_pat(String pattern, String str) throws Exc_Dck {
		Date d= Dud.toDate_ptrn(pattern, str);
		
		Dud2 r = new Dud2();
		r.date = d;
		return r;
	}
	
	public Date toDate() {
		return date;
	}
	
	public String toStr(String pattern) {
		return Dud.toStr_ptrn(pattern, this.date);
	}
	
	public Dud2 addDay(int i) {
		return this;
	}
	
	public String toStr_yyyyMMdd() {
		return Dud.toStr_yyyyMMdd(date);
	}
	
	public String toStr_yyMMdd() {
		return Dud.toStr_yyMMdd(this.date);
	}
	
	public String toStr_ptrn(String sPattern) { 
		return Dud.toStr_ptrn(sPattern, this.date);
	}
	
}
