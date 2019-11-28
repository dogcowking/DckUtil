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
