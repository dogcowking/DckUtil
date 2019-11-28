package com.dogcowking.dev;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>Dck Util String</b><br/>
 * @author jshasus
 *
 */
public class Dus {
	
	/**
	 * 
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public static String toValid3ByteUTF8String(String s) {
		final String LAST_3_BYTE_UTF_CHAR = "\uFFFF";
		final String REPLACEMENT_CHAR = "\uFFFD"; 

		
		final int length = s.length();
	    StringBuilder b = new StringBuilder(length);
	    for (int offset = 0; offset < length; ) {
	       final int codepoint = s.codePointAt(offset);

	       // do something with the codepoint
	       if (codepoint > LAST_3_BYTE_UTF_CHAR.codePointAt(0)) {
	           b.append(REPLACEMENT_CHAR);
	       } else {
	           if (Character.isValidCodePoint(codepoint)) {
	               b.appendCodePoint(codepoint);
	           } else {
	               b.append(REPLACEMENT_CHAR);
	           }
	       }
	       offset += Character.charCount(codepoint);
	    }
	    return b.toString();
	}
	
	
	

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String spaceToNbsp(String str)
	{
		String rt;

		Pattern p = Pattern.compile(" [ ]+"); //\s 는 \t \r\n을 포함하므로 쓸 수 없음..
		Matcher m = p.matcher(str);

		StringBuffer sb= new StringBuffer();

		int i=0;
		while(m.find())
		{
			m.appendReplacement(sb,m.group().replaceAll("\\s", "&nbsp;"));
		}
		m.appendTail(sb);

		return sb.toString();
	}

	/**
	 * 
	 * ;;; 171004 url => 링크로 전환 추가
	 * 
	 * 
	 * * 특수문자 & < > 처리
	 * * 공백처리
	 * * 개행 -> <br/>
	 * * 161211 확인 
	 * @param str
	 * @return
	 */
	public static String textToProperHtml(String str)
	{
		if(str == null) return null;
		str = str.replace("&", "&amp;");  // replace 순서가 중요함.. ex) & 치환후 -> 후에 & 들어가는것..
		str = str.replace("<", "&lt;");
		str = str.replace(">", "&gt;");
		str = spaceToNbsp(str);
		str = str.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		str = str.replace("\"","&quot;");
		str = str.replace("\n", "<br/>"); //\r\n이 아니고..??
		
		// link 변경 작업
		str = htmlUrlToLink(str);
		
		return str;
	}
	

	/**
	 * IP 주소를 123.456.789.3 -> 123.456.*.3 꼴로 바꿈.
	 * @param ipAddress
	 * @return
	 */
	public static String ipAddressBlind(String ipAddress) {
		String rt="";
		//			System.out.println(ipAddress + " will blinded");
		try {
			Pattern p = Pattern.compile("\\.");
			String[] arrStr = p.split(ipAddress);

			int count = 0 ;
			if(arrStr.length < 4) { return ipAddress; } 
			for(String s : arrStr) {
				System.out.println(s);
				if(count <2) {
					rt += s+"."; 
				} else if(count ==2 ) {
					rt += "***.";
				} else if(count ==3) {
					rt += s;
				} else { return ipAddress; }

				count++;

			}
		} catch(Exception e) {
			return ipAddress;
		}

		return rt;
	}
	
	
	
	/**
	 * 
		출처: http://gun0912.tistory.com/65 [박상권의 삽질블로그]
	 * @param name
	 * @param firstValue
	 * @param secondValue
	 * @return
	 */
	public static final String selectJosa(String word, String withBatchim, String withoutBatchim) throws Exc_Dck {
		char lastCharacter = word.charAt(word.length() - 1); // 한글의 제일 처음과 끝의 범위밖일 경우는 오류
		if (lastCharacter < 0xAC00 || lastCharacter > 0xD7A3) {  
			throw new Exc_Dck("마지막 글자가 한글이 아님");
		} 
		String seletedValue = (lastCharacter - 0xAC00) % 28 > 0 ? withBatchim : withoutBatchim; 
		return word+seletedValue; 
	}
	
	
	/**
	 * 마지막 글자 추출
	 * @param word
	 * @return
	 */
	public static char lastChar(String word) {
		return word.charAt(word.length()-1);
	}
	
