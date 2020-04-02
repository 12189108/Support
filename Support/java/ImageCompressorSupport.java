package Support;
import android.content.*;
import android.net.*;
import java.io.*;
import android.database.*;
import android.provider.*;
import android.graphics.*;
import android.graphics.BitmapFactory.*;

public class ImageCompressorSupport
{
	private int maxWidth=612;
	private int maxHight=816;
	private Bitmap.CompressFormat com=Bitmap.CompressFormat.JPEG;
	private int quality = 80;
    private String destinationDirectoryPath;
	public ImageCompressorSupport(Context  c){destinationDirectoryPath=c.getCacheDir().getPath()+File.pathSeparator+"Compressor";}
	public ImageCompressorSupport setMaxWidth(int p1){maxWidth=p1;return this;}
	public ImageCompressorSupport setMaxHight(int p1){maxHight=p1;return this;}
	public ImageCompressorSupport setCompressFormat(Bitmap.CompressFormat p1){com=p1;return this;}
	public ImageCompressorSupport setQuality(int p1){quality=p1;return this;}
	public ImageCompressorSupport setDestinationDirectoryPath(String p1){destinationDirectoryPath=p1;return this;}
	public File compressToFile(File image,String comp) throws Throwable{return ImageSupport.compressImage(image,maxWidth,maxHight,com,quality,destinationDirectoryPath+File.pathSeparator+comp);}
	public File compressToFile(File imageFile) throws Throwable{return compressToFile(imageFile,imageFile.getName());}
	public Bitmap compressToBitmap(File imageFile){return ImageSupport.decodeSampledBitmapFromFile(imageFile,maxWidth,maxHight);}
	public static class ImageSupport{
		public static File compressImage(File imageFile,int reqwidth,int reqhight,Bitmap.CompressFormat com,int quality, String destinationPath)throws Throwable{
			FileOutputStream output=new FileOutputStream(destinationPath);
			decodeSampledBitmapFromFile(imageFile,reqwidth,reqhight).compress(com,quality,output);
			output.flush();
			output.close();
			return new File(destinationPath);
		}
		private static Bitmap decodeSampledBitmapFromFile(File imageFile, int reqWidth, int reqHeight) {
			BitmapFactory.Options op=new BitmapFactory.Options();
			op.inJustDecodeBounds=true;
			BitmapFactory.decodeFile(imageFile.getAbsolutePath(),op);
			op.inSampleSize=calculateInSampleSize(op,reqWidth,reqHeight);
			op.inJustDecodeBounds=false;
			return BitmapFactory.decodeFile(imageFile.getAbsolutePath(),op);
		}
		private static int calculateInSampleSize(BitmapFactory.Options op,int reqwith,int reqhight){
			int hight=op.outHeight;
			int width=op.outWidth;
			int size=1;
			if(hight>reqhight||width>reqwith){
				int halfw=width / 2;
				int halfh = hight / 2;
				while((halfh/size)>=reqhight&&(halfw/size)>=reqwith){
					size*=2;
				}
			}
			return size;
		}
	}
	public static class FileSupport{
	private static int BUFF_SIZE=1024*4;
	public static File from(Context c,Uri uri)throws Throwable{
		InputStream input=c.getContentResolver().openInputStream(uri);
		String filename=getFileName(c,uri);
		String[] splitName=splitFileName(filename);
		File temp=File.createTempFile(splitName[0], splitName[1]);
		temp=reName(temp,filename);
		temp.deleteOnExit();
		FileOutputStream out=new FileOutputStream(temp);
		copy(input,out);
		return temp;
	}
	private static String[] splitFileName(String filename)
{
		String name=filename;
		String extension="";
		int i=filename.lastIndexOf(".");
		if(i!=-1){
		name=filename.substring(0,i);
		extension=filename.substring(i);
		}
		return new String[]{name,extension};
	}
	private static String getFileName(Context c, Uri uri)
{
		String result=null;
		if(uri.getScheme().equals("content")){
			Cursor cur=c.getContentResolver().query(uri, null, null, null, null);
			if(cur!=null&&cur.moveToFirst()){
				result=cur.getString(cur.getColumnIndex(OpenableColumns.DISPLAY_NAME));
			}
			cur.close();
		}
		if(result==null){
			result=uri.getPath();
			int cut=result.lastIndexOf(File.pathSeparator);
			if(cut!=-1){result=result.substring(cut+1);}
		}
		return result;
	}
	private static File reName(File file,String newname){
		File newFile=new File(file.getParent(), newname);
		if(!newFile.equals(file)){
			if(newFile.exists()&&newFile.delete()){}
			if(file.renameTo(newFile)){}
		}
		return newFile;
	}
	public static long copy(InputStream in,OutputStream output)throws Throwable{
		long coun=0;
		int n;
		byte[] buff=new byte[BUFF_SIZE];
		while(-1!=(n=in.read(buff))){
			output.write(buff,0,n);
			coun+=n;
		}
		in.close();
		output.close();
		return coun;
	}}
}
