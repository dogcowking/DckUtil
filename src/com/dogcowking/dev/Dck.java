package com.dogcowking.dev;



/**
 * <b>Dck Util 관리 클래스</b><br/>
 * 
 * 1. Dck Util Common 은 상속받을것 <br/>
 * 2. 그 외  클래스는 줄임말 형식으로 선언
 * (기존에는 DckDateUtil 을 Dck.d 로 줄여 썼으나, 점 하나 빼기 위해 그냥 Dckd 로 쓰므로 큰 의미는 없음)
 * 
 * 
 * @author jshasus
 *
 */
public class Dck extends Duc {
	
	/**
	 * Date Util 
	 */
	public static class d extends Dud {};

	/**
	 * String Util 
	 */
	public static class s extends Dus {};
	
	/**
	 * Print Util
	 */
	public static class p extends Dup {};
	
}
