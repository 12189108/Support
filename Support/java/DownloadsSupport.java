package Support;
import android.content.*;
import org.apache.http.*;
import android.os.*;
import java.io.*;
import java.net.*;
public class DownloadsSupport extends Thread
{
	private Context c;
	private String url,filename;
	private long length;
	private onDownloadListener onDownloadListener;
	private Throwable error;
	private double precent;
	private boolean still=true;
	private URL Url;

	private HttpURLConnection uc;
	public DownloadsSupport(Context c,String url,String filename){
	this.c=c;this.url=url;this.filename=filename;
	}
	public DownloadsSupport setonDownloadListener(onDownloadListener onDownloadListener)
	{this.onDownloadListener = onDownloadListener;return this;}
	public DownloadsSupport startDownload()
	{new classes().start();return this;}
	public static interface onDownloadListener{
		void onReciveFileLenght(Context context,long lenght);
		void onDownloadChange(Context context,double precent);
		void onError(Context context,Throwable throwable);
	}
	class classes extends Thread
	{

		@Override
		public void run()
		{
			// TODO: Implement this method
			super.run();
			try{
			InputStream in=(InputStream) uc.getContent();
			File f=new File(filename);
			if(f.exists())f.delete();f.createNewFile();
				BufferedInputStream bis=new BufferedInputStream(in);
				FileOutputStream fos=new FileOutputStream(f);
				BufferedOutputStream boss=new BufferedOutputStream(fos);
				int read;
				int count=0;
				precent=0.0;
				byte[] buff=new byte[1024 * 2];
				while((read=bis.read(buff))!=-1){
					boss.write(buff,0,read);
					count+=read;
					precent=((double)count/length)*100.0;
					//2020 3 20 20:08
					//防止下载完成时流未关闭，而新流已闯入，造成堵塞，导致app卡死，同时，防止handlemessage发送过快
					sleep(1);//很重要！！！！不可删除！！！！停顿必须大于0！！！！！！！！！！
					//停顿与发送message顺序不可改变！！！！！否则可能出现流的堵塞！！！
					new Handlers().sendEmptyMessage(404);
				}
				boss.flush();
				boss.close();
				fos.flush();
				fos.close();
				bis.close();
				in.close();
			}
			catch (Throwable e)
			{error = e;new Handlers().sendEmptyMessage(200);}
		}}
	@Override
	public void run()
	{
		// TODO: Implement this method
		super.run();
		try{
		Url=new URL(url);
		uc=(HttpURLConnection) Url.openConnection();
		uc.setRequestProperty("Accept-Encoding", "identity");
		uc.setRequestMethod("GET");
		uc.connect();
		length=uc.getContentLength();
		new Handlers().sendEmptyMessage(-200);
		}catch(Throwable e){error=e;new Handlers().sendEmptyMessage(200);}
	}
	class Handlers extends Handler{
		public Handlers()
		{super(c.getMainLooper());}

		@Override
		public void handleMessage(Message msg)
		{
			// TODO: Implement this method
			super.handleMessage(msg);
			if(msg.what==200&&onDownloadListener!=null){onDownloadListener.onError(c,error);}
			if(msg.what==-200&&onDownloadListener!=null){onDownloadListener.onReciveFileLenght(c,length);}
			if(msg.what==404&&onDownloadListener!=null&&still){onDownloadListener.onDownloadChange(c,precent);if(precent>=100){still=false;currentThread();}
			}
		
	}
}}
