package com.test.security;

import java.io.File;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.test.BaseServiceTest;
import com.utils.DateTimeUtils;
import com.utils.FormatUtils;
import com.utils.JdbcTsmarketUtils;

/**
 * neeq行情数据测试（新三板）
 * @author hwaggLee
 * @createDate 2016年11月2日
 */
public class TestNeeqTsmarket extends BaseServiceTest{

	public static void main(String[] args) {
		new TestNeeqTsmarket().testStart();
	}

	/**
	 * 测试启动器，并结束异常打印
	 * 
	 */
	@Test
	public void testStart() {
		JdbcTsmarketUtils utils = new JdbcTsmarketUtils();
		try {
			start(utils);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			utils.closeConnection();
		}
	}
	
	private void start(JdbcTsmarketUtils utils)throws Exception{
		System.out.println("start temp_allstock ...");
		List<Map<String, Object>> allStockCodeList = utils.findModeResult("select xxzqdm from temp_allstock", null);
		System.out.println("start temp_allstock success.");
		if(allStockCodeList == null || allStockCodeList.size() ==0 ) return;
		List<Map<String, Object>> stockList = null;
		for (Map<String, Object> map : allStockCodeList) {
			String xxzqdm = FormatUtils.formatToStringTrim(map.get("xxzqdm"));
			if( StringUtils.isBlank(xxzqdm))continue;
			System.out.println("start stock "+xxzqdm);
			LinkedHashMap<String,LinkedHashMap<String,Map<String, Object>>> mmMap = new LinkedHashMap<String, LinkedHashMap<String,Map<String,Object>>>();
			LinkedHashMap<String,LinkedHashMap<String,Map<String, Object>>> yyMap = new LinkedHashMap<String, LinkedHashMap<String,Map<String,Object>>>();
			//行情信息
			stockList = utils.findModeResult("select * from security_info s where s.xxzqdm  = '"+xxzqdm+"' order by s.mdate desc", null);
			
			for (Map<String, Object> m : stockList) {
				Date mdate = FormatUtils.formatToDate(m.get("mdate"));
				if( mdate == null )continue;
				String yyyymm = DateTimeUtils.date2StrDate(mdate, "yyyyMM");//月
				String yyyy = DateTimeUtils.date2StrDate(mdate, "yyyy");//年
				String dateStr = DateTimeUtils.date2StrDate(mdate, DateTimeUtils.FORMAT_yyyy_MM_dd);
				LinkedHashMap<String,Map<String, Object>> mmList = mmMap.get(yyyymm);
				if( mmList == null ){
					mmList = new LinkedHashMap<String,Map<String,Object>>();
					mmMap.put(yyyymm, mmList);
				}
				mmList.put(dateStr,m);
				LinkedHashMap<String,Map<String, Object>> yyList = yyMap.get(yyyy);
				if( yyList == null ){
					yyList = new LinkedHashMap<String,Map<String,Object>>();
					yyMap.put(yyyy, yyList);
				}
				yyList.put(dateStr,m);
			}
			
			//行情数据
			stockList = utils.findModeResult("select * from security_market s where s.hqzqdm  = '"+xxzqdm+"' order by s.mdate desc", null);
			for (Map<String, Object> m : stockList) {
				Date mdate = FormatUtils.formatToDate(m.get("mdate"));
				if( mdate == null )continue;
				String yyyymm = DateTimeUtils.date2StrDate(mdate, "yyyyMM");//月
				String yyyy = DateTimeUtils.date2StrDate(mdate, "yyyy");//年
				String dateStr = DateTimeUtils.date2StrDate(mdate, DateTimeUtils.FORMAT_yyyy_MM_dd);
				
				LinkedHashMap<String,Map<String, Object>> mmList = mmMap.get(yyyymm);
				if( mmList == null ){
					continue;
				}
				Map<String, Object> mm = mmList.get(dateStr);
				if( mm == null ){
					continue;
				}
				mm.putAll(m);
				
				LinkedHashMap<String,Map<String, Object>> yyList = yyMap.get(yyyy);
				if( yyList == null ){
					continue;
				}
				Map<String, Object> yy = yyList.get(dateStr);
				if( yy == null ){
					continue;
				}
				yy.putAll(m);
			}
			
			/*for (String	yyyymm : mmMap.keySet()) {
				LinkedHashMap<String,Map<String, Object>> list = mmMap.get(yyyymm);
				for (String dateStr : list.keySet()) {
					System.out.println(yyyymm+"="+dateStr);
				}
			}
			*/
			System.out.println("insert sql...");
			
			StringBuilder sbsql = new StringBuilder();
			//月
			if(mmMap != null&&mmMap.size() >=0 ) {
				StringBuilder sb = new StringBuilder();
				sb.append(" INSERT INTO `security_market_period` (`id`, `xxzqdm`, `xxzqjc`, `xxgprq`, `xxzgb`, `hqzxj`, `hqzrsp`, `hqsly`, `hqcjl`, `hqcje`, `hqsdate`, `hqedate`, `hqperiod`, `insertTime`) VALUES ");
				String insertTime = DateTimeUtils.date2StrDate(new Date(), DateTimeUtils.FORMAT_yyyy_MM_dd_HH_mm_ss);
				String xxgprq = null;
				for (String yyyymm : mmMap.keySet()) {
					LinkedHashMap<String,Map<String, Object>> list = mmMap.get(yyyymm);
					if( list == null || list.size() ==0 )continue;
					Map<String, Object> startMMMap = null;
					Map<String, Object> endMMMap = null;
					int i = 1 ;
					int size = list.size();
					if(size == 1 ){
						for (String dateStr : list.keySet()) {
							endMMMap = list.get(dateStr);
							startMMMap = list.get(dateStr);
						}
					}else{
						for (String dateStr : list.keySet()) {
							if( i == 1 ){
								endMMMap = list.get(dateStr);
							}else if( i == size){
								startMMMap = list.get(dateStr);
							}
							i++;
						}
					}
					if( startMMMap == null || endMMMap == null )continue;
					
					String id =  UUID.randomUUID().toString().replace("-", "");
					String xxzqjc =  FormatUtils.formatToStringTrim(endMMMap.get("xxzqjc"));
					if(StringUtils.isBlank(xxgprq)){
						xxgprq = FormatUtils.formatToStringTrim(endMMMap.get("xxgprq"));
					}
					String xxzgb =  FormatUtils.formatBigDecimalToInteger(endMMMap.get("xxzgb"),0);
					String hqzjcj =  FormatUtils.formatBigDecimalToInteger(endMMMap.get("hqzjcj"),3);
					String hqzrsp =  FormatUtils.formatBigDecimalToInteger(startMMMap.get("hqzrsp"),4);
					String hqsly1 =  FormatUtils.formatBigDecimalToInteger(endMMMap.get("hqsyl1"),3);
					String hqcjl =  FormatUtils.formatBigDecimalToInteger(endMMMap.get("hqcjsl"),0);
					String hqcje =  FormatUtils.formatBigDecimalToInteger(endMMMap.get("hqcjje"),3);
					String hqsdate = DateTimeUtils.date2StrDate(FormatUtils.formatToDate(startMMMap.get("mdate")), DateTimeUtils.FORMAT_yyyy_MM_dd);
					String hqedate = DateTimeUtils.date2StrDate(FormatUtils.formatToDate(endMMMap.get("mdate")), DateTimeUtils.FORMAT_yyyy_MM_dd);
					String hqperiod =  yyyymm.substring(4);
					
					sb.append("\n(");
					sb.append("'").append(id).append("'").append(",");
					sb.append("'").append(xxzqdm).append("'").append(",");
					sb.append("'").append(xxzqjc).append("'").append(",");
					sb.append("'").append(xxgprq).append("'").append(",");
					sb.append("'").append(xxzgb).append("'").append(",");
					sb.append("'").append(hqzjcj).append("'").append(",");
					sb.append("'").append(hqzrsp).append("'").append(",");
					sb.append("'").append(hqsly1).append("'").append(",");
					sb.append("'").append(hqcjl).append("'").append(",");
					sb.append("'").append(hqcje).append("'").append(",");
					sb.append("'").append(hqsdate).append("'").append(",");
					sb.append("'").append(hqedate).append("'").append(",");
					sb.append("'").append(hqperiod).append("'").append(",");
					sb.append("'").append(insertTime).append("'");
					sb.append("),");
				}
				sbsql.append(sb.toString().substring(0, sb.length()-1)+";\n");
			}
			//年
			if(yyMap != null&&yyMap.size() >=0 ) {
				StringBuilder sb = new StringBuilder();
				sb.append(" INSERT INTO `security_market_period` (`id`, `xxzqdm`, `xxzqjc`, `xxgprq`, `xxzgb`, `hqzxj`, `hqzrsp`, `hqsly`, `hqcjl`, `hqcje`, `hqsdate`, `hqedate`, `hqperiod`, `insertTime`) VALUES ");
				String insertTime = DateTimeUtils.date2StrDate(new Date(), DateTimeUtils.FORMAT_yyyy_MM_dd_HH_mm_ss);
				String xxgprq = null;
				for (String yyyymm : yyMap.keySet()) {
					LinkedHashMap<String,Map<String, Object>> list = yyMap.get(yyyymm);
					if( list == null || list.size() ==0 )continue;
					Map<String, Object> startMMMap = null;
					Map<String, Object> endMMMap = null;
					int i = 1 ;
					int size = list.size();
					if(size == 1 ){
						for (String dateStr : list.keySet()) {
							endMMMap = list.get(dateStr);
							startMMMap = list.get(dateStr);
						}
					}else{
						for (String dateStr : list.keySet()) {
							if( i == 1 ){
								endMMMap = list.get(dateStr);
							}else if( i == size){
								startMMMap = list.get(dateStr);
							}
							i++;
						}
					}
					
					String id =  UUID.randomUUID().toString().replace("-", "");
					String xxzqjc =  FormatUtils.formatToStringTrim(endMMMap.get("xxzqjc"));
					if(StringUtils.isBlank(xxgprq)){
						xxgprq = FormatUtils.formatToStringTrim(endMMMap.get("xxgprq"));
					}
					String xxzgb =  FormatUtils.formatBigDecimalToInteger(endMMMap.get("xxzgb"),0);
					String hqzjcj =  FormatUtils.formatBigDecimalToInteger(endMMMap.get("hqzjcj"),3);
					String hqzrsp =  FormatUtils.formatBigDecimalToInteger(startMMMap.get("hqzrsp"),4);
					String hqsly1 =  FormatUtils.formatBigDecimalToInteger(endMMMap.get("hqsyl1"),3);
					String hqcjl =  FormatUtils.formatBigDecimalToInteger(endMMMap.get("hqcjsl"),0);
					String hqcje =  FormatUtils.formatBigDecimalToInteger(endMMMap.get("hqcjje"),3);
					String hqsdate = DateTimeUtils.date2StrDate(FormatUtils.formatToDate(startMMMap.get("mdate")), DateTimeUtils.FORMAT_yyyy_MM_dd);
					String hqedate = DateTimeUtils.date2StrDate(FormatUtils.formatToDate(endMMMap.get("mdate")), DateTimeUtils.FORMAT_yyyy_MM_dd);
					String hqperiod =  "13";
					
					sb.append("\n(");
					sb.append("'").append(id).append("'").append(",");
					sb.append("'").append(xxzqdm).append("'").append(",");
					sb.append("'").append(xxzqjc).append("'").append(",");
					sb.append("'").append(xxgprq).append("'").append(",");
					sb.append("'").append(xxzgb).append("'").append(",");
					sb.append("'").append(hqzjcj).append("'").append(",");
					sb.append("'").append(hqzrsp).append("'").append(",");
					sb.append("'").append(hqsly1).append("'").append(",");
					sb.append("'").append(hqcjl).append("'").append(",");
					sb.append("'").append(hqcje).append("'").append(",");
					sb.append("'").append(hqsdate).append("'").append(",");
					sb.append("'").append(hqedate).append("'").append(",");
					sb.append("'").append(hqperiod).append("'").append(",");
					sb.append("'").append(insertTime).append("'");
					sb.append("),");
				}
				sbsql.append(sb.toString().substring(0, sb.length()-1)+";\n");
			}
			System.out.println("add db file...");
			//utils.insert(sbsql.toString());
			FileUtils.write(new File("C:\\Users\\huage\\Desktop\\security_market_period.sql"), sbsql.toString(),"utf-8",true);
		}

		System.out.println("end ...");
	}
}
