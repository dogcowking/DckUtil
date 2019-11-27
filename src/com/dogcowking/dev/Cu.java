package com.dogcowking.dev;

	import java.io.BufferedOutputStream;
	import java.io.BufferedReader;
	import java.io.ByteArrayOutputStream;
	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.FileOutputStream;
	import java.io.FileReader;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.PrintStream;
	import java.io.UnsupportedEncodingException;
	import java.lang.reflect.Field;
	import java.math.BigDecimal;
	import java.math.BigInteger;
	import java.net.URLEncoder;
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.Calendar;
	import java.util.Collection;
	import java.util.Collections;
	import java.util.Comparator;
	import java.util.Date;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	import java.util.Set;
	import java.util.Map.Entry;
	import java.util.regex.Matcher;
	import java.util.regex.Pattern;
	 
	import javax.servlet.http.HttpServletRequest;

	import org.apache.commons.io.IOUtils;


	/**
	 * ;; 190702 dateForBbs 부터 원본과 달라짐 반영 필요
	 * 
	 * 
	 * ;; 190423 cu2 -> cu 로 개명
	 * - 기존 cu 와 합쳐야 함.
	 * ;; 기존 Cu + CommonUtil 합치고, cidog 관련 메서드는 모두 삭제한 버전
	 * - 190307
	 */
	public class Cu {
		
	
	
		
		
	

		
		
		/**
		 * 굳이 만들필요는 없지만, CommonUtil 기능에 TimeChecker 포함되게 하기 위해
		 * 선언적 의미로 만듦.
		 * *161230
		 * @return
		 */
		public static TimeChecker createInstance_timeChecker() { 
			return new TimeChecker();
		}
	

		/**
		 * 
		 * @param lstAo
		 */
		public static void printList_sqlResult(List<Object[]> lstAo) {
			LogUtil.infoLine();
			LogUtil.info("size : " + lstAo.size());
			for(Object o : lstAo) {
				Object[] ao = (Object[])o;
				for(Object o2 : ao) {
					if(o2==null ) { 
						System.out.print("(NULL)");
					} else {
						System.out.print(o2.toString());
					}
					System.out.println(" ");
				}
				System.out.println("");
			}
			LogUtil.infoLine();
		}
		
		/**
		 * 
		 * @param lstAo
		 */
		public static String toString_sqlResult(List<Object[]> lstAo) {
			LogUtil.infoLine();
			StringBuilder sb= new StringBuilder();
			sb.append("-----------------------------------------------\r\n");

			LogUtil.info("size : " + lstAo.size());
			for(Object o : lstAo) {
				Object[] ao = (Object[])o;
				for(Object o2 : ao) {
					if(o2==null ) { 
						sb.append("(NULL)");
					} else {
						sb.append(o2.toString());
					}
					sb.append(" ");
				}
				sb.append("\r\n");
			}
			sb.append("-----------------------------------------------\r\n");

			return sb.toString();
		}
		
		
		/**
		 * URL 에서 oid=XX 추출하기
		 * (네이버뉴스)
		 * 
		 * * 170322
		 * @param s
		 * @return 무조건 1개만 반환, 없으면 null 반환
		 */
		public static String regex_oid(String s) { 
			if(s==null) { return null; }
			
			String oid=null;
			
			// 1. oid 추출 후에, 
			Pattern p =  Pattern.compile("oid=(\\d+)");

			Matcher m = p.matcher(s);

			if(m.find() == true) { 
				oid= m.group(1);
			}


			return oid;
		}
		
	
		
		
		
		
		public static String[] ciTextFileToString(long cioId) throws FileNotFoundException, IOException {
			return ciTextFileToString("./src/test/java/com/cidog/irs/test/sample/"+cioId+".txt");
		}
		
		
		/**
		 * [0] CI ID
		 * [1] URL
		 * [2] 제목
		 * [3] 내용
		 * 
		 * @param sFilePath
		 * @return
		 * @throws IOException 
		 * @throws FileNotFoundException 
		 */
		public static String[] ciTextFileToString(String sFilePath) throws FileNotFoundException, IOException {
			File f = new File(sFilePath);
			String[] as= new String[4];
			
			
			BufferedReader br = new BufferedReader(new FileReader(f));
			
			String sLine;
			int cnt=0;
			StringBuilder sb = new StringBuilder();
			
			while((sLine=br.readLine())!=null) {
				if(cnt<3) { 
					as[cnt] = sLine;
				} else { 
					sb.append(sLine);
				}
				cnt++;
				
			}
			
			as[3] = sb.toString();
			
		
			return as;
		}
		
	

		
	
		

	
		

		
		
		/**
		 * text 파일 불러오기
		 * @param filePath
		 * @return
		 * @throws IOException 
		 */
		public static String loadText(String sFilePath ) throws IOException { 

			File f = new File(sFilePath);
			FileInputStream fis = new FileInputStream(f); 
			String s = IOUtils.toString(fis);
			
			
			return s;
		}
		
		
		
		
		
//		/**
//		 * 
//		 * 주어진 i 의 크기에 맞춰서 반환 - byte 크기에 맞춤..
//		 * 
//		 * @param s
//		 * @param i
//		 * @return
//		 */
//		public static String fitStr(String s, int i) {
//			int byteCnt;
//			
//			 for(int i=0 ; i <s.length() ; i++) { 
//				 
//			 }
//		}
		
		
		


		

	

		
		
		static ThreadLocal<Integer > tl_pcnt = new ThreadLocal<Integer>();
		
		
		/**
		 * print count
		 */
		public static void pcnt() {
			Integer i = tl_pcnt.get();
			i = Cu.nvl(i, 0);
			i++;
			
			p("// COUNT " + i);
			
			tl_pcnt.set(i);
			
		}
		


		public static void t_chkRegEx(String sPat, String s) {
			Pattern p = Pattern.compile(sPat);
			Matcher m = p.matcher(s);
			
			Cu.p(sPat + "<="+s+" : Match-" + m.matches() +"/find-"+(m.find()==true?"true("+m.group(0)+")":"false"));
		}

		
		
		
		
		public static void append(File f, String s) throws Exception {
				InputStream is = IOUtils.toInputStream(s);
				BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(f, true/*append*/));
				
				IOUtils.copy(is, os);
				os.flush();
				os.close();
				is.close();
					
		}
		
		


	


		public static void exceptParam(String msg) throws Exc_Cu {
			throw new Exc_Cu(msg);
		}


	




		public static void wait(String string) {
			wait(string, 3);
		}


	
		





		/**
		 * 
		 * @param string
		 * @param string2
		 * @return
		 * @throws IOException 
		 * @throws FileNotFoundException 
		 */
		public static String fileToString(String fileName, String encoding) throws FileNotFoundException, IOException {
			byte[] ab;

			//1 
			ab =IOUtils.toByteArray(new FileInputStream(fileName));
			String tmp = new String(ab, encoding);
			return tmp ;
		}


		public static void encoding(String oriFile, String oriEncoding, String newEncoding,
				String newFile) throws IOException {

			String tmp = fileToString(oriFile, oriEncoding);
			byte[] ab= tmp.getBytes(newEncoding);

			FileOutputStream fos = new FileOutputStream(newFile);

			fos.write(ab);
			fos.flush();
			fos.close();
		}




		/**
		 * ex : 벵에돔 , 낚시 
		 * "벵에돔"+"낚시"
		 * 
		 * ;; 주의 : '벵에돔 낚시'가 들어와도 <match.. 자연어 처리 모드> 에서는 
		 * 벵에돔 or 낚시 로 인식 될 수 있지만,
		 * - 여기선 " "를 붙이니 한 단어로 인식됨
		 * 
		 * ;; 개선방향 : 이상한 거 내보내다가 alterAt 시 큰 문제 될 수 있으니 좀더 제한을 강화하는게...
		 * (각 term 이 비어있으면 안된다든지 . . . )
		 * 
		 * t 2-31
		 * 
		 * @param titleTerm null 일때 null 반환 / length =0 일때 null 반환
		 * @return
		 * @throws InsufficientParamException 
		 */
		public static String toSql_MatchBooleanMode(String[] titleTerm) throws Exception {
			if(titleTerm == null) return null;
			if(titleTerm.length ==0  ) return null;

			String s= "";
			for(int i =0 ; i<titleTerm.length ; i++ ) {
				String s2 = titleTerm[i];

				s+= " +";

				if("*".equals(s2.substring(s2.length() -1)) ) {
					if(s2.matches("[^ ]+") == true) { // 띄어쓰기 없어야 "*" 가능
						s+= s2;
					} else {
						throw new Exception("띄어쓰기와 * 은 함께 사용될 수 없음 : " + s);
					}
				} else {
					s += "\""+s2+"\"";
				}



			}


			return s;
		}



		/**
		 * 
		 * @param s
		 * @param sPath
		 * @throws IOException 
		 * @throws FileNotFoundException 
		 */
		public static void stringToFile(String s, String sPath) throws FileNotFoundException, IOException {
			FileOutputStream fos = new FileOutputStream(sPath);
			IOUtils.write(s, fos);
			fos.flush();
			fos.close();
		}



	




		public static void map() {
			// TODO Auto-generated method stub

		}


//		/**
//		 * 만든거 안까먹으려면 여기에 넣어줘야 지 
//		 * 
//		 * ;;180818
//		 */
//		public static ListMap listMap() {
//			return new ListMap();
//		}



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
		
	}
}
