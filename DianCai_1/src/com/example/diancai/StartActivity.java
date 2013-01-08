package com.example.diancai;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.example.DBHelper.SQLiteDbHelper;
import com.example.adapter.HideListViewAdapter;
import com.example.adapter.MyListViewAdapter;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Tab页面手势滑动切换以及动画效果
 * 
 * @author HWM
 * 
 */
public class StartActivity extends Activity implements  AdapterView.OnItemClickListener, View.OnClickListener{
	// ViewPager是google SDk中自带的一个附加包的一个类，可以用来实现屏幕间的切换。
	// android-support-v4.jar
	//文件的路径
	public final static String URL = "/data/data/com.example.diancai/files";
	//数据库文件
	public final static String DB_FILE_NAME = "tabkaway.db";
	public final static String TABLE_NAME1 = "dish_info";
	public final static String TABLE_NAME2 = "MinCai";
	static SQLiteDatabase db = null;
	private ViewPager mPager;//页卡内容
	private List<View> listViews; // Tab页面列表
	private ImageView cursor;// 动画图片
	private TextView t1, t2, t3;// 页卡头标
	private float offset =0;// 动画图片偏移量
	private int bmpW;// 动画图片宽度	
	private ListView myList1,myList2,myList3;
	View view1,view2,view3;
	private ViewGroup mContainer;
	private ImageView mImageView;
	private TextView mText;
	Cursor cs;
	private ScrollView mScrollView;
	private List<String> list1,list2,list3,list4,list5;
    private  HideListViewAdapter hideadapter;
	private List<List<String>> lls; 
	private static final int[] PHOTOS_RESOURCES = new int[] {
		R.drawable.maximages_1,
		R.drawable.maximages_2,
		R.drawable.maximages_3,
		R.drawable.maximages_4,
		R.drawable.maximages_5,
		R.drawable.maximages_6,
		R.drawable.maximages_7,
		R.drawable.maximages_8,
		R.drawable.maximages_9,
		R.drawable.maximages_10,
		R.drawable.maximages_11,
		R.drawable.maximages_12,
		R.drawable.maximages_13,
		R.drawable.maximages_14,
		R.drawable.maximages_15,
		R.drawable.maximages_16,
		R.drawable.maximages_17,
		R.drawable.maximages_18,
		R.drawable.maximages_19,
		R.drawable.maximages_20,
		R.drawable.maximages_21,
		R.drawable.maximages_22,
		R.drawable.maximages_23,
		R.drawable.maximages_24,

	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		InitImageView();
		InitTextView();
		lls = getlls1(TABLE_NAME1);
		InitView();
		
		MyListViewAdapter mylistadapter = new MyListViewAdapter(this,lls);
		lls = getlls1(TABLE_NAME2);
	    hideadapter = new HideListViewAdapter(this,lls);
		myList1.setAdapter(mylistadapter);	
		myList2.setAdapter(hideadapter);
		myList3.setAdapter(hideadapter);
		myList1.setOnItemClickListener(this);
		myList2.setOnItemClickListener(new myList3Listener());
		myList3.setOnItemClickListener(new myList3Listener());
		
		mContainer.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
		InitViewPager();
	}
	private void InitView(){
		LayoutInflater mInflater = LayoutInflater.from(this);
		view1 =mInflater.inflate(R.layout.lay1, null);
		view2 =mInflater.inflate(R.layout.lay2, null);
		view3 =mInflater.inflate(R.layout.lay3, null);
		myList1=(ListView) view1.findViewById(R.id.listview_1);
		myList2=(ListView) view2.findViewById(R.id.listview_1);
		myList3=(ListView) view3.findViewById(R.id.listview_1);	
		mImageView = (ImageView)view1. findViewById(R.id.picture);
		mContainer = (ViewGroup) view1.findViewById(R.id.container);
		mText =(TextView)view1.findViewById(R.id.detailInfo);      
		mScrollView =(ScrollView)view1.findViewById(R.id.scrollView);
		LinearLayout linearlayout1 = (LinearLayout)view1.findViewById(R.id.LinearLayout);
		linearlayout1.setClickable(true);
		linearlayout1.setFocusable(true);
		linearlayout1.setOnClickListener(this);           
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

	 /*
	  * 获取
	  * 
	  */
	 private List<List<String>> getlls1(String tableName ) {  
		 List<List<String>> lls = new ArrayList<List<String>>();
		 list1 = new ArrayList<String>();  
		 list2 = new ArrayList<String>(); 
		 list3 = new ArrayList<String>(); 
		 list4 = new ArrayList<String>(); 
		 list5 = new ArrayList<String>();
		 db=SQLiteDbHelper.copyDB((this));
		 String sql = "SELECT * FROM "+tableName;
		 cs = db.rawQuery(sql, null);  
		 if(cs.moveToFirst()) {  
			 do{     
				 String je = cs.getString(cs.getColumnIndex("Name"));  
				 String ms = cs.getString(cs.getColumnIndex("Info")); 
				 String lx = cs.getString(cs.getColumnIndex("Price"));  
				 String sj = cs.getString(cs.getColumnIndex("Time"));   
				 String xq = cs.getString(cs.getColumnIndex("particularInfo"));   
				 list1.add(je);   
				 list2.add(ms);
				 list3.add(lx);  
				 list4.add(sj);   
				 list5.add(xq);
				 lls.add(list1);	
				 lls.add(list2);	
				 lls.add(list3);	
				 lls.add(list4);	
				 lls.add(list5);
			 }    
			 while(cs.moveToNext());
		 }      
		 cs.close();   
		 db.close();  
		 return lls; 
	 }	

	 class myList3Listener implements AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			hideadapter.setSelectItem(arg2);
		
			LinearLayout l = (LinearLayout)arg1.findViewById(R.id.hidlayout);
			
			if(l.getVisibility() != View.VISIBLE){
			
				l.setVisibility( View.VISIBLE);
			}else{
				l.setVisibility(View.GONE);
			}
			
			
			
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


	 private final class DisplayNextView implements Animation.AnimationListener {
		 private final int mPosition;

		 private DisplayNextView(int position) {
			 mPosition = position;
		 }

		 public void onAnimationStart(Animation animation) {
		 }

		 public void onAnimationEnd(Animation animation) {
			 mContainer.post(new SwapViews(mPosition));

		 }

		 public void onAnimationRepeat(Animation animation) {
		 }
	 }

	 private final class SwapViews implements Runnable {
		 private final int mPosition;

		 public SwapViews(int position) {
			 mPosition = position;
		 }

		 public void run() {
			 final float centerX = mContainer.getWidth() / 2.0f;
			 final float centerY = mContainer.getHeight() / 2.0f;
			 Rotate3dAnimation rotation;          
			 if (mPosition > -1) {
				 myList1.setVisibility(View.GONE);
				 mImageView.setVisibility(View.VISIBLE);
				 mImageView.requestFocus();
				 mScrollView.setVisibility(View.VISIBLE);
				 mScrollView.requestFocus();
				 rotation = new Rotate3dAnimation(90, 360, centerX, centerY, 310.0f, false);
			 } else {
				 mScrollView.setVisibility(View.GONE);
				 myList1.setVisibility(View.VISIBLE);
				 myList1.requestFocus();
				 rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 310.0f, false);
			 }

			 rotation.setDuration(500);
			 rotation.setFillAfter(true);
			 rotation.setInterpolator(new DecelerateInterpolator());
			 mContainer.startAnimation(rotation);
		 }
	 }


	 private void applyRotation(int position, float start, float end) {
		 // Find the center of the container
		 final float centerX = mContainer.getWidth() / 2.0f;
		 final float centerY = mContainer.getHeight() / 2.0f;



		 // Create a new 3D rotation with the supplied parameter
		 // The animation listener is used to trigger the next animation
		 final Rotate3dAnimation rotation =
				 new Rotate3dAnimation(start, end, centerX, centerY, 310.0f, true);

		 rotation.setDuration(500);
		 rotation.setFillAfter(true);
		 rotation.setInterpolator(new AccelerateInterpolator());
		 rotation.setAnimationListener(new DisplayNextView(position));

		 mContainer.startAnimation(rotation);

	 }


	 public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		 // Pre-load the image then start the animation
		 mImageView.setImageResource(PHOTOS_RESOURCES[position]);
		 mText.setText(list5.get(position));
		 applyRotation(position, 0, 90);

	 }

	 public void onClick(View v) {
		 applyRotation(-1, 180, 90);

	 }
}