package com.dogcowking.dev;

import java.time.LocalDateTime;
import java.util.Date;

import org.joda.time.base.AbstractInstant;

public class Dud2 {
	Date date;
	
	/**
	 * fromDate_now() 의 약어
	 * @return
	 */
	public static Dud2 now() {
		return fromDate_now();		
	}
	
	/**
	 * 약어 : now()
	 * @return
	 */
	public static Dud2 fromDate_now() {
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
	
	public static Dud2 fromStr_hyphen(String str) throws Exc_Dck {
		Date d  = Dud.toDate_hyphen(str);
		Dud2 r = new Dud2();
		r.date = d;
		return r;
	}
	
	public static Dud2 fromStr_yyyyMMdd(String str) throws Exc_Dck {
		Date d  = Dud.toDate_yyyyMMdd(str);
		Dud2 r = new Dud2();
		r.date = d;
		return r;
	}
	
	public static Dud2 fromDate(Date d) {
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
	
	public String toStr_yyyyMMdd() {
		return Dud.toStr_yyyyMMdd(date);
	}
	
	public String toStr_yyMMdd() {
		return Dud.toStr_yyMMdd(this.date);
	}
	
	public String toStr_ptrn(String sPattern) { 
		return Dud.toStr_ptrn(sPattern, this.date);
	}

	public String toStr_bbs_2_before() {
		return Dud.toStr_bbs_2(this.date);
	}
	
	/////////////////////////////////////////
	public Dud2 addMonth(int i) {
		this.date =  Dud.addMonth(this.date, i);
		return this;
	}
	
	public Dud2 addDay(int i) {
		this.date =  Dud.addDay(this.date, i);
		return this;
	}
	
	public Dud2 addYear(int i) {
		this.date =  Dud.addYear(this.date, i);
		return this;
	}
}
