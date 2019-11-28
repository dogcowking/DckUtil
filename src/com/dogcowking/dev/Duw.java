package com.dogcowking.dev;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

/**
 * <b>Dck Util Network</b><br/>
 * 
 * 
 * 의존성 필요함
 * javax.servlet-3.0.jar 
 * 또는
 *
 <dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>3.0.1</version>
    <scope>provided</scope>
</dependency>
 
 * 
 * 
 * 
 * 191128
 * @author jshasus
 *
 */
public class Duw {


	/**
	 * http://주소:포트/컨텍스트 
	 * 
	 * ;; 뒤에 / 붙여서 사용할것
	 * 
	 * TODO
	 * @param req
	 * @return
	 */
	public static String getBasicRequestUrl(HttpServletRequest req) {
		return "http" + "://"+req.getServerName() + ":"
				+ req.getServerPort()
				+ (req.getContextPath() != null ? req.getContextPath() : "");
	}

	/**
	 * 

	출처: https://nine01223.tistory.com/302 [스프링연구소(spring-lab)]
	 * @param request
	 * @return
	 */
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	/**
	 * paramStr() 을 호출하여 파람이 있다면 &a=1&b=1 형태로 호출..
	 * 파람이 없다면 "" 만 호출
	 * 
	 * 다른 파람에 덧붙일때 사용하기 위함
	 * @param objects
	 * @return
	 */
	public static String paramStr_withAmp(Object... objects) { 
		String r = paramStr(objects);
		if(r.equals("")) {
			return "";
		} else {
			return "&"+paramStr(objects);
		}
	}







	/**
	 * ;; URL 파라메터 넣어주면 URL 파라메터 String 으로 만들고,
	 * - null 인건 빼줌.
	 * - 복수 파람 처리 (ex : a=1 & a=2
	 * 
	 * ;; 한글, 특문은 URL 인코딩 됨
	 * 
	 * [짝수(0부터)] param Key 
	 * [홀수] param value
	 * 
	 * ;; t 2-44
	 * 
	 * ;; paramOne() 과 연결됨
	 * 
	 * param value null 이면 해당 파람은 빠짐 
	 * 
	 * @param objects
	 * @return
	 */
	public static String paramStr(Object... objects) {
		String r ="";

		boolean moreThanOne = false;

		for(int i=0; i<objects.length ; i+=2) {
			if(objects[i+1] == null ) continue;


			// 파라메터 값이 배열인경우 
			if(objects[i+1].getClass().isArray() == true) {
				Object[] values = (Object[]) objects[i+1];

				for(Object oVal : values) {
					if(moreThanOne == true) { 
						r+= "&";
					}
					r+=paramOne((String)objects[i], oVal);
					moreThanOne = true;
				}

			} else { // 일반적인 파람 값 1개 인 경우
				if(moreThanOne == true) { 
					r+= "&";
				}
				r += paramOne((String)objects[i], objects[i+1]);
				moreThanOne = true;
			}
		}



		return r;
	}


	/**
	 * 
	 * @param sKey
	 * @param bValue
	 * @return
	 */
	public static String paramOne(String sKey, Object bValue) {
		String r="";
		r+= sKey + "=";

		if(bValue.getClass()  == String.class) { // str 인경우 인코딩

			try {
				r += URLEncoder.encode((String)bValue , "UTF-8");
			} catch (UnsupportedEncodingException e) { // 나올리가.
				r +="encodeErr";
			}

		} else { // 다른 클래스는 toString 알아서 되게
			r+= bValue;
		}

		return r;
	}


	/**
	 * 1개 key 에 복수 value 들어가는 경우 를 위해
	 * ex : cipId =[1,2,3,4] 면
	 * [cipId,1,cipId,2,cipId,3,cipId,4] 배열 만들어줌 
	 * 
	 * 
	 * ;; t 2-46
	 * @param sKey
	 * @param Ids
	 * @return
	 */
	public static Object[] paramMultiToAo(String sKey, Object[] values) {
		Object[] arr =new Object[values.length * 2];

		for(int i=0; i<values.length ; i+=2) {
			arr[i] = sKey;
			arr[i+1] = values[(i-1)/2];
		}

		return arr;
	}


}
