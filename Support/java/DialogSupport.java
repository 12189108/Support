package Support;
import android.app.*;
import android.content.*;
import android.view.*;
import android.view.View.*;
import com.tool.box.task1.*;
public class DialogSupport
{
	private Context con;
	public DialogSupport(Context c){
		con=c;
			}
	public void SimpleExitDialog(boolean candismiss){
			new DialogFactorySupport(con).SimpleDialog(R.string.DTitle, R.string.isexit,con. getString(R.string.yes),con. getString(R.string.no), new OnClickListener(){

					@Override
					public void onClick(View p1)
					{
						new IOSupport(con).clearCache();
						SystemServiceSupport.Exit();
					// TODO: Implement this method
					}
				}, null, false);
		}
	public AlertDialog SimpleViewDialog(View v,boolean canable){
		AlertDialog.Builder a=new AlertDialog.Builder(con);
		a.setView(v);
		a.setCancelable(canable);
		AlertDialog b=a.show();
		return b;
	}
	public void upDataDialog(){
	
	}
}
