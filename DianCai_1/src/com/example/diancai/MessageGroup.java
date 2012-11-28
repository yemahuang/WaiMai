package com.example.diancai;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
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
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Tab页面手势滑动切换以及动画效果
 * 
 * @author HWM
 * 
 */
public class MessageGroup extends Activity {
	// ViewPager是google SDk中自带的一个附加包的一个类，可以用来实现屏幕间的切换。
	// android-support-v4.jar
	//文件的路径
	public final static String URL = "/data/data/com.example.diancai/files";
	//数据库文件
	public final static String DB_FILE_NAME = "tabkaway.db";
	// 归属地
	public final static String TABLE_NAME = "dish_info";
	static SQLiteDatabase db = null;
	private ViewPager mPager;//页卡内容
	private List<View> listViews; // Tab页面列表
	private ImageView cursor;// 动画图片
	private TextView t1, t2, t3;// 页卡头标
	private float offset =0;// 动画图片偏移量
	private int bmpW;// 动画图片宽度	
	private ListView myList1,myList2,myList3;
	View view1,view2,view3;
	Cursor cs;
	ImageView imageView1;
	private List<String> list1,list2,list3,list4;
//	private HashMap<String, Object> list5;
	private Integer[] mImageIds = {
			R.drawable.images_25,
			R.drawable.images_26,
			R.drawable.images_27,
			R.drawable.images_28,
			R.drawable.images_29,
			R.drawable.images_30,
			R.drawable.images_31,		   
			R.drawable.images_32,	
			R.drawable.images_33,		    
			R.drawable.images_34,		     
			R.drawable.images_35,		   			
			R.drawable.images_46,		    
			R.drawable.images_37,
			R.drawable.images_38,		   
			R.drawable.images_39,
			R.drawable.images_40,		 
			R.drawable.images_41,		  
			R.drawable.images_42,		    
			R.drawable.images_43,
			R.drawable.images_44,
			R.drawable.images_45,
			R.drawable.images_46,
			R.drawable.images_47,  
			R.drawable.images_48,  
			R.drawable.images_49,
			R.drawable.images_50,
			R.drawable.images_51,
			R.drawable.images_52,
			R.drawable.images_53,
			R.drawable.images_54
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		InitImageView();
		InitTextView();
		getlls();
		LayoutInflater mInflater = LayoutInflater.from(this);
		view1 =mInflater.inflate(R.layout.lay1, null);
		view2 =mInflater.inflate(R.layout.lay2, null);
		view3 =mInflater.inflate(R.layout.lay3, null);
		myList1=(ListView) view1.findViewById(R.id.listView1);
		myList2=(ListView) view2.findViewById(R.id.listView2);
		myList3=(ListView) view3.findViewById(R.id.listView3);		
		MyAdapter adapter = new MyAdapter(this);
		myList1.setAdapter(adapter);
		myList2.setAdapter(adapter);
		myList3.setAdapter(adapter);
		InitViewPager();
		myList1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(MessageGroup.this, "点击事件还未添加！", Toast.LENGTH_SHORT).show();
			}

		});
	}


	/**
	 * 初始化头标
	 */
	private void InitTextView() {
		t1 = (TextView) findViewById(R.id.text1);
		t2 = (TextView) findViewById(R.id.text2);
		t3 = (TextView) findViewById(R.id.text3);

		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));
	}

	/**
	 * 初始化ViewPager
	 */
	private void InitViewPager() { 
		mPager = (ViewPager) findViewById(R.id.vPager);
		listViews = new ArrayList<View>();
		listViews.add(view1);
		listViews.add(view2);
		listViews.add(view3);
		mPager.setAdapter(new MyPagerAdapter(listViews));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mPager.setOnTouchListener(new OnTouchListener() {


			public boolean onTouch(View v, MotionEvent event) {

				System.out.println("2------>"+event.getRawX());
				return false;				
			}
		});
	}

	/**
	 * 初始化动画
	 */
	private void InitImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.tab_cursor2)
				.getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) /2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置
	}
	
	private List<List<String>> getlls() {  
		list1 = new ArrayList<String>();  
		list2 = new ArrayList<String>(); 
		list3 = new ArrayList<String>(); 
		list4 = new ArrayList<String>(); 
	//	list5 = new HashMap<String, Object>();
		db=copyDB(this);
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
						

			}    
			while(cs.moveToNext());
		}      
		cs.close();   
		db.close();  
		return null; 
	}


	/**
	 * ListView适配器
	 * 
	 */
	public final class ViewHolder{
		public ImageView img;
		TextView tv1, tv2, tv3, tv4;

	}

	public class MyAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		//		private List<String> lls;


		public MyAdapter(Context context){
			this.mInflater = LayoutInflater.from(context);
			//			this.lls = l;
		}
		public int getCount() {
			// TODO Auto-generated method stub
			return list1.size();
		}

		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {
				holder=new ViewHolder();  
				convertView = mInflater.inflate(R.layout.vlist, null);
				holder.img = (ImageView)convertView.findViewById(R.id.img);
				//				holder.title = (TextView)convertView.findViewById(R.id.title);	
				holder.tv1 = (TextView) convertView     
						.findViewById(R.id.textView1);  
				holder.tv2 = (TextView) convertView     
						.findViewById(R.id.textView2);    
				holder.tv3 = (TextView) convertView      
						.findViewById(R.id.textView3);  
				holder.tv4 = (TextView) convertView     
						.findViewById(R.id.textView4);
				convertView.setTag(holder);
			}else {

				holder = (ViewHolder)convertView.getTag();
			}
			holder.tv1.setText(list1.get(position));   
			System.out.println("---->--"+list1.get(position)+position);
			holder.tv2.setText(list2.get(position));   
			holder.tv3.setText(list3.get(position)); 
			holder.tv4.setText(list4.get(position)); 
			holder.img.setImageResource(mImageIds[position]);
			return convertView;
		}

	}

	/**
	 * ViewPager适配器
	 */
	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}


		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		float one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		float two = one * 2;// 页卡1 -> 页卡3 偏移量
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:				
				animation = new TranslateAnimation(one, 0, 0, 0);			
				break;
			case 1:				
				animation = new TranslateAnimation(one, one, 0, 0);			
				break;
			case 2:				
				animation = new TranslateAnimation(offset, two, 0, 0);

				break;
			}		
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}


		public void onPageScrollStateChanged(int arg0) {
		}
	}

	/**
	 * 加载数据库
	 */
	// 将raw文件中的数据库文件拷贝至手机中的程序内存当中
	public static SQLiteDatabase copyDB(Context context) {
		try
		{	
			String databaseFilename = URL + "/" + DB_FILE_NAME;
			File dir = new File(DB_FILE_NAME);
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
}