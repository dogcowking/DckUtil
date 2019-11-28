package com.dogcowking.dev;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * <b>Dck Util Print</b><br/>
 * 
 * 191128 분리
 * @author jshasus
 *
 */
public class Dup {
	
	static ThreadLocal<Integer > tlCnt = new ThreadLocal<Integer>();

	public static void p(Object o) { 
		System.out.print(o);
	}
	
	public static void ln(Object o) { 
		System.out.println(o);
	}

	// 구현 시작 // 구현 시작 // 구현 시작 // 구현 시작 // 구현 시작 // 구현 시작 // 구현 시작 // 구현 시작 // 구현 시작 // 구현 시작 
	
	/**
	 * 
	 * ;; 180326
	 * @param m
	 */
	public static void map(Map m) {
		for(Object o : m.entrySet()) {
			p(o);
		}
	}

	
	/**
	 * print Repeat
	 * @param s
	 */
	public static void repeat(String s, int num) { 
		for(int i=0; i<num ; i ++) { 
			p(s);
		}
	}
	

	/**
	 * 
	 * @param i : 대기 시간(초)
	 */
	public static void wait(String strName, int i) {
		for(int j=0 ; j<i ; j++ ) {
			ln(strName + " Waiting... "+j+" / " + i +" sec");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * 
	 * @param str
	 */
	public static void notice(String str) {
		ln("--------------------------- "+str+" --------------------------- ");
	}
	



	/**
	 * print AND print count
	 * @param o
	 */
	public static void cnt(Object o) { 
		Integer i = tlCnt.get();
		i = Dck.nvl(i, 0);
		i++;
		
		p("-----------------" + i + " " + o);
		
		tlCnt.set(i);
	}
	
	/**
	 * 리스트를 프린트.
	 * 
	 * List<Object> 로 간주
	 * @param lst
	 */
	public static void coll(Collection<?> lst) { 
		if(lst == null) {
			ln("list null");;
			return;
		}
		ln("[[ 리스트 출력 - size : " + lst.size());
		int cnt =0 ; 
		for(Object o : lst) { 
			if(o==null ) { 
				ln(cnt + "(NULL)");
			} else {
				ln(cnt + " " + o.toString());
			}
			
			cnt++;
		}
		ln("// 리스트 종료");
	}
	
	

	public static void arr(Object[] arr) {
		ln("[[ 리스트 출력 - size : " + arr.length);
		
		int cnt =0 ; 
		for(Object o : arr) {
			if(o==null ) { 
				ln(cnt + " " +"(NULL)");
			} else {
				ln(cnt + " " +o.toString());
			}
			cnt++;
		}
		ln("//");
	}
	
	
	
	
	/**
	 * 
	 * DB SQL 실행 결과가 List<Object[]> 로 나온 경우 이를 프린트
	 * 
	 * 
	 * @param lstAo
	 */
	public static void printList_sqlResult(List<Object[]> lstAo) {
		line();
		p("size : " + lstAo.size());
		for(Object o : lstAo) {
			Object[] ao = (Object[])o;
			for(Object o2 : ao) {
				if(o2==null ) { 
					p("(NULL)");
				} else {
					p(o2);
				}
				ln(" ");
			}
			ln("");
		}
		line();
	}

	


	

	public static void line() {
		ln("-------------------------------------------------------------");
	}

	public static void title(String string) {
		ln("------------------- "+string + " --------------");
	}

		
	
	public static String getTitleWithLine(String title) { 
		return "---------------- " + title + " --------------\r\n";
	}
	
	/**
	 * 별 구분선출력 ********************
	 * @return
	 */
	public static String getDivisionLine() {
		return "\r\n******************************************************************\r\n";
	}
	





	public static void wait(String string) {
		wait(string, 3);
	}
}
