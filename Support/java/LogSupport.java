package Support;

import android.content.*;
import java.io.*;
import java.util.logging.*;

public class LogSupport
{
	private Context c;
	public String paths;
	public LogSupport(Context c){
		this.c=c;
		paths="sdcard/log/"+new SystemServiceSupport(c).getPackageName()+"/";
		prepareFileDir();
		}
	public void writelog(String log,String path){
		if(new File(paths+path+".logdat").exists()){
			String s=new IOSupport(c).Read(paths+path+".logdat")+"\n";
			new IOSupport(c).write(s+TimeSupport.get_ALLTIME()+"\n"+log,paths+path+".logdat");
		}
		else{
			new IOSupport(c).write(TimeSupport.get_ALLTIME()+"\n"+log,paths+path+".logdat");
		}
	}
	public String readlog(String path){
		return new IOSupport(c).Read(paths+path+".logdat");
	}
	public void prepareFileDir(){
	new IOSupport(c).touchdir("sdcard/log");
	new IOSupport(c).touchdir(paths);
	}
	public void outputlog(String name,String outputpath){
		File f=new File(paths+name+".logdat");
		if(f.isFile()){
		try{
			String log=new IOSupport(c).Read(paths + name + ".logdat");
			new IOSupport(c).writeString(outputpath,log,IOSupport.GS_UTF_8);
			writelog("logwrite:outputsuccess file name='"+name+"'","logoutputhistory");
		}
		catch (Exception e)
		{
		writelog("logerror:file:"+name+" can't be logfile", "logoutputerror");
		}}
		else{writelog("logerror:file:"+name+" not exists","logoutputerror");}
		}
	private Logger getLogger(Class<?> logcontext){
		Logger loger=Logger.getLogger(logcontext.getName());
		loger.setLevel(Level.ALL);
		return loger;
	}
}
