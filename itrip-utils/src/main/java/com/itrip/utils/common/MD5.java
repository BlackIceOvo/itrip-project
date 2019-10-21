package com.itrip.utils.common;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class MD5 {

	public static String getMd5(String plainText,int length) {
		try {
			//根据MD5算法创建消息摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			//使用指定的字节更新摘要
			//默认是utf-8
			md.update(plainText.getBytes());
			//获得密文信息
			// digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			byte [] b = md.digest();
			//System.out.println("b="+md.digest());
			//System.out.println("b.length="+b.length);

			int i;
			//将密文信息转换成16进制的字符串形式
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				//System.out.println("i="+i);
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
				//System.out.println("buf="+buf);
			}
			// 32位
			// return buf.toString();
			// 16位
			// return buf.toString().substring(0, 16);
			
			return buf.toString().substring(0, length);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static int getRandomCode(){		
		int max=9999;
        int min=1111;
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;		
	}

	public static String getMD5(String str){
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			//加密
			BASE64Encoder base64en = new BASE64Encoder();
			//解密
			//BASE64Decoder base64De = new BASE64Decoder();
			String aa = base64en.encode(md5.digest(str.getBytes("utf-8")));
			return  aa;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}


	public static String getDecoder(String str){
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] b = null;
			//解密
			BASE64Decoder base64De = new BASE64Decoder();
			b=base64De.decodeBuffer(str);
			String aa = new String(b,"utf-8");
			return  aa;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	public static void main(String[] args) {
		System.out.println(MD5.getMd5("helloadsfdsffsf",6));
		//System.out.println(getRandomCode());
		System.out.println(MD5.getMD5("123"));
	}

}
