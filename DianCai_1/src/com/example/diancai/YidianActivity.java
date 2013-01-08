package com.example.diancai;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DBHelper.SQLiteDbHelper;
import com.example.adapter.YidianListViewAdapter;

/**
 * Tab页面手势滑动切换以及动画效果
 * 
 * @author HWM
 * 
 */
public class YidianActivity extends Activity {
	// ViewPager是google SDk中自带的一个附加包的一个类，可以用来实现屏幕间的切换。
	// android-support-v4.jar
	//文件的路径
	public final static String URL = "/data/data/com.example.diancai/files";
	//数据库文件
	public final static String DB_FILE_NAME = "tabkaway.db";
	// 归属地
	public final static String TABLE_NAME = "insert_dish";
	static SQLiteDatabase db = null;
	private ListView myList1;
	Cursor cs;
	ImageView imageView1;
	private List<String> list1,list2,list3,list5;
	private List<Integer> list4;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yidiancai);
		myList1= (ListView)findViewById(R.id.yidianlist);
		getlls1();
		YidianListViewAdapter adapter = new YidianListViewAdapter(this,getlls1(),list1,list2,list3,list4,list5);
		myList1.setAdapter(adapter);
		myList1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(YidianActivity.this, "点击事件还未添加！", Toast.LENGTH_SHORT).show();
			}

		});
	}

	private List<List<String>> getlls1() {  
		List<List<String>> lls = new ArrayList<List<String>>();
		list1 = new ArrayList<String>();  
		list2 = new ArrayList<String>(); 
		list3 = new ArrayList<String>(); 
		list4 = new ArrayList<Integer>(); 
		list5 = new ArrayList<String>(); 
		db=SQLiteDbHelper.copyDB((this));
		String sql = "SELECT * FROM "+TABLE_NAME;
		cs = db.rawQuery(sql, null);  
		if(cs.moveToFirst()) {  
			do{     
				String je = cs.getString(cs.getColumnIndex("Name"));  
				String ms = cs.getString(cs.getColumnIndex("Info")); 
				String lx = cs.getString(cs.getColumnIndex("Price"));  
				String sj = cs.getString(cs.getColumnIndex("Time"));
				String im = cs.getString(cs.getColumnIndex("Images"));
				int i = Integer.valueOf(im);
				list1.add(je);   
				list2.add(ms);
				list3.add(lx);
				list5.add(sj);
				list4.add(i);   
				lls.add(list1);	
				lls.add(list2);	
				lls.add(list3);	
				lls.add(list5);	

			}    
			while(cs.moveToNext());
		}      
		cs.close();   
		db.close();  
		return lls; 
	}	

}