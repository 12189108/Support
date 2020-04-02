package Support;
import android.app.*;
import android.content.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;
import com.tool.box.task1.*;
public class SystemServiceSupport extends ClassSupport
{
	private Context c;
	private String androids="android";
	public SystemServiceSupport(Context con){
		c=con;
			}
	public String getThisappversionName() throws Exception{
		return c.getPackageManager().getPackageInfo(c.getPackageName(),0).versionName;
	}
	public String getPackageName(){
		return c.getPackageName();
	}
	public int getThisappversionCode(){
		try
		{
			return c.getPackageManager().getPackageInfo(c.getPackageName(), 1).versionCode;
		}
		catch (Throwable e)
		{}
		return 0;
	}
	public final static int getSystemSDK(){
		return android.os.Build.VERSION.SDK_INT;
	}
	public String getSystemversionName()throws Exception{
		return c.getPackageManager().getPackageInfo(androids,0).versionName;
	}
	public int getSystemversionCode() throws Exception{
		return c.getPackageManager().getPackageInfo(androids,0).versionCode;
	}
	public String getapp(){
		try
		{
			return c.getPackageManager().getPackageInfo(c.getPackageName(), 64).signatures[0].toCharsString();
		}
		catch (Throwable e)
		{
			return null;
		}
	}
	public final static void Exit(){
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(1);
	}
	/*public void isTestPhone(){
		TelephonyManager t=(TelephonyManager) c.getSystemService(c.TELEPHONY_SERVICE);
		String ie=t.getDeviceId().toString();
		String im=t.getSubscriberId().toString();
		if (im.equals("460003054739204") && ie.equals("864147028352932"))
		{
			new GetSupport(c).getShortToastFactorySupport().makeText(R.string.welcome).show();
		}
		else{
			SharedPreferences sp=c.getSharedPreferences("time",c.MODE_PRIVATE);
			SharedPreferences.Editor sd=sp.edit();
			if(sp.getAll().toString().equals("{}")){
				sd.putInt("this",new TimeSupport().get_Day()).commit();
				sd.putInt("rest",10).commit();
				new ShortToastFactorySupport(c).makeText(c.getString(R.string.restofday).replace("%",sp.getInt("rest",0)+"")).show();
			}
			else if(sp.getInt("this",0)==new TimeSupport().get_Day()){
			}
			else{
				if(sp.getInt("rest",0)-1<=0){Exit();}
				else{
					sd.putInt("this",new TimeSupport().get_Day()).commit();
					sd.putInt("rest",sp.getInt("rest",0)-1).commit();
					new ShortToastFactorySupport(c).makeText(c.getString(R.string.restofday).replace("%",sp.getInt("rest",0)+"")).show();
				}
			}
		}
	}*/
	public final static boolean isnull(TextView v){
		if(v.getText().toString().trim().equals("")){
			return true;
		}
		return false;
	}
	public final static boolean isnull(EditText v){
		if(v.getText().toString().trim().equals("")){
			return true;
		}
		return false;
	}
	public static boolean isnulls(EditText et){
		if(et.getText().toString().equals("")){
			return true;
		}
		return false;
	}
	public static boolean isnulls(TextView tv){
		if(tv.getText().toString().equals("")){
			return true;
		}
		return false;
	}
	public void CopytoSystem(String copy){
		ClipboardManager copySerVice=(ClipboardManager) c.getSystemService(c.CLIPBOARD_SERVICE);
		copySerVice.setText(copy);
	}
	public String getcopyString(){
		ClipboardManager copy=(ClipboardManager) c.getSystemService(c.CLIPBOARD_SERVICE);
		return copy.getText().toString();
	}
	public void toKill(){
		Intent i=new Intent();
		i.setClassName(c,null);
		c.startActivity(i);
		Exit();
	}
	public void getString(){
		if(MD5Support.getString(c,IOSupport.RawFile2byte(c,R.raw.about)).equals(MD5Support.getMD5(getapp()))){}
		else{
			while(true){
			toKill();Exit();
				try
				{
					wait(2);
				}
				catch (InterruptedException e)
				{}
				finally{
				System.exit(ServiceSupport.String2int(System.err.toString()));
				}
			}
			}
	}
	public double getWindowHight(){
		WindowManager w=(WindowManager) c.getSystemService(c.WINDOW_SERVICE);
		return w.getDefaultDisplay().getHeight();
	}
	public void hideKeyboard(View view){
		InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE); 
		if(imm!=null){
			if(imm.isActive())imm.hideSoftInputFromWindow(view.getWindowToken(),0);
		}
	}
	public void showKeyboard(View view){
		InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE); 
		if(imm!=null){
			imm.showSoftInput(view,0);
		}
	}
	public static double getSystemVersion(){
		switch(getSystemSDK()){
		case 1:return 1;
		case 2:return 1.1;
		case 3:return 1.5;
		case 4:return 1.6;
		case 5:return 2;
		case 6:return 2.01;
		case 7:return 2.1;
		case 8:return 2.2;
		case 9:return 2.32;
		case 10:return 2.34;
		case 11:return 3;
		case 12:return 3.1;
		case 13:return 3.2;
		case 14:return 4.02;
		case 15:return 4.04;
		case 16:return 4.1;
		case 17:return 4.2;
		case 18:return 4.3;
		case 19:return 4.4;
		case 20:return 4.49;
		case 21:return 5;
		case 22:return 5.1;
		case 23:return 6;
		default:return 0;
		}
	}
	}
