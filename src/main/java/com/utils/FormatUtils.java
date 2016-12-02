package com.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class FormatUtils {

	/**
	 * object转化成string，空不转化,去掉首尾空格
	 * @param ob
	 * @return
	 */
	public static String formatToStringTrim(Object ob){
		if(ob == null )return null;
		return ob.toString().trim();
	}
	public static Date formatToDate(Object ob){
		if(ob == null || StringUtils.isBlank(ob.toString()))return null;
		try {
			return (Date)ob;
		} catch (Exception e) {
			System.out.println(ob);
			e.printStackTrace();
		}
		return null;
	}
	public static Integer formatToInteger(Object ob){
		if(ob == null || StringUtils.isBlank(ob.toString()) || !StringUtils.isNumeric(ob.toString()))return null;
		try {
			return Integer.parseInt(ob.toString().trim());
		} catch (Exception e) {
			System.out.println(ob);
			e.printStackTrace();
		}
		return null;
	}
	public static String formatBigDecimalToInteger(Object ob,int scale){
		if(ob == null || StringUtils.isBlank(ob.toString()))return null;
		try {
			if( ob instanceof BigDecimal){
				return BigDecimalUtils.toStringScale((BigDecimal)ob	, scale, null, RoundingMode.HALF_UP);
			}
			return null;
		} catch (Exception e) {
			System.out.println(ob);
			e.printStackTrace();
		}
		return null;
	}
	
	
}
