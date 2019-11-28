package com.dogcowking.dev;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <b>Dck Util Date</b><br/>
 * 
 * 
 * s : String 클래스<br/>
 * d : Date 클래스<br/>
 * ts : Timestamp<br/>
 * a, arr : array<br/>
 * 
 * @author jshasus
 *
 */
public class Dud {
	
	/** 날짜 패턴 (정규표현식으로 변환은 아님) */
	public static String[] asPatDate = { 
			"yy/MM/dd HH:mm"
			,"yyyyMMdd"
			,"yyyy-MM-dd HH:mm"
			,"yyyy-MM-dd HH:mm:ss"
			,"yyMMdd"
			,"yyMMdd HH:mm"
			,"yyyy.MM.dd HH:mm" // 티스토리 전형적 패턴 // '.' 으로 남겨두면 Pattern 에서도 동작하는데 큰 문제는 없음 
			,"yyyy.MM.dd HH:mm:ss"
			,"yyyy.MM.dd"
			,"yyyy.MM.dd." //네이버블로그 PostTitleListAsync.nhn
			,"yyyyMMddHHmmss"
			,"yyyyMMddHHmm"
			,"yyyy.MM.dd (오전|오후) hh:mm" // 네이버 뉴스 일부 http://sports.news.naver.com/kbaseball/news/read.nhn?oid=410&aid=0000359054
			,"yyyy-MM-dd" //170329 //test10-20-1
	}; // 인용 가능한 패턴 모음
	
	
	/**
	 * 위 명령어의 약어
	 * @param s : null 일때 null 반환
	 * @return
	 * @throws Exc_Dck 
	 */
	public static Date toDate(String s) throws Exc_Dck { 
		return stringToDate(s);
	}
	

	/**
	 * * 주의 stringToDate 와 달리 "추출"에 의의가 있음. 
	 * *170311 정렬해놓고 안쓰던 오류 수정
	 * 
	 * test  5-38
	 * 
	 * 날짜 문자열이 섞여 있는 것에서 날짜 문자열만 추출
	 * ex ) 딤딤이 161210 --> 161210   
	 * @param str
	 * @return
	 * @throws Exc_Dck 
	 */
	public static String extractDateString(String str) throws Exc_Dck { 
		
		// AM PM =>  오전,오후
		str = str.replace("AM","오전");
		str = str.replace("PM","오후");
		

		// 1. 긴 패턴부터 적용하기 위한 정렬 ( 짧은 패턴이 긴 패턴에 포함되는 경우 있으므로...)
		List<String> lst = new ArrayList<String>(Arrays.asList(asPatDate));
		Collections.sort(lst, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) { // null 없음 가정
				return o2.length() - o1.length();
			}
		});
		
		System.out.println("[extractDateString] targetStr : " + str);
		
		for(String sPat : lst) {
//			LogUtil.infoLine();
//			LogUtil.info(sPat);

			// 2. 각 패턴 규칙 맞는지 확인
			String sDatePattern_regex = sPat.replaceAll("y","\\\\d").replaceAll("MM|dd|hh|HH|mm|ss","\\\\d\\\\d?");
			Matcher mat = Pattern.compile(sDatePattern_regex).matcher(str);
			
			boolean m = mat.find();
			String extractedString = null;
			
//			LogUtil.info(sDatePattern_regex + " : " +m);
			if(m == false) {
				continue;
			}
			
			extractedString = mat.group();
			
//			System.out.println("[extractDateString] datePat : " + sPat);
//			System.out.println("[extractDateString] matched regex : " + sDatePattern_regex);
//			System.out.println("[extractDateString] extractedString : " + extractedString);
				
			// 3. 추출된 문자열로 실제로 Date() 생성 테스트 후 반환
			String sDatePat = sPat.replace("(오전|오후)","a");
			SimpleDateFormat sdf = new SimpleDateFormat(sDatePat);
			
			try { 
				sdf.parse(extractedString);
				return extractedString;// 추출하여 반환함.
			} catch(ParseException e) { // 오류발생시도 다음 패턴 처리를 위해 넘김 
				//e.printStackTrace();
				// 2016.10.23 은 'yyyy.mm.dd.' 에서 한번 잡혔따가 오류 발생하고 ,
				//  적당한 'yyyy.mm.dd'를 다시 적용함 
				continue; 
			}
		}
		
		// 4. 아무것도 찾지 못했다면 Exception 으로 반환함.
		throw new  Exc_Dck("not found acceptable Date String In paramter string. '"+str+"'");
		
	}

	
	/**
	 * * extractDateString() 과 연계해서 쓰일 때가 있어 (JavtypeCicExtractor) 둘 간의 패턴 맞춰야..
	 * test  5-36, 5-38
	 * 
	 * 
	 * *161210 생성
	 * * SimpleDateFormat 은 잘못된 패턴도 변환해버리므로 이를 해결 위해 Pattern Matcher 이용함
	 * * 전역 static인 sArrPatDate를 사용 
	 * 
	 * 
	 * @param str : null 일때 null 반환
	 * @return
	 * @throws Exc_Dck 
	 */
	public static Date stringToDate(String str) throws Exc_Dck {
		if(str == null || str.trim().equals("")) return null;
		
		str = str.trim();
		
		// AM PM =>  오전,오후
		str = str.replace("AM","오전");
		str = str.replace("PM","오후");
		
		// 1. 긴 패턴부터 적용하기 위한 정렬 ( 짧은 패턴이 긴 패턴에 포함되는 경우 있으므로...)
		List<String> lst = new ArrayList<String>(Arrays.asList(asPatDate));
		Collections.sort(lst, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) { // null 없음 가정
				return o2.length() - o1.length();
			}
		});
		
