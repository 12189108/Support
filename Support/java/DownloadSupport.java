package Support;
import android.util.*;
import java.math.*;
import java.net.*;
import org.apache.http.*;
import org.json.*;

public class DownloadSupport
{
	private String downloadurl;
	public String mfilename="filename";
	public String mfilelength="filelength";
	public DownloadSupport(String downurl){
		this.downloadurl=downurl;
	}
	/*
	*注意判断网络状态！
	*注意不要放在主线程运行！！！
	*偶尔网络不稳定可重复调用2-3次
	*/
	public JSONObject getFileInfo(){
		JSONObject jb=new JSONObject();
		long filelength;
		try{
		URL url=new URL(downloadurl);
		HttpURLConnection uc=(HttpURLConnection) url.openConnection();
		uc.setRequestProperty("Accept-Encoding", "identity");
		uc.setRequestMethod("GET");
		//uc.setRequestProperty("Accept-Encoding", "identity");
		uc.connect();
		int code=uc.getResponseCode();
		if(code==200){
			String filename=uc.getHeaderField("Content-Disposition");
			filelength=uc.getContentLength();
			if (filename == null || filename.length() < 1)
			{
				URL u=uc.getURL();
				String f1=u.getFile();
				filename=f1.substring(f1.lastIndexOf("/")+1);
			}
			else{
				if(uc.getContentEncoding()==null)
				filename=URLDecoder.decode(filename.substring(filename.indexOf("filename=")+9),IOSupport.GS_UTF_8);
				else filename=URLDecoder.decode(filename.substring(filename.indexOf("filename=")+9),uc.getContentEncoding());
				filename=filename.replace("\"","");
			}
			jb.put("err",false);
			jb.put(mfilename,filename);
			jb.put(mfilelength,filelength);
		}
		else{
			jb.put("err",true);
			jb.put("err_msg","Code:"+code);
		}
		}catch(Throwable e){
			try{
			jb.put("err",true);
			jb.put("err_msg",e.toString());
			}catch(Throwable ee){Log.e(DownloadSupport.class.toString(),"errjb",ee);}
		Log.e(DownloadSupport.class.toString(),"err",e);
		}
		return jb;
	}
	public static double div(double d1,double d2,int scale){
		if(scale<0)throw new IllegalArgumentException("unknow scale");
		BigDecimal b1=new BigDecimal(d1);
		BigDecimal b2=new BigDecimal(d2);
		return b1.divide(b2,scale,b2.ROUND_HALF_UP).doubleValue();
	}
}
