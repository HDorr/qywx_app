package com.ziwow.scrmapp.tools.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class NumberUtil {
	
	/**金额为分的格式 */    
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";
    
	/**
	 * 获取两个double数字相减，防止丢失精度(保留两位小数)
	 * @param doubleNum1
	 * @param doubleNum2
	 * @return
	 */
	public static double getDoubleSubtract(double doubleNum1, double doubleNum2){
		BigDecimal bigDecNum1 = new BigDecimal(Double.valueOf(doubleNum1));
		BigDecimal bigDecNum2 = new BigDecimal(Double.valueOf(doubleNum2));
		BigDecimal bigDecSubtract = bigDecNum1.subtract(bigDecNum2);
		double subtract = bigDecSubtract.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return subtract;
	}
	
	/**
	 * 获取两个double数字相加，防止丢失精度(保留两位小数)
	 * @param doubleNum1
	 * @param doubleNum2
	 * @return
	 */
	public static double getDoubleAdd(double doubleNum1, double doubleNum2){
		BigDecimal bigDecNum1 = new BigDecimal(Double.valueOf(doubleNum1));
		BigDecimal bigDecNum2 = new BigDecimal(Double.valueOf(doubleNum2));
		BigDecimal addResult = bigDecNum1.add(bigDecNum2);
		double result = addResult.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}
	
	/**
	 * 获取两个double数字相乘，防止丢失精度(保留两位小数)
	 * @param doubleNum1
	 * @param doubleNum2
	 * @return
	 */
	public static double getDoubleMul(double doubleNum1, double doubleNum2){
		BigDecimal bigDecNum1 = new BigDecimal(Double.valueOf(doubleNum1));
		BigDecimal bigDecNum2 = new BigDecimal(Double.valueOf(doubleNum2));
		BigDecimal addResult = bigDecNum1.multiply(bigDecNum2);
		double result = addResult.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}
	
	/**
	 * 
	 * @Title: add 两个float数据相加保留两位小数
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param value1
	 * @param @param vaule2
	 * @param @return 设定文件 
	 * @return float 返回类型 
	 * @throws
	 */
	public static float add(float value1, float vaule2){
		BigDecimal b1 = new BigDecimal(Float.valueOf(value1));
		BigDecimal b2 = new BigDecimal(Float.valueOf(vaule2));
		BigDecimal addResult = b1.add(b2);
		float result = addResult.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		return result;
	}
	
	/**
	 * 
	 * @Title: sub 两个float数据相减保留两位小数
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param value1
	 * @param @param value2
	 * @param @return 设定文件 
	 * @return double 返回类型 
	 * @throws
	 */
	public static int sub(float value1, float value2){
		BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
		BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
		BigDecimal subtractResult = b1.subtract(b2);
		int result = subtractResult.setScale(2, BigDecimal.ROUND_HALF_UP).intValue();
		return result;
	}
	
	/**
	 * 
	 * @Title: mul 两个数相乘保留两位小数
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param value1
	 * @param @param value2
	 * @param @return 设定文件 
	 * @return double 返回类型 
	 * @throws
	 */
	public static float mul(int value1, float value2){
		BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
		BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
		BigDecimal multiResult = b1.multiply(b2);
		float result = multiResult.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		return result;
	}
	
	/**
	 * 
	 * @Title: mul 两个数相乘保留两位小数
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param value1
	 * @param @param value2
	 * @param @return 设定文件 
	 * @return double 返回类型 
	 * @throws
	 */
	public static float mul(int value1, double value2){
		BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
		BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
		BigDecimal multiResult = b1.multiply(b2);
		float result = multiResult.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		return result;
	}
	
	/**
	 * 两个Double数相除
	 * 
	 * @param v1
	 * @param v2
	 * @return int
	 */
	public static int div(Double v1, Double v2) {
		if (v1 == 0 || v2 == 0)
			return 0;
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.divide(b2, 1 ,BigDecimal.ROUND_FLOOR).intValue();
	}
    
	/**
	 * 获取两个double数字相除，防止丢失精度(保留两位小数)
	 * @param doubleNum1
	 * @param doubleNum2
	 * @return
	 */
	public static double getDoubleDiv(double doubleNum1, double doubleNum2){
		BigDecimal bigDecNum1 = new BigDecimal(Double.valueOf(doubleNum1));
		BigDecimal bigDecNum2 = new BigDecimal(Double.valueOf(doubleNum2));
		double result = bigDecNum1.divide(bigDecNum2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}
	
	/**
	 * 
	 * @Title: getDivFloor 两个整数相除取ceil后整数
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param a
	 * @param @param b
	 * @param @return 设定文件 
	 * @return int 返回类型 
	 * @throws
	 */
	public static int getDivCeil(int a, int b) {
		// 如果被除数小于0，则返回0
		if (b <= 0) {
			return 0;
		}
		return (a + b - 1) / b;
	}
	
	/**
	 * 订单运费计算
	 * (1) 首件运费不同:首件运费最高的商品运费 + 其他商品的续件运费 × 商品总数量
	 * (2) 首件运费相同:续件运费最低的商品运费 + 其他商品的续件运费 × 商品总数量
	 * 
	 * @param freightList
	 * @return
	 */
	public static float getFreightValue(List<String> freightList) {
		if(null == freightList || freightList.isEmpty())
			return 0;
		float freight = 0;
		TreeMap<Float, List<Float[]>> values = new TreeMap<Float, List<Float[]>>();
		for (int i = 0; i < freightList.size(); i++) {
			String freightStr = freightList.get(i);
			String[] strArry = freightStr.split("_");
			if (strArry != null && strArry.length == 5) {
				List<Float[]> paramList  = new ArrayList<Float[]>();
				Float[] paramValues = new Float[4];
				paramValues[0] = Float.valueOf(strArry[0]);
				paramValues[1] = Float.valueOf(strArry[2]);
				paramValues[2] = Float.valueOf(strArry[3]);
				paramValues[3] = Float.valueOf(strArry[4]);
				paramList.add(paramValues);
				Float initCondition = Float.valueOf(strArry[1]);
				if(values.containsKey(initCondition)) {
					List<Float[]> duplicateList = values.get(initCondition);
					duplicateList.add(paramList.get(0));
				} else {
					values.put(initCondition, paramList);
				}
			}
		}
		Map.Entry<Float, List<Float[]>> maxInitEntry = values.lastEntry();
		// 首件最高运费
		Float maxInitFreight = maxInitEntry.getKey();
		
		// 获取续件续费最低值
		List<Float[]> maxInitValue =  maxInitEntry.getValue();
		float minIncFreigth = maxInitValue.get(0)[2];
		int m = 0;
		for (int i = 1; i < maxInitValue.size(); i++) {
			Float[] tmpVal = maxInitValue.get(i);
			Float incFreight = tmpVal[2];
			if(incFreight < minIncFreigth){
				minIncFreigth = incFreight;
				m = i;
			}
		}
		System.out.println("首件最高运费为:" + maxInitFreight + "," + maxInitValue);
		System.out.println("续件续费最低索引m=" + m + ", 最小续价=" + minIncFreigth);
		
		Float[] minIncItem = maxInitValue.get(m);
		int a = minIncItem[0].intValue();    // 首件个数
		int b = minIncItem[1].intValue();    // 续件个数
		Float c = minIncItem[2];  			 // 续费金额
		int d = minIncItem[3].intValue();    // 购买数量
		// 首件运费最高续件运费部分
		float incFreightPart = 0;
		// 其他所有商品续件运费部分
		float otherTotalPart = 0;
		
		// 判断首件运费最高是否有续件
		int incFreigthVal = d - a;
		if(incFreigthVal > 0) {
			int initSub = getDivCeil(incFreigthVal, b);
			incFreightPart = mul(initSub, c);
		}
		
		// 处理首件最高运费相同的列表的续件运费部分
		for (int n = 0; n < maxInitValue.size(); n++) {
			if(n == m) {
				continue;
			}
			Float[] minItemOut = maxInitValue.get(n);
			int outB = minItemOut[1].intValue();    // 续件个数
			Float outC = minItemOut[2];  			// 续费金额
			int outD = minItemOut[3].intValue();    // 购买数量
			int tempVal = getDivCeil(outD, outB);
			float incFreightOutPart = mul(tempVal, outC);
			otherTotalPart = add(otherTotalPart, incFreightOutPart);
		}
		
		// 计算其他所有商品续件运费部分
		values.remove(maxInitFreight);  // 移除首件最高运费的entry,计算其他所有商品续件运费部分
		Set<Map.Entry<Float, List<Float[]>>> set = values.entrySet();
		for (Iterator<Map.Entry<Float, List<Float[]>>> it = set.iterator(); it.hasNext();) {
			Map.Entry<Float, List<Float[]>> entry =  it.next();
			List<Float[]> list = entry.getValue();
			for (Float[] f : list) {
				int incNum = f[1].intValue();	// 续件个数
				Float incAmount = f[2];			// 续费金额
				int buyCount = f[3].intValue();	// 购买数量
				int subNum = getDivCeil(buyCount, incNum);
				float incPart = mul(subNum, incAmount);
				otherTotalPart = add(otherTotalPart, incPart);
			}
		}
		
		// 最终运费=首件最高运费 + (首件运费最高续件运费部分+其他所有商品续件运费部分)
		float initOutPart = add(incFreightPart, otherTotalPart);
		freight = add(maxInitFreight, initOutPart);
		return freight;
	}
    
    /**  
     * 将分为单位的转换为元 （除100）  
     *   
     * @param amount  
     * @return  
     * @throws Exception   
     */    
    public static String changeF2Y(String amount) throws Exception{    
        if(!amount.matches(CURRENCY_FEN_REGEX)) {    
            throw new Exception("金额格式有误");    
        }    
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();    
    }
        
	/**
	 * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额
	 * 
	 * @param amount
	 * @return
	 */
	public static String changeY2F(String amount) {
		String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥或者$的金额
		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0l;
		if (index == -1) {
			amLong = Long.valueOf(currency + "00");
		} else if (length - index >= 3) {
			amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
		} else if (length - index == 2) {
			amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
		} else {
			amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
		}
		return amLong.toString();
	}
}