//		System.out.println("[stringToDate] targetStr : " + str);

		for(String sPat : lst) { 
			
			// 2. 각 패턴 규칙 맞는지 확인
			String sDatePattern_regex = sPat.replaceAll("y","\\\\d").replaceAll("MM|dd|hh|HH|mm|ss","\\\\d\\\\d?");
			Matcher mat = Pattern.compile(sDatePattern_regex).matcher(str);
			
			boolean m = mat.matches();
			
			if(m == false) {
				continue;
			}
//			System.out.println("[stringToDate] datePat : " + sPat);
//			System.out.println("[stringToDate] matched regex : " + sDatePattern_regex);
			
			// Date() 생성 후 반환 
			String sDatePat = sPat.replace("(오전|오후)","a");
			SimpleDateFormat sdf = new SimpleDateFormat(sDatePat);
			
			try { 
				return sdf.parse(str);
			} catch(ParseException e) { // 오류발생시도 다음 패턴 처리를 위해 넘김 
				continue; 
			}
		}
		
		throw new  Exc_Dck("not found acceptable Date pattern. - '" + str + "'");
	}
	
	
	
	/**
	 * yyMMddHHmmss 로 반환
	 * @param d
	 * @return
	 */
	public static String timestamp2(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		return sdf.format(d);
	}

	/**
	 * yyMMddHHmmss 로 반환
	 * @param d
	 * @return
	 */
	public static String timestamp2() { 
		return timestamp2(new Date());
	}
	

	/**
	 * null 일때 "(DATE NULL)" 반환
	 * @param d
	 * @return
	 */
	public static String dateToYymmdd(Date d ) {
		if(d==null) {
			return "(DATE NULL)";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		return sdf.format(d);
	}
	
	
/**
 * DATETIME에서 날짜만 추출 (yyyy-MM-dd)
 * 
 * ;; null 또는 오류 시 "" 반환
 * @param date
 * @return
 */
	public static String dateToGeneralString(Date date) {
		try {
			String s ="";
			s += date.getYear() + 1900;
			s += "-";
			s += date.getMonth() +1 ;
			s += "-";
			s += date.getDate() ;
			
			return s;
		} catch(Exception e) {}
		return "";
	}
	
	

	
	/**
	 * 지정된 날짜 만큼 더함
	 * 
	 * * 참고 : 고쳐진 객체 반환  / 입력된 객체는 그대로 남음. 
	 * 
	 * 
	 * http://stackoverflow.com/questions/1005523/how-to-add-one-day-to-a-date
	 * 
	 * 
	 * * 170517
	 * 
	 * @param d
	 * @param i
	 * @return
	 */
	public static Date addDay(Date d, int i) {
		if(d == null) return null;
		
		Calendar c = Calendar.getInstance();
		
		c.setTime(d);
		c.add(Calendar.DATE, i);
		
		return c.getTime();
	}
	
	public static Date addMonth(Date d, int i) {
		if(d == null) return null;
		
		Calendar c = Calendar.getInstance();
		
		c.setTime(d);
		c.add(Calendar.MONTH, i);
		
		return c.getTime();
	}
	
	/**
	 * * [ 더 과거인 date 반환 
	 * 
	 * * null 이 있을 경우 null 이 아닌 것만 반환 
	 * 
	 * * 용도 Task_CollCio 에서 지금까지 나온 CIO 의 Date 의 date 중 가장 오래된 것만 남겨 놓는 경우
	 * 
	 * 
	 * * 170528
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static Date getMorePast(Date a, Date b) { 
		if(a ==null)  return b;
		if(b == null) return a;
		return a.compareTo(b) > 0 ? b:a;
	}
	
	
	/**
	 ** [ 더 미래인 date 반환 
	 * 
	 * 
	 * * null 이 있을 경우 null 이 아닌 것만 반환
	 * 
	 * 
	 * * 170528
	 * @param a
	 * @param b
	 * @return
	 */
	public static  Date getMoreFuture(Date a, Date b) { 
		if(a ==null)  return b;
		if(b == null) return a;
		return a.compareTo(b) < 0 ? b:a;
	}
	

	
	/**
	 * * 날짜 구간에 들어오는지 확인
	 * 
	 * bDate <= date < eDate 일때 true
	 * 
	 * * bDate, eDate 바뀌었을때에 대한 처리 없음.
	 * 
	 * * bDate or eDate 있는데 date nul  일땐 false (날짜조건 있는데, 검사 대상 없는경우..)
	 * 
	 * 
	 * * test 8-8
	 * 
	 * 170621
	 * @param bDate
	 * @param eDate
	 * @param date
	 * @return
	 */
	public static boolean dateAccept(Date bDate, Date eDate, Date date) {
		boolean rt = true;
		
		if(bDate != null && (date==null ||  bDate.compareTo(date) > 0 )) { 
			rt = false;
		}
		
		if(eDate != null && (date==null ||eDate.compareTo(date) <= 0) ) {
			rt = false;
		}
		
		
		return rt;
	}
	
	/**
	 * ;; 참고 : 문자 입력시 (ex :abcde) 오류 대신 null 반환
	 * 
	 * ;; 171229 find => match, 날짜 1개만 입력된 경우 처리
	 * 
	 * ;;170722 CmdInterpreter 에서 가져옴
	 * 
	 * 170529~170530 형태의 문자열을 배열에 나눠 담아 반환
	 * 
	 * tmp = null 일때 둘다 null 로 반환
	 * 
	 * ;; t-8-16
	 * 
	 * 
	 * @param sDateRange
	 * @return
	 */
	public static String[] parseDateRange(String sDateRange) {
		String sPastDate = null;
		String sFutureDate = null;

		if (sDateRange != null) {
			Pattern p = Pattern.compile("(\\d*)~(\\d*)");
			Matcher m = p.matcher(sDateRange);
			if(m.matches() == true) {
				sPastDate = m.group(1);
				sFutureDate = m.group(2);
				return new String[] { Dck.s.noStrToNull(sPastDate),
						Dck.s.noStrToNull(sFutureDate) };
			}
			
			// 2 날짜가 1개만 나온 경우
			// ex : 171229 => 171229~171229와 동일
			p = Pattern.compile("(\\d*)");
			m = p.matcher(sDateRange);
			if(m.matches() == true) {
				sPastDate = m.group(1);
				return new String[] { Dck.s.noStrToNull(sPastDate),
						Dck.s.noStrToNull(sPastDate) };
			}
	
		}

		return new String[] { Dck.s.noStrToNull(sPastDate),
				Dck.s.noStrToNull(sFutureDate) };
	}

	
	/**
	 * 
	 * ;; 두개의 날짜를
	 * 
	 * ;; null 인 날짜는 ""로 처리
	 * 
	 * 170722atdwAt => scr 시 사용
	 * 
	 * 
	 * @param bDate
	 * @param eDate
	 * @return
	 */
	public static String scrDate(Date bDate, Date eDate) {
		String s="";
		s+=(bDate==null?"":dateToYymmdd(bDate));
		s+="~";
		s+=(eDate==null?"":dateToYymmdd(eDate));
		return s;
	}
	


	// 이거 어디감?
//	public static String nowTimeStamp() {
//		return dateToStrDatestamp(new Date());
//	}


	/**
	 * 
	 *   null 일때 null 반환
	 * @param ciCreateDate
	 * @return
	 */
	public static String dateToYymmdd_2(Date d) {
		if(d==null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		return sdf.format(d);
	}
	
	
	/**
	 * @param d null 일때 null
	 * @return
	 */
	public static String toYymm(Date d) {
		if(d==null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
		return sdf.format(d);
	}
	
	

	public static String today_2_6digit() {
		return dateToYymmdd(new Date());
	}

	public static Date toTomorrow() {
		return addDay(new Date(), +1);
	}

	public static Date toYesterday() {
		return addDay(new Date(), -1);
	}
	


	
	/**
	 * yyMMdd HH:mm:ss 로 반환
	 * @return
	 */
	public static String timestamp() {
		return dateToString(new Date());
	}
	

	/**
	 * Date 를 일반적인 표시 방법으로 반환
	 * yyMMdd HH:mm:ss
	 * @return
	 */
	public static String dateToString() {
		return dateToString(new Date());
	}
	
	/**
	 * yyMMdd HH:mm:ss 로 반환
	 * @param d : null 일때 null 반환
	 * @return
	 */
	public static String dateToString(Date d) {
		if(d == null ) return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd HH:mm:ss");
		return sdf.format(d);
	}
	
	


	

	public static Date stringToDateTomorrow(String eDate) throws Exc_Dck {
		return addDay(stringToDate(eDate),1);
	}
	
	
	/**
	 * 위 명령의 약어
	 * @param s
	 * @return
	 * @throws Exc_Dck
	 */
	public static Date toTomorrow(String s) throws Exc_Dck  {
		return stringToDateTomorrow(s);
	}
	

	
	

	public static int lastDayOfMonth(String yearMonth) throws Exc_Dck {
		Date d = toDate(yearMonth+"01");
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	
	/**
	 * string 형 date 의 i 일 이후 를 계산
	 * 
	 * ;; 반환 yymmdd
	 * 
	 * @param sDt
	 * @param i
	 * @return
	 * @throws Exc_Dck 
	 */
	public static String addDay(String sDt, int i) throws Exc_Dck {
		return dateToYymmdd_2(addDay(toDate(sDt), i));
	}

	

	public static String toYymmdd(Date d) {
		return dateToYymmdd_2(d);
	}


	public static String toYymmdd() {
		return toYymmdd(new Date());
	}
	


	/**
	 * = dateToString()
	 * yyMMdd HH:mm:ss 로 반환
	 * @param d : null 일때 null 반환
	 * @return
	 */
	public static String timestamp(Date date) {
		return dateToString(date);
	}

	


	/**
	 * 
	 * @param d
	 * @return
	 */
	public static String toYyyymm(Date d) {
		if(d==null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(d);
	}


	public static String to8digit(Date d) {
		if(d==null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(d);
	}


	/**
	 * date 간의 동일성 비교
	 * 
	 * 둘다 null 이면 true
	 * 둘중 하나 null 이면 false
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean eq(Date d1, Date d2) {
		if(d1 == null && d2 == null) {
			return true;
		}

		if(d1 == null || d2 == null) { 
			return false;
		}


		return d1.getTime() == d2.getTime();
	}
	

	public static String date6ToDate8(String date6) throws Exc_Dck {
		return to8digit(toDate(date6));
	}	
	


	/**
	 * d 의 다음날을 반환함.
	 * 이때 d가 00시 가 아니더라도 00시로 간주한 다음날을 반환함
	 * (즉 해당일의 24시 시점을 반환)
	 * 
	 * t 2-59
	 * 
	 * 171225
	 * @param d
	 */
	public static Date tomorrowZeroHour(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set( Calendar.MINUTE, 0 );
		c.set( Calendar.SECOND, 0 );
		c.set( Calendar.MILLISECOND, 0 );
		
		c.add(Calendar.DATE, 1);
		
		return c.getTime(); 
	}
	
	
	
	/**
	 * 
	 * 
	 * ;;181225
	 * @param d
	 * @return
	 */
	public static Date zeroHour(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set( Calendar.MINUTE, 0 );
		c.set( Calendar.SECOND, 0 );
		c.set( Calendar.MILLISECOND, 0 );
		
		return c.getTime(); 
	}


	/**
	 *  mm 까지 반환
	 * 
	 * yyMMdd HH:mm
	 * @param d 
	 * @return
	 */
	public static String toStr_mm(Date d) {
		if(d == null ) return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd HH:mm");
		return sdf.format(d);
	}


	/**
	 * 
	 * t 502-3
	 * @param 오늘 작성 : 시:분만 표시<br/>
	 * 그 이전 : 190101 11:22
	 * @return
	 */
	public  static String toStr_bbs(Date d) {
		Date dNow = new Date();
		
		SimpleDateFormat sdfNow = new SimpleDateFormat("YYMMDD");
		String s1 = sdfNow.format(dNow);
		
//		SimpleDateFormat sd = new SimpleDateFormat("YYMMDD");
		String s2 = sdfNow.format(d);
		
		if(s1.equals(s2)) {
			return new SimpleDateFormat("HH:mm").format(d);
		}  else {
			return new SimpleDateFormat("yyMMdd HH:mm").format(d);
		}
	}
	

	
	/**
	 * t 502-3
	 * 
	 * @param date
	 * @return 몇초전, 몇시간전 , 몇년전 .. .
	 */
	public static String toStr_bbs_2(Date date) { 
		final int SEC = 60;
		final int MIN = 60;
		final int HOUR = 24;
		final int DAY = 30;
		final int MONTH = 12;

		long curTime = System.currentTimeMillis();
		long regTime = date.getTime();
		long diffTime = (curTime - regTime) / 1000;

		String msg = null;

		if(diffTime < SEC) {
			// sec
			msg = diffTime + "초전";
		} else if ((diffTime /= SEC) < MIN) {
			// min
//			System.out.println(diffTime);
			msg = diffTime + "분전";
		} else if ((diffTime /= MIN) < HOUR) {
			// hour
			msg = (diffTime ) + "시간전";
		} else if ((diffTime /= HOUR) < DAY) {
			// day
			msg = (diffTime ) + "일전";
		} else if ((diffTime /= DAY) < MONTH) {
			// day
			msg = (diffTime ) + "달전";
		} else {
			msg = (diffTime) + "년전";
		}


		return msg;
	}

}
