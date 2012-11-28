package com.example.DBHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.diancai.R;
public class SQLiteDbHelper {
	//文件的路径
		public final static String URL = "/data/data/com.example.diancai/files";
		//数据库文件
		public final static String DB_FILE_NAME = "tabkaway.db";
		// 归属地
		public final static String TABLE_NAME = "dish_info";
		static SQLiteDatabase db = null;
		private static Cursor cs;
		private  List<String> list1,list2,list3,list4;
		public SQLiteDbHelper (Context context,List<String> list1,List<String>
		list2,List<String> list3,List<String> list4){
			this.list1=list1;
			this.list2=list2;
			this.list3=list3;
			this.list4=list4;
		}
	/**
	 * 加载数据库
	 */
	// 将raw文件中的数据库文件拷贝至手机中的程序内存当中
	public static SQLiteDatabase copyDB(Context context) {
		try
		{	
			String databaseFilename = URL + "/" + DB_FILE_NAME;
			File dir = new File(URL);
			if (!dir.exists())
				dir.mkdir();
			if (!(new File(databaseFilename)).exists()) {
				InputStream is = context.getResources().openRawResource(R.raw.tabkaway);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
			db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);

		}
		catch(Exception e){

		}
		return db;

	}
	/**
	 * insert db
	 * @return
	 */
	public void insertdb(){
		
	}
	
	public  List<List<String>> getlls1() {  
		List<List<String>> lls = new ArrayList<List<String>>();
		list1 = new ArrayList<String>();  
		list2 = new ArrayList<String>(); 
		list3 = new ArrayList<String>(); 
		list4 = new ArrayList<String>(); 
	//	list5 = new HashMap<String, Object>();
		db=SQLiteDbHelper.copyDB(null);
		String sql = "SELECT * FROM "+TABLE_NAME;
		cs = db.rawQuery(sql, null);  
		if(cs.moveToFirst()) {  
			do{     
				String je = cs.getString(cs.getColumnIndex("Name"));  
				String ms = cs.getString(cs.getColumnIndex("Info")); 
				String lx = cs.getString(cs.getColumnIndex("Price"));  
				String sj = cs.getString(cs.getColumnIndex("Time"));   
				list1.add(je);   
				list2.add(ms);
				list3.add(lx);  
				list4.add(sj);   
				lls.add(list1);	
				lls.add(list2);	
				lls.add(list3);	
				lls.add(list4);	

			}    
			while(cs.moveToNext());
		}      
		cs.close();   
		db.close();  
		return lls; 
	}
	
}
