package Support;
import java.security.*;
import java.util.*;
import android.content.*;
import javax.crypto.spec.*;
import javax.crypto.*;
public class MD5Support
{
public final static String getMD5(String input){
		MessageDigest md5=null;
		try
		{
			md5 = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e)
		{e.printStackTrace();return null;}
		char[] charry=input.toCharArray();
		byte[] b=new byte[charry.length];
		for(int i=0;i<charry.length;i++){
			b[i]=(byte) charry[i];
		}
		byte[] md5byte=md5.digest(b);
		StringBuffer hex=new StringBuffer();
		for(int i=0;i<md5byte.length;i++){
			int val=((int)md5byte[i])&0xff;
			if(val<16){
			hex.append("0");
			}
			hex.append(Integer.toHexString(val));
		}
		return hex.toString().trim().toString();
	}
	public final static String getStringRandom() {
		int length=6;
		String val = "";
		Random random = new Random();

		//参数length，表示生成几位随机数
		for(int i = 0; i < length; i++) {

			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			//输出字母还是数字
			if( "char".equalsIgnoreCase(charOrNum) ) {
				//输出是大写字母还是小写字母
				i=i-1;
			} else if( "num".equalsIgnoreCase(charOrNum) ) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
	public final static String getStringRandoms() {
		int length=16;
		String val = "";
		Random random = new Random();

		//参数length，表示生成几位随机数
		for(int i = 0; i < length; i++) {

			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			//输出字母还是数字
			if( "char".equalsIgnoreCase(charOrNum) ) {
				//输出是大写字母还是小写字母
				val += (char) (97 + random.nextInt(26));   
			} else if( "num".equalsIgnoreCase(charOrNum) ) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
	public static byte[] toString(Context c,String s){
	SecureRandom ss=new SecureRandom();
		try
		{
			DESKeySpec d = new DESKeySpec(getMD5(new SystemServiceSupport(c).getapp()).getBytes(IOSupport.GS_UTF_8));
			SecretKeyFactory sss=SecretKeyFactory.getInstance("des");
			SecretKey ssss=sss.generateSecret(d);
			Cipher cc=Cipher.getInstance("des");
			cc.init(cc.ENCRYPT_MODE,ssss,ss);
			return cc.doFinal(s.getBytes(IOSupport.GS_UTF_8));
		}
		catch (Exception e)
		{}
		catch (Throwable e)
		{}
		return null;
	}
	public static String getString(Context c,byte[] b){
		SecureRandom ss=new SecureRandom();
		try
		{
			DESKeySpec d = new DESKeySpec(getMD5(new SystemServiceSupport(c).getapp()).getBytes(IOSupport.GS_UTF_8));
			SecretKeyFactory sss=SecretKeyFactory.getInstance("des");
			SecretKey ssss=sss.generateSecret(d);
			Cipher cc=Cipher.getInstance("des");
			cc.init(cc.DECRYPT_MODE,ssss,ss);
			return new String(cc.doFinal(b));
			}catch(Exception e){}
		return null;
	}
	public static boolean canuse(byte[] b,Context c){
		SecureRandom ss=new SecureRandom();
		try
		{
			DESKeySpec d = new DESKeySpec(getMD5(new SystemServiceSupport(c).getapp()).getBytes(IOSupport.GS_UTF_8));
			SecretKeyFactory sss=SecretKeyFactory.getInstance("des");
			SecretKey ssss=sss.generateSecret(d);
			Cipher cc=Cipher.getInstance("des");
			cc.init(cc.DECRYPT_MODE,ssss,ss);
			cc.doFinal(b);
			return true;
		}catch(Exception e){
			System.err.print(e.toString());
		}
		catch(Throwable e){
			System.err.println(e.toString());
		}
		return false;
	}
	}
