package com.jyss.ziwei.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

	// ///////////
	public static List<Long> stringToLongList(String str, String splitStr) {
		List<Long> list = new ArrayList<Long>();
		if (str == null || str.isEmpty()) {
			return list;
		}
		String[] arr = str.split(splitStr);
		for (String id : arr) {
			list.add(Long.valueOf(id));
		}
		return list;
	}

	public static List<String> stringToStringList(String str, String splitStr) {
		List<String> list = new ArrayList<String>();
		if (str == null || str.isEmpty()) {
			return list;
		}
		String[] arr = str.split(splitStr);
		for (String id : arr) {
			list.add(id);
		}
		return list;
	}

	public static String[] stringtoArray(String str, String splitStr) {
		String[] arr = null;
		if (str != null && !(str.isEmpty())) {
			arr = str.split(splitStr);
		}
		return arr;
	}




	public static boolean saveUpload(MultipartFile file, String filePath) {

		File saveDir = new File(filePath);
		if (!saveDir.getParentFile().exists()) {
			saveDir.getParentFile().mkdirs();
		}
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				// 文件路径转存
				file.transferTo(saveDir);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	////补位函数///
	public static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				// sb.append(str).append("0");//右补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}


	// 随机生成四位数字
	public static String getSaltFour() {
		StringBuilder str = new StringBuilder();// 定义变长字符串
		Random random = new Random();
		// 随机生成数字，并添加到字符串
		for (int i = 0; i < 4; i++) {
			str.append(random.nextInt(10));
		}
		// 将字符串转换为String
		return str.toString();

	}

	////生成ID的
	public static String getMyId(String id) {
		String reStr  ="";
		String bwStr = addZeroForNum(id,6);
		String rStr = getSaltFour();
		reStr = rStr+bwStr;
		return reStr;

	}

	/**
	 * 时间戳加两位随机数
	 * @return
	 */
	public static long getItemId() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//long millis = System.nanoTime();
		//加上两位随机数
		Random random = new Random();
		int end2 = random.nextInt(99);
		//如果不足两位前面补0
		String str = millis + String.format("%02d", end2);
		long id = new Long(str);
		return id;
	}




	/**
	 * 解析成玩伴路径
	 * @param scenicSpotId
	 * @param type 1 =音频，2=图片
     * @return
     */
	public static String getSourceUrl(String scenicSpotId,String type) {
		String firstDirectory =  scenicSpotId.substring(0,4);
		String secondDirectory =  scenicSpotId.substring(0,8);
		String sourceurl =firstDirectory+"/"+secondDirectory;
		///1 =音频
		if(type.equals("1")){
			sourceurl = sourceurl+"/audio/"+scenicSpotId+"_32Kbps.m4a";
		///2=图片
		}else if(type.equals("2")){
			sourceurl = sourceurl+"/img/"+scenicSpotId+".jpg";
		}
		return sourceurl;

	}

	public static String getScenciUrl(String scenicId) {
		String firstDirectory =  scenicId.substring(0,8);
		String scenciUrl ="3.0/"+firstDirectory+"-0";
		return scenciUrl;

	}


	public static void main(String[] args)  {

        String ss="[\"/0086/32/05/020001/00863205020001_148-148.jpg\",\"/0086/32/05/020001/00863205020001_124-124.jpg\"]";

		List<String>  collection = JSONObject.parseArray(ss, String.class);//把字符串转换成集合
		System.out.print(collection.size());

		//String [] a = stringtoArray(ss,",");
		//picStringtoArray(ss,",");

		String t ="wwww333";
		System.out.print(t.substring(0,4));


	}




}