	/**
	 * 마지막 글자가 받침 있는 한글이면 true
	 * 아니면 false(받침없는 한글 and 한글 아닌경우)
	 * @param word
	 * @return
	 */
	public static boolean haveBatchim(String word) { 
		char lastChar = lastChar(word);
		return (lastChar  - 0xAC00) % 28 > 0; 
	}
	
	
	/** 이메일 패턴 반환 
	 * test 5-55-1
	 * */
	public static String regex_emailPat() { 
//		return "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";' //단독으로는 잘 되는 데 , 문자열 섞인 상태에서는 잘 안됨.. ^ 때문인듯
		//sPat = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$"; //http://darkhorizon.tistory.com/259
		
		 return "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+";//http://moonstoneit.blogspot.kr/2013/07/email-check-regular-expression.html
	}
	
	
	/**
	 * 
	 * *170602 trim 포함 
	 * 
	 * 입력값이 "" 이면 null로
	 * 나머진 그대로 ( null 이면 null 반환, 값 있으면 있는대로 반환 ) 
	 * 
	 * @param sPastDate
	 */
	public static String noStrToNull(String str) {
		if(str == null) return null;
		if("".equals(str.trim())) return null;
		
		return str;
	}
	

	/**
	 * 기존 substr 에서 
	 * - null 처리
	 * - 주어진 글자보다 원본이 적은경우 오류 
	 * 에 대한 처리를 한것
	 * @param s
	 * @param i
	 * @return
	 */
	public static String substr(String s, int i) {
		if(s == null) { 
			return s;
		}
		
		if(i > s.length()) { 
			return s;
		}
		
		return s.substring(0, i);
	}
	

	/**
	 * 티스토리 주소 정규화
	 * 
	 * @param url
	 * @return "http://이름.tistory.com" 으로 반환함. 
	 * @throws ParseException 
	 */
	public static String normalizeUrl_tistory(String url) throws ParseException { 
		String sPat = "(http|https)://(\\w+)\\.tistory\\.com/?";
			
		Matcher m = Pattern.compile(sPat).matcher(url);
		
		if(m.matches()) {
			return "http://"+m.group(2)+".tistory.com";
		} else {
			throw new ParseException("this pattern doen't match tistory url : "+ url, 0);
		}
	}
	
	/**
	 * 티스토리 블로그 아이디  추출하기
	 * @param url
	 * @return
	 * @throws ParseException
	 */
	public static String extractWriter_tistory(String url) throws ParseException { 
		String sPat = "(http|https)://(\\w+)\\.tistory\\.com.*";
			
		Matcher m = Pattern.compile(sPat).matcher(url);
		
		if(m.matches()) {
			return m.group(2);
		} else {
			throw new ParseException("this pattern doen't match tistory url : "+ url, 0);
		}
		
	}
	
	
	
	/**
	 * * 규칙
	 * 네이버 뉴스 타이틀 고려함
	 * // 구두점 3개 연속은 구분자가 되지만, 구두점 1개는 구분자로 하지 않음 170105
	* // '-'는 하지 않음 (ex : Geun-hye) 
	 * 사용처 : WordCounter 등
	 * 
	 * *170105 생성
	 * @param s
	 * @return
	 */
	public static String[] splitWord(String s) {
		 return s.split("···|\\.\\.\\.|[/:\\s“\\[\\]\\|”\"'‘’,\\(\\)…·\\?<>!]+");
		
	}
	
	public static String strOnly(Object o) {
		if(o==null) { return null; } 
		return (o instanceof String?(String)o : o.toString());
	}
	

	
	/**
	 * string 을 받아서 byte 로 변환, i~j 까지의 
	 * 
	 * 100 바이트 일때 0,99 까지 이므로 
	 * i >=  0
	 * j <= 99 임
	 * 
	 * ;; 범위 벗어나도 오류는 없음.
	 * @param str
	 * @param i
	 * @param j
	 * @return
	 */
	public static String subStringByte(String str, int i, int j) {
		byte[] ab = str.getBytes();
		
		i = i<0?0:i;
		j = j>=ab.length?ab.length-1:j;
		
		ab = Arrays.copyOfRange(ab, i, j);
		
		return new String(ab);
	}

	
	/**
	 * textToProperHtml 의 줄임말
	 * 
	 * 
	 * ; 171004 url => 링크로 전환 추가
	 * 
	 * 170923
	 * @param s
	 * @return
	 */
	public static String toHtml(String s) {
		return textToProperHtml(s);
	}
	

	/**
	 * 
	 * @param sUrl
	 * @return
	 */
	public static boolean isUrl(String sUrl) { 
		String sPat = "((http|ftp|https):\\/\\/)[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
		
		Pattern p  =Pattern.compile(sPat);
		Matcher m = p.matcher(sUrl);
		
		return m.matches();
	}
	
	
	/**
	 * 주의 : 이건 html 에 해당 안함.  & 같은 경우 html 에선 &amp; 로 치환되는데, 여기에 대한 고려는 없음.
	 * 
	 * t 2-18
	 * @param sHtml
	 * @return
	 */
	public static String urlToLink(String sHtml) { 
		String sPat = "((http|ftp|https):\\/\\/)[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
		
		return sHtml.replaceAll(sPat, "<a target='blank' href='$0'>$0</a>");
	}
	
