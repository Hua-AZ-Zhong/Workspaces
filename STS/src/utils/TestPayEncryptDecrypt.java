package utils;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class TestPayEncryptDecrypt {
	
	//算法名称
    public static final String KEY_ALGORITHM = "DESede";
    // 算法名称/加密模式/填充方式 
    public static final String ALGORITHM_ECB = "DESede/ECB/NoPadding";
    public static final String ALGORITHM_CBC = "DESede/CBC/NoPadding";
    /** 初始向量 **/
	private static String iv = "0000000000000000";
	
    /** 
     * ECB解密 
     * @param key 密钥 
     * @param data Base64编码的密文 
     * @return 明文 
     * @throws Exception 
     */
    public static byte[] des3DecodeECB(String key, byte[] data) throws Exception {
    	String str=new String(data,"UTF-8");
    	data=hexStringToByte(str);
        Key deskey = keyGenerator(key);
        Cipher cipher = Cipher.getInstance(ALGORITHM_ECB);
        //IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(data);
        bOut=bytesToHexString(bOut).getBytes("UTF-8");
        //byte[] bOut = cipher.doFinal(Base64.decodeBase64(data));
        return bOut;
    }
    
    /** 
     * ECB加密 
     * @param key 密钥 
     * @param data 明文 
     * @return Base64编码的密文 
     * @throws Exception 
     */
    public static byte[] des3EncodeECB(String key, byte[] data) throws Exception {
    	String str=new String(data,"UTF-8");
    	data=hexStringToByte(str);
        Key deskey = keyGenerator(key);
        Cipher cipher = Cipher.getInstance(ALGORITHM_ECB);
        //IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(data);
        bOut=bytesToHexString(bOut).getBytes("UTF-8");
        return bOut;
        //return Base64.encodeBase64(bOut);
    }
    
    /** 
     * CBC解密 
     * @param key 密钥 
     * @param data  
     * @return 明文 
     * @throws Exception 
     */
    public static byte[] des3DecodeCBC(String key, byte[] data,String param) throws Exception {
    	try {
    		String str=new String(data,"UTF-8");
    		data=hexStringToByte(str);
			Key deskey = null;
			String value=null;
			DESedeKeySpec spec = new DESedeKeySpec(hexStringToByte(key));
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance(ALGORITHM_CBC);
			IvParameterSpec ips = new IvParameterSpec(hexStringToByte(iv));
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
			byte[] decryptData = cipher.doFinal(data);
			value=bytesToHexString(decryptData);
			System.out.println("Clear with padding is " + value);
			if("span".equals(param)||"mpan".equals(param)||"pan".equals(param)){
				int index=value.lastIndexOf("F")>value.lastIndexOf("8")?value.lastIndexOf("F"):value.lastIndexOf("8");
            	return value.substring(0,index).getBytes("UTF-8");
			}else if("expiryDate".equals(param)){
				return value.substring(0,4).getBytes("UTF-8");
			}else if("cvn2".equals(param)){
				return value.substring(0,3).getBytes("UTF-8");
            }else if("otpValue".equals(param)){
            	byte[] ss={decryptData[0],decryptData[1],decryptData[2],decryptData[3]};
            	return ss;
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
    }
    
    /** 
     * CBC加密 
     * @param key 密钥 
     * @param data 明文 
     * @return 密文 
     * @throws Exception 
     */
    public static byte[] des3EncodeCBC(String key, byte[] data,String param) throws Exception {
    	try {
			Key deskey = null;
			String va=new String(data,"UTF-8");
			byte[] b=null;
			if("span".equals(param)||"mpan".equals(param)||"pan".equals(param)){
				if(va.length()%2==1){
					va=va+"F80";
				}else{
					va=va+"80";
				}
				while(va.length()%16!=0){
					va=va+"00";
				}
				b=hexStringToByte(va);
			}else if("expiryDate".equals(param)){
				va=va+"800000000000";
				b=hexStringToByte(va);
			}else if("cvn2".equals(param)){
				va=va+"F800000000000";
				b=hexStringToByte(va);
			}else if("otpValue".equals(param)){
				byte[] ss=va.getBytes("ASCII");
				byte[] s={ss[0],ss[1],ss[2],ss[3],80,00,00,00};
				b=s;
			}else{
				b=data;
			}
			DESedeKeySpec spec = new DESedeKeySpec(hexStringToByte(key));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
			deskey = keyFactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance(ALGORITHM_CBC);
			IvParameterSpec ips = new IvParameterSpec(hexStringToByte(iv));
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] encryptData = cipher.doFinal(b);
			encryptData=bytesToHexString(encryptData).getBytes("UTF-8");
			return encryptData;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
    }
    
    /** 
     * 字节数组到十六进制字符串的转换 
     * @param byteArray 字节数组 
     * @return 十六进制字符串 
     */
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
    
    /** 
     * 十六进制字符串到字节数组的转换 
     * @param hexString 十六进制字符串 
     * @return 字节数组
     */
    public static byte[] hexStringToByte(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}
    
    /** 
     *   
     * 生成密钥key对象 
     * @param KeyStr 密钥字符串 
     * @return 密钥对象 
     * @throws InvalidKeyException   
     * @throws NoSuchAlgorithmException   
     * @throws InvalidKeySpecException   
     * @throws Exception 
     */
    private static Key keyGenerator(String keyStr) throws Exception {
        byte input[] = HexString2Bytes(keyStr);
        DESedeKeySpec KeySpec = new DESedeKeySpec(input);
        SecretKeyFactory KeyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        return ((Key) (KeyFactory.generateSecret(((java.security.spec.KeySpec) (KeySpec)))));
    }
    
    private static int parse(char c) {
        if (c >= 'a') return (c - 'a' + 10) & 0x0f;
        if (c >= 'A') return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }
 
    // 从十六进制字符串到字节数组转换 
    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }
    
    /** 
     * char到byte的转换 
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static void main(String[] args) throws Exception {
		// pan encrypt
		System.out.println("pan under enc key is " + new String(des3EncodeCBC("FD839464E9DA38FEF4541FD5E3D3B062FD839464E9DA38FEF4541FD5E3D3B062","6250130000000012".getBytes("UTF-8"),"pan"), "UTF-8"));
		
		// pan decrypt
		try {
			System.out.println("pan in clear is " + new String(des3DecodeCBC("FD839464E9DA38FEF4541FD5E3D3B062FD839464E9DA38FEF4541FD5E3D3B062","57203FD854F821DFCEE760AD1893AB80".getBytes("UTF-8"),"pan"), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
