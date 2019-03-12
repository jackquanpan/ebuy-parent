package com.java.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**  
 * @Title: http://www.smschinese.cn/api.shtml
 * @date 2011-3-22
 * @version V1.2  
 */
public class SMSTool {
	
	//用户名
	private static String Uid = "jack1";
	
	//接口安全秘钥
	private static String Key = "d41d8cd98f00b204e980";
	
	//手机号码，多个号码如13800000000,13800000001,13800000002
	//private static String smsMob = "13800000000";
	
	//短信内容
	//private static String smsText = "验证码：8888";

	public static Map<String,Object> sendSMS (String smsMob) {
		
		HttpClientUtil client = HttpClientUtil.getInstance();

		int i = new Random().nextInt(1000000);
		String smsText="您的注册易买网验证码是：" +i+"";
		Map<String,Object>resultMap=new HashMap<>();
		int flag = client.sendMsgUtf8(Uid, Key, smsText, smsMob);
		if(flag>=1){
			resultMap.put("error","1");
		}else{
			resultMap.put("error","0");
			resultMap.put("smsCode",i);
		}
		return resultMap;
	}
}
