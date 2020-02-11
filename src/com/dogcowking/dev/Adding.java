package com.dogcowking.dev;

import java.lang.reflect.Field;

public class Adding {

	/**
	 * 
	 * @param o
	 * @return
	 */
	public static String toString(Object o) {
		String r = "";
		Class c = o.getClass();
		System.out.println("클래스 : " + c);
		int i=0;
		for(Field f : c.getDeclaredFields() ) {
			System.out.println("필드 " + i + " " + f);
			try {
				r += f.getName()+":"+f.get(o);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			r += "/";
		}
		
		return r;
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
	}
}
