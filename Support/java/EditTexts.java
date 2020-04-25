package Support;

import Support.*;
import android.content.*;
import android.widget.*;

public class EditTexts extends EditText
{
	public EditTexts(android.content.Context context) {super(context);v2(this,context);}
	public EditTexts(android.content.Context context, android.util.AttributeSet attrs) {super(context,attrs);v2(this,context);}

	public EditTexts(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {super(context,attrs,defStyleAttr);/*v2(this,context);*/}

	public EditTexts(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr, int defStyleRes)
	{super(context, attrs, defStyleAttr, defStyleRes);v2(this,context);}


	private void v2(EditText b,Context c){
	b.setHint(MD5Support.getString(c,ByteTransformSupport.Base64Decode(b.getHint().toString())));
	}}