	/**
	 * ;; urlToLink 에서 &amp; 를 고려함.
	 * 
	 * t-2-19
	 * @param sHtml
	 * @return
	 */
	public static String htmlUrlToLink(String sHtml) { 
		String sPat = "((http|ftp|https):\\/\\/)[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b(([-a-zA-Z0-9@:%_\\+.~#?//=]|&amp;)*)";
		
		return sHtml.replaceAll(sPat, "<a target='blank' href='$0'>$0</a>");
	}
	
	/**
	 * str의 마지막 이 개행문자인 경우 삭제함.
	 * 
	 * ;; t 2-23
	 * @param s
	 * @return
	 */
	public static String removeLastCr(String s) {
		if(s == null)  return null;
		
		if(s.length() >= 2 && s.substring(s.length()-2, s.length()).equals("\r\n") == true) {  
			return substr(s, s.length()-2); 
		}
		
		if(s.length() >= 1 &&  s.substring(s.length()-1, s.length()).equals("\n") == true) {  
			return substr(s, s.length()-1);
		}
		
		return s;
	}

	/**
	 * trim 인데 null 고려
	 * @param word
	 * @return
	 */
	public static String trim(String word) {
		if(word == null) return null;
		return word.trim();
	}
	


	/**
	 * ;; 이/가 조사 추가 
	 * s가 받침 있으면 +이
	 * 없으면 +가
	 * 를 해서 반환
	 * 
	 * ;; 개선 : 영문자는 ?  
	 * @param s
	 * @return
	 */
	public static String josa_ie(String s) {
		if(s==null ) return null;
		
		if(haveBatchim(s) == true) return s+"이";
		else return s+"가";
	}


	/**
	 * 받침 있으면 +과
	 * 없으면 +와 
	 * @param stringSimple
	 * @return
	 */
	public static String josa_wa(String s) {
		if(s==null ) return null;

		if(haveBatchim(s) == true) return s+"과";
		else return s+"와";
	}

	public static String josa_eul(String s) {
		if(s==null ) return null;

		if(haveBatchim(s) == true) return s+"을";
		else return s+"를";
	}

	public static String josa_eun(String s) {
		if(s==null ) return null;

		if(haveBatchim(s) == true) return s+"은";
		else return s+"는";
	}
	

	/**
	 * 
	 * ;; 가&&나 
	 * -> (?=.*가)(?=.*나) 로 regExp 로 바꿈.
	 * @param s
	 */
	public static String andSignToRegEx(String s) {
		String sNewWord ="";

		if(s.split("&&").length <= 1) {
			return s;
		}

		for(String tmpS : s.split("&&") ) { 
			sNewWord+= "(?=.*"+tmpS+")";
		}

		return sNewWord;

	}
	

	/**
	 * null 가능한 split
	 * @param word
	 * @param string
	 * @return
	 */
	public static String[] splitStr(String str, String splitRegexp) {
		if(str == null) return null;
		return str.split(splitRegexp);
	}



	/**
	 * System.getProperty("line.separator"); 반환하되, 
	 * 반환되지 않으면 윈도우의 개행문자인 \r\n 을 반환 
	 * 
	 * 180125
	 * @return
	 */
	public static String cr() {
		String cr = System.getProperty("line.separator");
		if(cr == null || cr.length() ==0 ) {
			cr = "\r\n";
		}
		return cr;
	}


	/**
	 * StrArr 받아서 모두 or 조건 (가|나|다|...)
	 * @param josa
	 * @return
	 */
	public static String patToOr(String[] asJosa) {
		String sRt = "";
		int i=0;
		for(String s : asJosa) { 
			sRt += s;

			if(i<asJosa.length -2) {
				sRt += "|";
			}

			i++;
		}

		return sRt;
	}
	
