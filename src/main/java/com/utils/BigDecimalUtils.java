package com.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang.StringUtils;

public class BigDecimalUtils {

	
	/**
	 * 比较是否相等
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean compareTo1Eq2(BigDecimal d1,BigDecimal d2){
		if( d1 == null ){
			if( d2 == null )return true;
			return false;
		}
		if( d2 == null )return false;
		return d1.compareTo(d2) == 0 ;
	}
	
	/**
	 * 比较d1<d2（其中为空返回false）
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean compareTo1Lt2(BigDecimal d1,BigDecimal d2){
		if( d1 == null )return false;
		if( d2 == null )return false;
		return d1.compareTo(d2) < 0 ;
	}

	/**
	 * 比较d1>d2（其中为空返回false）
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean compareTo1Gt2(BigDecimal d1,BigDecimal d2){
		if( d1 == null )return false;
		if( d2 == null )return false;
		return d1.compareTo(d2) > 0 ;
	}
	
	
	/**
	 * 转String，并四舍五入RoundingMode.HALF_UP,如果value=null则返回默认值
	 * @param value
	 * @param scale
	 * @param defaultValue
	 * @return
	 */
	public static String toStringScale(BigDecimal value,int scale,String defaultValue){
		if(value == null ) return defaultValue;
		return value.setScale(scale,RoundingMode.HALF_UP).toString();
	}
	

	/**
	 * 转String,如果value=null则返回默认值
	 * @param value
	 * @param scale
	 * @param defaultValue
	 * @param mode
	 * @return
	 */
	public static String toStringScale(BigDecimal value,int scale,String defaultValue,RoundingMode mode){
		if(value == null ) return defaultValue;
		return value.setScale(scale,mode).toString();
	}
	
	/**
	 * String转bigdecimal
	 * @param value
	 * @param scale
	 * @param defaultValue
	 * @return
	 */
	public static BigDecimal toBigDecimalScale(String value,int scale,BigDecimal defaultValue){
		if(StringUtils.isBlank(value))return defaultValue;
		try {
			return new BigDecimal(value).setScale(scale,RoundingMode.HALF_UP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}

	public static BigDecimal toBigDecimalScale(Object value,int scale,BigDecimal defaultValue){
		if(value == null )return defaultValue;
		return toBigDecimalScale(value.toString(), scale, defaultValue);
	}
	
	/**
	 * 乘积，并四舍五入RoundingMode.HALF_UP,如果value=null or two则返回默认值
	 * @param value
	 * @param scale
	 * @param defaultValue
	 * @return
	 */
	public static String multiplyScaleToStr(BigDecimal value, BigDecimal two,int scale,String defaultValue){
		if( value == null || two == null ) return defaultValue;
		return value.multiply(two).setScale(scale,RoundingMode.HALF_UP).toString();
	}
	
	

	/**
	 * 减法，并四舍五入RoundingMode.HALF_UP,如果value=null则返回默认值,denominator=为空则返回value的四舍五入值
	 * @param value
	 * @param denominator
	 * @param scale
	 * @param defaultValue
	 * @return
	 */
    public static String subtractScaleToStr(BigDecimal value, BigDecimal denominator,int scale,String defaultValue){
    	if( value == null )return defaultValue;
    	if( denominator == null || compareTo1Eq2(denominator, BigDecimal.ZERO))return value.setScale(scale,RoundingMode.HALF_UP).toString();
        return value.subtract(denominator).setScale(scale,RoundingMode.HALF_UP).toString();
    }
    
    /**
     * 
	 * 减法，并四舍五入RoundingMode.HALF_UP,如果value=null则返回默认值,denominator=为空则返回value的四舍五入值
	 * 转换成BigDecimal时出现异常则返回defaultValue
     * @param value
     * @param denominator
     * @param scale
     * @param defaultValue：默认值
     * @return
     */
    public static String subtractScaleToStr(String value, String denominator,int scale,String defaultValue){
    	if( StringUtils.isBlank(value) )return defaultValue;
    	if( StringUtils.isBlank(denominator) )return value;
    	try {
			BigDecimal v = new BigDecimal(value);
			BigDecimal d = new BigDecimal(denominator);
			return v.subtract(d).setScale(scale,RoundingMode.HALF_UP).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return defaultValue;
    }

	public static BigDecimal toMillion(Long num,int scale){
		if(num==null )return BigDecimal.ZERO;
		return new BigDecimal(num).divide(new BigDecimal(10000), scale, BigDecimal.ROUND_HALF_UP);
	}
	public static BigDecimal toMillion(BigDecimal bigDecimal,int scale){
		if(bigDecimal==null )return BigDecimal.ZERO;
		return bigDecimal.divide(new BigDecimal(10000), scale, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal toBillion(BigDecimal bigDecimal,int scale){
		if(bigDecimal==null )return BigDecimal.ZERO;
		return bigDecimal.divide(new BigDecimal(100000000), scale, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal toBillion(Long num,int scale){
		if(num==null )return BigDecimal.ZERO;
		return new BigDecimal(num).divide(new BigDecimal(100000000), scale, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal setScale(BigDecimal num,int scale){
		if(num==null)return BigDecimal.ZERO;

		return num.setScale(scale,BigDecimal.ROUND_HALF_UP);
	}
}
