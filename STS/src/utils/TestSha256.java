package utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestSha256 {
	private static byte[] sha256(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest md = null;
		md = MessageDigest.getInstance("SHA-256");
		md.reset();
		md.update(data);
		return md.digest();
	}
	
    public static String bytesToHexString(byte[] byteArray) {
		StringBuilder stringBuilder = new StringBuilder();
		if (byteArray == null || byteArray.length <= 0) {
			return null;
		}
		for (int i = 0; i < byteArray.length; i++) {
			int v = byteArray[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv.toUpperCase());
		}
		return stringBuilder.toString();
	}
    
	public static void main(String[] args) throws Exception {
		// 示例1
		String input="12345";
		try {
			byte[] bytes = sha256(input.getBytes("UTF-8"));
			String shaHex = bytesToHexString(bytes);
			System.out.println("sha256并转16进制输出1：" + shaHex);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("No argorithm error");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println("Encoding error");
		}
		
		// 示例2
		System.out.println("sha256并转16进制输出2：" + bytesToHexString(sha256("12345".getBytes("UTF-8"))));
	}
}
