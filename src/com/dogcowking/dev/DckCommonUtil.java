package com.dogcowking.dev;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DckCommonUtil {

	/**
	 * 주어진 맵에, key를 이용하여 i 만큼 증가시킴
	 * * 맵을 카운트로 이용시 사용 
	 * @param m
	 * @param key
	 * @param i
	 * @return
	 * 
	 * test  10-28
	 */
	public static int mapCount(Map<String, Integer> m, String key, int i) { 
		Integer tmp = m.get(key);
		m.put(key, tmp==null?1:tmp+i);
		
		return m.get(key);
	}

	
	/**
	 * 숫자문자 배열 ->  Long 배열
	 * @param as
	 */
	public static Long[] toAl(String[] as) {
		if(as == null) return null;
		
		Long[] al = new Long[as.length];
		
		for(int i=0 ; i<as.length ; i++) { 
			al[i] = Long.parseLong(as[i]);
		}
		
		return al;
	}
	

	public static Long[] toAl(String sArrTheme, String splitDeter) {
		if(sArrTheme == null) { 
			return null;
		}
		return toAl(sArrTheme.split(splitDeter));
	}
	
	
	
	
	/**
	 * nul 처리 추가된 배열=>리스트
	 * 
	 * ;; Arrays.asLIst 를 사용했었으나, 수정 불가하여, 애초에 새로 생성하는 로직으로 변경 170707
	 * - java.util.ConcurrentModificationException 를 막기 위한건데 소용 없었음 ? ? 170707
	 * - synchro.. 추가 
	 * 
	 * * 용도 print 시 array 는 주소만 보이지만, list 는 목록을 보여주므로 활용하기 위해. .
	 * @param ao
	 * @return
	 */
	public static List asList(Object[] ao) {
		if(ao == null) { return null; }
		
		List lst = Collections.synchronizedList( new ArrayList());
		
		for(Object o : ao ) { 
			lst.add(o);
		}
//		return Arrays.asList(ao);
		
		return lst;
	}
	
	public static List<Integer> asList(int[] ao) {
		if(ao == null) { return null; }
		
		List<Integer> lst = Collections.synchronizedList( new ArrayList());
		
		for(int o : ao ) { 
			lst.add(o);
		}
//		return Arrays.asList(ao);
		
		return lst;
	}

	
	// 170601
	
	/**
	 * 1개짜리 Long을 배열로 만들어줌
	 * 
	 * * 예 : findCi / byCip 할때 단일 cip 는 안받으니... 배열로 .. 
	 * @param l
	 * @return
	 */
	public static Long[] toArr(long l) { 
		return new Long[] {l};
	}
	
	/**
	 * 맵을 카운트 용으로 사용 
	 * 
	 * key 가 map 에 입력된 적 없다면 1로 생성,
	 * 입력된적 있다면 기존 카운트에 1을 추가함.
	 * @param m
	 * @param key
	 */
	public static void cnt(Map m, Object key) {
		Integer cnt = (Integer) m.get(key);
		
		if(cnt == null) {
			m.put(key, 1);
		} else {
			m.put(key, cnt+1);
		}
	}

	

	
	/**
	 * 맵을 카운트 용으로 쓸때 ( Object, Integer ) 
	 * 맵에서 가장 큰 value을 가진 Key 를 반환 
	 * 
	 * @param m
	 * @return
	 */
	public static Object cntMaxKey(Map m) {
		Integer maxValue = null;
		Object maxKey = null;
		
		 for(Object o :  m.keySet() ) {
			 Integer i = (Integer) m.get(o);
			 
			 if(maxKey == null) { 
				 maxKey = o;
				 maxValue = i;
			 } else if((Integer)maxValue < i) {
				 maxKey = o;
				 maxValue = i;
			 }
			 
			 
		 }
		 
		 return maxKey;
	}
	


	/**
	 * Object[] => Long[] 
	 * 
	 * Object 는 당연히 Long 이어야.. 
	 * 
	 * null 가능
	 * 
	 * 170618
	 * @param al
	 * @return
	 */
	public static Long[] aoToAl(Object[] al) {
		if(al == null) return null;
		
		Long [] al2 = new Long[al.length];
		
		for(int i=0; i <al.length ; i++) {
			al2[i] =(Long) al[i];
		}
		
		return al2;
	}
	
	
	
	/**
	 * 
	 * @param src
	 * @return
	 */
	public static List clone(List src) { 
		List tar = new ArrayList();
		
		for(Object o : src) { 
			tar.add(o);
		}
		
		
		return tar;
	}
	

	
	
	
	/**
	 * 1개 키에 여러개 val 들어가는 경우 처리 위함.
	 * 
	 * ex : '에어포켓' 은 '구조' 카테고리, '에어포켓' 카테고리 동시에 걸려야..
	 * 
	 * 
	 * 170621
	 * @param m
	 * @param key
	 * @param val
	 * @throws ImpossibleException 
	 */
	public static void put2map(Map m, String key, Object val) throws Exc_Cu { 
		Object o = m.get(key);
		if(o==null) { 
			m.put(key, val);
		} else if(o!=null && o.getClass() == val.getClass()) {
			List l= new ArrayList();
			l.add(o);
			l.add(val);
			m.put(key, l);
		}  else if(o!=null && o.getClass() == ArrayList.class) {
			((List)o).add(val);
		} else {
			throw new Exc_Cu("맵의 val 이 null 도, 기존객체도, 리스트도 아닌 경우");
		}
	}
	

	/**
	 * object 를 요소 1개짜리 객체로..
	 * @param o
	 * @return
	 */
	public static List oneToList(Object o) {
		List l = new ArrayList();
		l.add(o);
		return l;
	}
	
	
	

	/**
	 * 글자수 0 => null 로 반환 (trim 포함) 
	 * @param iconFileName
	 * @return
	 */
	public static String zeroToNull(String str) {
		if(str == null)  return null;
		if(str.trim().length() == 0) return null; 
		return str;
	}
	
	
	
	

	/**
	 * ;; 리스트 차집합 
	 * - removeAll과 다른건, lst1의 clone 후 (새 리스트 생성) 제외시킴
	 * 
	 * 170713
	 * @param lst
	 * @param lst2
	 * @return
	 */
	public static List minus(List lst, List lst2) {
		List lst3 = Cu.clone(lst);
		lst3.removeAll(lst2);
		return lst3;
	}
	
	

	
	


	
	
	/**
	 * 
	 * @param lst
	 * @return
	 */
	public static Long[] toArr(List<Long> lst) {
		Long[] ao = new Long[lst.size()];
		ao = lst.toArray(ao);
		return ao;
	}
	
	


	
	/**
	 * ;; 2를 1로 넣음.. 중복은 넣지 않음.
	 * ;; 주의 : lst1 은 변형됨
	 * @param lst1
	 * @param lst2
	 */
	public static void addAllLong_noDupl(List<Long> lst1, List<Long> lst2) { 
		for(Long l : lst2) { 
			if(lst1.contains(l) == false) { 
				lst1.add(l);
			}
		}
	}
	
	

	

	
	/**
	 * 
	 * @param l
	 * @param l
	 * @return
	 */
	public static Long nvl(Long l, long newL) {
		return l == null ?newL : l;
	}
	
	
	public static Boolean nvl(Boolean l, boolean newL) {
		return l == null ?newL : l;
	}
	

	/**
	 * 
	 * @param page
	 * @param newL
	 * @return
	 */
	public static Integer nvl(Integer l, int newL) {
		return l == null ?newL : l;
	}
	
	
	/**
	 * 
	 * 
	 * ;; lst 를 l 로 줄여쓰기로 함 170714
	 * - 지금까지 대체 왜.? 
	 * @return
	 */
	public static List<Object> aoToLo(Object[] ao) { 
		List  l = new ArrayList();
		
		
		for(Object o : ao ) { 
			l.add(o);
		}
		
		return l;
	}
	

	/**
	 * 
	 * @param linkType
	 * @param string
	 * @return
	 */
	public static String nvl(String ori, String nullCase) {
		return ori == null ? nullCase : ori;
	}


	/**
	 * 리스트 내 중복 제거 
	 * @param lstAtdw_accepted
	 */
	public static void removeDupl(List lst) {
		for(int i =0 ;  i<lst.size()  ; i++) { 
			Object o = lst.get(i);
			for(int j=i+1 ; j<lst.size() ;) { 
				Object o2 = lst.get(j);
				if(o == o2) { 
					lst.remove(j);
				} else {
					j++;
				}
			}
		}
	}
	


	/**
	 * true일때 true
	 * false null 일때 false
	 * 
	 * ;; NPE 방지 위한 조치
	 * @param f
	 * @return
	 */
	public static boolean isTrue(Boolean f) {
		return Boolean.TRUE.equals(f);
	}

	
	/**
	 * false 일때 true
	 * true, null 일때 false
	 * @param f
	 * @return
	 */
	public static boolean isFalse(Boolean f) { 
		return Boolean.FALSE.equals(f);
	}

	
	/**
	 * Long.parseLong 과 동일
	 * ;; trim후 "" 인경우, null 인경우 => null 반환
	 * ;; 그 외는 동일하게 NumberFormatException 발생 가능
	 * @param group
	 * @return
	 */
	public static Long parseLongNull(String s) {
		if(s == null || s.trim().length() == 0) {
			return null;
		}
		return Long.parseLong(s);
	}
	
	/**
	 * 
	 * 
	 * ;;170809
	 * @param string
	 * @return
	 */
	public static String[] oneToArr(String str) {
		return new String[] {str} ;
	}

	

	
	

	
	/**
	 * lstAo 에서 i 지정시  Object[i] 만 모아 List 생성
	 * 
	 *  ;; SQL 쿼리 반환값에 사용
	 * 
	 * 
	 * ;; 170812
	 * @param lstAo
	 * @param i
	 */
	public static List lstAoToLst(List<Object[]> lstAo, int i) {
		List lst= new ArrayList();
		
		for(Object[] ao : lstAo) {
			lst.add(ao[i]);
		}
		return lst;
	}
	


	/**
	 * ll
	 * 
	 * ;; 180814
	 * @param l
	 * @return null 일때 null
	 */
	public static Long[] oneToArr(Long l) {
		if(l==null) return null;
		return new Long[] {l};
	}
	
	
	
	/**
	 * 
	 * @param o
	 * @param l
	 * @return
	 */
	public static List addToList(Object o, List l) {
		if(l==null) { 
			l = new ArrayList();
		}

		l.add(o);
		return l;
	}
	

	/**
	 * try - catch 없는 Thread.sleep
	 * @param ms
	 */
	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch(Exception e) { 
			e.printStackTrace();
		}
	}

	
	
	/**
	 * 
	 * @param m
	 * @param key
	 * @param i
	 * @return
	 */
	public static Integer cnt(Map m, Object key, Integer i) {
		Integer cnt = (Integer) m.get(key);
		
		if(cnt == null) {
			m.put(key,i);
		} else {
			m.put(key, cnt+i);
		}
		
		return (Integer) m.get(key);
	}

	

	
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	public static String exceptInfoSimple(Exception e) {
		return e.getClass().getSimpleName() + "/"+e.getMessage();
	}


	/**
	 * Exception을 String으로 변환
	 * @param t
	 * @return
	 */
	public static String makeStackTrace(Throwable t){ 
		if(t == null) return ""; 
		try{ 
			ByteArrayOutputStream bout = new ByteArrayOutputStream(); 
			t.printStackTrace(new PrintStream(bout)); 
			bout.flush(); 
			String error = new String(bout.toByteArray()); 

			return error; 
		}catch(Exception ex){ 
			return "Error occured while converting exception to String ... " + ex.getClass().getCanonicalName(); 
		} 
	} 
	
	/**
	 * makeStackTrace() 의 줄임말.
	 * 
	 * 
	 * 170615
	 * @param e
	 * @return
	 */
	public static String except(Throwable e) {
		return makeStackTrace(e);
	}
	

	/**
	 * 
	 * 둘다 null 이면 true
	 * 
	 * @param long1
	 * @param long2
	 * @return
	 */
	public static boolean eq(Long long1, Long long2) {
		if(long1 == null && long2 == null) {
			return true;
		} else if(long1 == null && long2 != null) { 
			return false; 
		} else {
			return long1.equals(long2);
		}
	}
	

	
	/**
	 * BigInteger => Integer
	 * @param bigInteger
	 * @return
	 */
	public static Integer toInteger(BigInteger bi) {
		if(bi==null) return null;
		
		return bi.intValue();
	}
	
	
	/**
	 * BigInteger => Long
	 * @param bigInteger
	 * @return
	 */
	public static Long toLong(BigInteger bi) {
		if(bi==null) return null;
		
		return bi.longValue();
	}
	

	/**
	 * intValue를 자리수 digit에 맞는 str 로 반환 
	 * 
	 * ex : 17, 5일때 00017 반환 
	 * @param intValue
	 * @param digit
	 * @return
	 */
	public static String digit(int intValue, int digit) {
		String sRt = intValue + "";
	
		while(sRt.length() < digit) { 
			sRt = "0"+sRt;
		}
		
		return sRt;
		
	}



	/**
	 * ;; select 결과가 List<Object[]> 로 나올때
	 * - 이 결과를 다시 컬럼별로 반환
	 * ex : select(lst, 0) 하면 Object[0] 에 있는 것들이 Object[] 로 변환되어 반환
	 * @param lst
	 * @param i
	 * @return
	 */
	public static Object[] select(List lst, int i) {
		List<Object> rt = new ArrayList();
		
		for(Object o  : lst) { 
			Object[] ao  = (Object[]) o;
			
			rt.add(ao[i]);
		}
	
		return rt.toArray();
	}



	/**
	 * Ojbect[] (요소는 BigInteger 인. . .) 것을 받아 Long []  으로 변경 
	 * 
	 * 
	 * @param select
	 * @return
	 */
	public static Long[] abiToAl(Object[] aobi) {
		if(aobi == null) return null;
		
		Long[] al = new Long[aobi.length];
		
		for(int i=0; i <aobi.length ; i++) {
			if(aobi[i] == null ) { al[i] = null; continue; }
			al[i] = ((BigInteger) aobi[i]).longValue();
		}
		
		return al;
	}




	
	/**
	 * 
	 * Set<Long> => Long[]
	 * 
	 * @param keySet
	 * @return
	 */
	public static Long[] toAl(Set<Long> setLong) {
//		Long[] al = new Long[setLong.size()];
//		
//		Iterator<Long> iter;
//		iter = setLong.iterator();
//		
//		int i=0;
//		while(iter.hasNext()) {
//			 al[i] = iter.next();
//		}
//		
//		return al;
		
		return setLong.toArray(new Long[setLong.size()]);
	}

	
	/**
	 * null 일때 true
	 * "" 일때 true
	 * "".trim size =0 일때 true
	 * "..." 일때 false
	 * 
	 * @param sMsg
	 * @return
	 */
	public static boolean isNoStr(String sMsg) {
		if(sMsg == null ) return true;
		if(sMsg.trim().length() == 0) return true;
		return false;
	}

	
	
	
	/**
	 * null 인경우 "" 로 반환
	 *  cf noStrToNull
	 * @param expla
	 * @return
	 * 
	 * 
	 * 171130
	 */
	public static String nullToNoStr(String s) {
		return s==null?"":s;
	}
	


	/**
	 * 
	 * 하나만 null이면 false
	 * 
	 * 
	 * ;;180813 
	 * @param long1
	 * @param long2
	 * @return
	 */
	public static boolean eqNoNull(Long long1, Long long2) {
		if(long1 == null || long2 == null) {
			return false;
		} else {
			return long1.longValue() == long2.longValue();
		}
	}

	/**
	 * 리스트 사이즈 0이면 null 반환, 그외엔 그대로 반환
	 * 
	 * 
	 * @param lst
	 * @return
	 */
	public static List size0ToNull(List lst) {
		if(lst == null) { // NPE 막아야지
			return null;
		} else if(lst.size() ==0 ) {
			return null;
		} else {
			return lst;
		}
	}


	/**
	 * al 이 size =0 일때 null 을 반환함
	 * - 나머진 그대로 반환
	 * @param al
	 * @return
	 */
	public static Long[] size0ToNull(Long[] al) {
		if(al == null) {
			return null;
		} else if(al.length == 0) {
			return null;
		} else {
			return al;
		}
	}


	/**
	 * 
	 * ;; 180326
	 * @param m
	 */
	public static void printMap(Map m) {
		for(Object o : m.entrySet()) {
			Cu.p(o);
		}
	}



	/**
	 * 숫자 나열된 str 을 배열로 변경
	 *  ex : 1,2 ,3 ,4
	 *  
	 *  ;; parseAl 에서 asNumToAl로 개명
	 *  - Cu2.p 쓰려는데 겹쳐서 불편..
	 * @param sAl
	 * @return
	 */
	public static Long[] asNumToAl(String sAl) {
		if(sAl == null) return null;
		String[] as= sAl.trim().split("\\s*,\\s*");

		Long[] al= new Long[as.length];

		int i=0;
		for(String s : as) {
			al[i] = Long.parseLong(s);
			i++;
		}

		return al;
	}


	public static Long[] lstLongToArr(List<Long> cioIds) {
		if(cioIds == null) return null;
		return cioIds.toArray(new Long[cioIds.size()]);
	}



	/**
	 * null 글자가 꼴보기 싫어
	 * 
	 * o.toString() 하든지, null 일땐 N 만 출력
	 * 
	 * @param o
	 * @return
	 */
	public static String nullToN(Object o ) {
		if(o==null) return "N";
		return o.toString();
	}

	// 180804
	/**
	 * 일반 Object nvl
	 * 
	 * - 
	 * @param o
	 * @return
	 */
	public static Object nvlo(Object o, Object o2) {
		if(o == null) return o2;
		return o;
	}


	/**
	 * arrayList 생성하기도 귀찮아 
	 * @return
	 */
	public static List<Long> newLongs() {
		return new ArrayList<Long>();
	}
	
	/**
	 * 맵을 주면  첫번째 요소부터 mapName=mapValue 순으로 해나감
	 * (0,1), 
	 * (2,3), ...
	 * 
	 *  당연히 짝수여야 
	 *  
	 *  ;; 맵 형태는 <String, String>
	 * @param as
	 * @return
	 */
	public static Map<String, String> mapEasy(String[] as ) {
		Map<String, String> m = new HashMap<String, String>() ;

		for(int i=0; i < as.length / 2 ; i++) {
			m.put(as[i*2], as[i*2+1]);
		}

		return m;

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
	

	/**
	 * 입력 배열중 null 아닌 의 갯수를 반환
	 * (파라메터 검사용)
	 * 
	 * ;; 181024
	 * @param objects
	 * @return
	 */
	public static int countNotNull(Object... objects) {
		if(objects == null) return 0;
		
		int r=0;
		for(Object o : objects) {
			if(o != null) r++;
		}
		
		return r;
	}


	public static Integer toInteger(BigDecimal bigDecimal) {
		if(bigDecimal == null) return null;
		return bigDecimal.intValue();
	}


	/**
	 * sql 결과 인 object -> BigDecimal ToInt
	 * @param object
	 * @return
	 */
	public static Integer bdToInt(Object o) {
		if(o == null) return null;
		return ((BigDecimal)o).intValue();
	}
	


	public static Integer size(List<?> lst) {
		if(lst == null ) return null;
		return lst.size();
	}



	/**
	 * ao 중 null 아닌것 세어 1일때만 true
	 * 0이거나 1보다 크면 false
	 * 
	 * ;; 옵션 파람중에 하나 들어왔는지 확인 
	 * @param ao
	 * @return
	 */
	public static boolean oneOfArr(Object... ao) {
		return countNotNull(ao) == 1; 
		
	}

	


	/**
	 * 
	 * @param o
	 * @return
	 */
	public static String toStringAllField(Object o) {
		String r ="";
		Field[] af = o.getClass().getFields();
		
		for(Field f : af) {
			r += f.getName() + " : "; 
			try {
				r+= f.get(o); 
			} catch (IllegalArgumentException e) {
				r += "IllegalArgumentException";
			} catch (IllegalAccessException e) {
				r += "IllegalAccessException";
			}
			
			r+= "\r\n";
		}
		
		return r;
	}
	





	/**
	 * ;; 최종적으로 폐기
	 * - 여기선 검사대상 메서드의 param name 은 알 수 없어
	 * arg0,1,... 로 표시될 뿐.
	 * 
	 * 그냥 값 nullCheck 하는게 나아
	 * 
	 * --------------------
	 * ;; 현 매서드를 호출했던 메서드에 null 이 있는지 확인하여 오류, 반환
	 * - ao 에 파라메터 내용은 직접 
	 * 
	 * ;; 주의 : 전달받은 파라메터 값이,  검사 대상인 메서드의 파라메터 정의 순서와 다르면 
	 * 메서드를 찾지 못해 오류가 발생됨. 그러나 예외를 던지지 않고 print 만 하고 종료됨.
	 * 
	 *  ;; 기각 : 
	 *  ao 의 값을 순회하며 파람의 타입을 알아내는 방법은
	 *  값이 null 로 넘어오면 type 자체를 알 수가 없으니까 (NPE 발생) 포기
	 * 
	 * - ClassNotFoundException NoMethodException 은 printStack.. 만하고 버려짐
	 * 
	 * 181231
	 */
//	public static void methodNullCheck(Object... ao)  throws NullPointerException {
//		Class c;
//		Method m = null;
//		int i=0;
//		
//		// 1 param Type 만들기 -폐기
//		
//		
//		// 2 메서드 가져오기 - 굳이 메서드 가져오는건 
//		// 파라메터 표시대상 메서드에서 쓰인 파라메터 이름을 알 수 없기 때문...  
//		i=0;
//		for(StackTraceElement s : Thread.currentThread().getStackTrace()) {
//			if(i == 2) {
//				try {
//					c = Class.forName(s.getClassName() );
////					m  = c.getMethod(s.getMethodName(), pTypes ); // 이 방법 안돼.. 
//
////					Cu2.p(Cu2.asList(c.getMethods()));
//					
//					// 2-1 호출한 메서드를 찾음 - 이름이 갖고, 파람 갯수가 같은 것 
//					// 파람 갯수가 같고, null 이 아닌 '(넘겨받은)파람값' 의 type 이 일치한다면 해당 메서드로 간주함.
//					Method[] ms = c.getDeclaredMethods();
//					
//					for(Method tmpM : ms) {
//						Cu2.p(tmpM.getName());
//						Cu2.p(Cu2.asList(tmpM.getParameters()));
//						Cu2.p(Cu2.asList(tmpM.));
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//					return;
//				}
//				break;
//
//			}
//			i++;
//		}
//		
//		// 3 파람별로 Null Check
//		String sMsg ="";
//		Parameter[] params = m.getParameters();
//		for(i=0; i < ao.length ;i++) { 
//			Cu2.p(params[i].getName() + " : " + ao[i]);
//		}
//		
		
//	}
	
	
	



	
	
	/**
	 * 넘겨받은 값에 null 이 섞여 있을 경우 NPE 던짐
	 * - 각 값의 타입과 값을 메시지에 담음.
	 * - null 인 경우 type 을 알 수 없지만 몇번째 파람인지는 알 수 있어.
	 *  
	 * @param ao
	 * @throws NullPointerException
	 */
	public static void nullCheckArr(Object... ao) throws NullPointerException {
		String sMsg = "";
		boolean nullFound = false;
		
		int i=0;
		for(Object o : ao) {
			sMsg += "[" +i + "]" + "= ";
			
			if(o == null) { 
				nullFound = true;
				sMsg+= " null\r\n";;
			} else { 
				sMsg += o.getClass().getSimpleName() + " 타입 / 값 : "+o + "\r\n";
			}
			i++;
		}
		
		if(nullFound) { 
			throw new NullPointerException(sMsg);
		}
	}
}