	/**
	 * 조사 모음
	 * 
	 * @param batchim true => 받침있을때 쓰는 조사 + 받침 무관한 조사 반환
	 *  false => 받침 없을때 쓰는 조사 + 받침 무관한 조사 반환
	 * @return
	 */
	public static String[] josa(boolean batchim) {
		// 받침 무관
		String[] asBatchim_notCare = "께서 에서 서 의 에 에게 께 한테 에게서 에서부터 보다 로 로서 로써 같이 처럼 도 로부터 부터 까지 마저 조차".split(" ");

		if(batchim == true) { // 받침 O
			return addAs(asBatchim_notCare, "이 을 으로 으로서 으로써 이라고 이여 이시여 아 과 이며 이랑 은 으로부터 이란 이든지 이나마 이야말로".split(" ")) ;
		} else { // 받침 X
			return addAs(asBatchim_notCare, "가 를 라고 여 시여 야 와 며 랑 는 란 든지 나마 야말로".split(" ")) ;
		}

	}


	/** 가운뎃점 */
	public static char ch_midDot() {
		return '·';
	}
	




	/**
	 * 첫번째 라인을 0으로 하여 지정된 라인의 위치를 가져옴
	 * 
	 * ;; 윈도우즈에서 \r\n 이므로 \n의 다음이 
	 * - 리눅스에서 \n 이므로 무사히 작동함.
	 * - substring 이용시는 trim() 이 필요 ( 개행문자 포함된 위치이므로 ) 
	 * 
	 * 해당 라인이 존재하지 않으면 null 반환
	 * @param s
	 */
	public static Integer getLinePosition(String s, int line) {
		int nowLine =0;
		for(int i=0; i<s.length() ; i++) {
			if(nowLine == line) {
				return i;
			}
			
			char c = s.charAt(i);
			
			if(c == '\n') {
				nowLine++;
			}
		}
		
		return null;
	}
	

	/**
	 * num 만큼 str 을 반복해서 반환
	 * @param num 
	 * @param string
	 * @return
	 */
	public static String repeat(int num, String string) {
		String s ="";
		for(int i=0; i<num; i++) {
			s+=string;
		}
		return s;
	}
	


	/**
	 * arr String 더하기
	 * 
	 * object[] 전환이 힘들어서 String[] 으로 구현
	 * @param as1
	 * @param as2
	 * @return
	 */
	public static String[] addAs(String[] as1, String[] as2) {
		String[] r = new String[as1.length + as2.length];
		
		for(int i=0; i<as1.length + as2.length ; i++) {
			if(i < as1.length) {
				r[i] = as1[i];
			} else {
				r[i] = as2[i-as1.length];
			}
		}
		return r;
	}
	


	/**
	 * 개행문자 반환 (system)
	 * @return
	 */
	public static String getCr() { 
		return System.getProperty("line.separator");
	}

	/**
	 * 
	 * 
	 * ;; 주의 : 기존 str 에 덧붙여서 '반환 '하는 거지, 기존 객체 수정해주는게 아냐
	 * @param sMsg
	 * @return
	 */
	public static String addCr(String sMsg) {
		return sMsg + getCr();
	}
	
	
	

	/**
	 * 파일이름에서 확장자 지우기
	 * abcdef.ext 에서 abcdef 를 분리
	 * 
	 * 정규식으로 확장자만 떼느거라 경로 있는 경우에 대해 고려 불가 
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getNameFromFileName(String fileName) {
		Pattern p  = Pattern.compile("(.*)(?:\\.(.*))");
		
		Matcher m = p.matcher(fileName);
		if(m.matches() == false) { 
			return null;
		}
		
		return m.group(1);
	}
	
	
	/**
	 * 파일이름에서 확장자만 가져오기
	 *  abcdef.ext 에서 ext 를 분리
	 * 
	 * 정규식으로 확장자만 떼느거라 경로 있는 경우에 대해 고려 불가 
	 * @param fileName
	 * @return
	 */
	public static String getExtFromFileName(String fileName) {
		Pattern p  = Pattern.compile("(.*)(?:\\.(.*))");
		
		Matcher m = p.matcher(fileName);
		if(m.matches() == false) { 
			return null;
		}
		
		return m.group(2);
	}


	// 190423

	/**
	 * mysql 에  4byte 문자 로 오류 나는 경우 사용
	 * 
	 * t 2-3
	 * 
	 * https://stackoverflow.com/questions/24840667/what-is-the-regex-to-extract-all-the-emojis-from-a-string
	 * 
	 * @param input
	 * @return
	 */
	public static String mysqlSafe(String input) {
		if (input == null) return null;
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < input.length(); i++) {
			if (i < (input.length() - 1)) { // Emojis are two characters long in java, e.g. a rocket emoji is "\uD83D\uDE80";
				if (Character.isSurrogatePair(input.charAt(i), input.charAt(i + 1))) {
					i += 1; //also skip the second character of the emoji
					continue;
				}
			}
			sb.append(input.charAt(i));
		}

		return sb.toString();
	}
}
