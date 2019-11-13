package com.seven.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 随机数工具类
 **/
public class RandomUtil {
	/**
	 * 得到指定位数的随机数
	 * */
	public static String getPosCode(int pos) {
		long num = (long) Math.pow(10, (pos - 1));
		Long i = (long) (((Math.random() * 9) + 1) * num);
		return i.toString();
	}
	
	/***
	 * 得到UUID
	 * */
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}

	/**
	 * 生成UUID字符串
	 * @return string UUID
	 * */
	public static String getUUIDS(){
		String s = UUID.randomUUID().toString();
		//去掉"-"符号
		return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
	}
	
	/**
     * 随机字符串
     */
    public static String randomStr(int length) {
        String allChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }

	/**
	 * 生成时间戳字符串
	 */
	public static String getTimeStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sdf.format(new Date());
		return date;
	}

	/**
	 * 将当前时间转换为yyyyMMddHHssmm,生成订单编号
	 */
	public static String useridToNo(Long userId){
		StringBuffer now = new StringBuffer(getTimeStr());
		now.append(userId).append(getPosCode(4));
		return now.toString();
	}




}
