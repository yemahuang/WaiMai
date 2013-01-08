package com.example.adapter;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diancai.R;


public class MyListViewAdapter extends BaseAdapter{
	
	public final class ViewHolder{
		public ImageView img;
		public Button btn_diancai;
		TextView tv1, tv2, tv3, tv4;
		
	}
	private Integer[] mImageIds = {
			R.drawable.images_1,		   
			R.drawable.images_2,	
			R.drawable.images_3,		    
			R.drawable.images_4,		     
			R.drawable.images_5,		   			
			R.drawable.images_6,		    
			R.drawable.images_7,
			R.drawable.images_8,		   
			R.drawable.images_9,
			R.drawable.images_10,		 
			R.drawable.images_11,		  
			R.drawable.images_12,		    
			R.drawable.images_13,
			R.drawable.images_14,
			R.drawable.images_15,
			R.drawable.images_16,
			R.drawable.images_17,  
			R.drawable.images_18,  
			R.drawable.images_19,
			R.drawable.images_20,
			R.drawable.images_21,
			R.drawable.images_22,
			R.drawable.images_23,
			R.drawable.images_24
	};
	Cursor cs;
	private List<List<String>> lls;
	private LayoutInflater mInflater;
	private List<String> list1,list2,list3,list4;
	public final static String URL = "/data/data/com.example.diancai/files";
	//数据库文件
	public final static String DB_FILE_NAME = "tabkaway.db";
	// 归属地
	public final static String TABLE_NAME = "dish_info";
	SQLiteDatabase db = null;
	public MyListViewAdapter(Context context ,List<List<String>
	> lls,List<String> list1,List<String> list2,List<String> list3,List<String> list4){
		this.mInflater = LayoutInflater.from(context);
		this.lls = lls;
		this.list1=list1;
		this.list2=list2;
		this.list3=list3;
		this.list4=list4;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return list1.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
//	public void setSelectItem(int arg2) {
//		// TODO Auto-generated method stub
//		//selectItem = arg2;
//	}
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView( final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder=new ViewHolder();  
			convertView = mInflater.inflate(R.layout.vlist_1, null);
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
			holder.btn_diancai = (Button)convertView
					.findViewById(R.id.button1);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder)convertView.getTag();
		}
		holder.tv1.setText(lls.get(0).get(position));   
		System.out.println("---->--"+list1.get(position)+position);
		holder.tv2.setText(lls.get(1).get(position));   
		holder.tv3.setText(lls.get(2).get(position)); 
		holder.tv4.setText(lls.get(3).get(position)); 
		holder.btn_diancai.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String databaseFilename = URL + "/" + DB_FILE_NAME;				
				db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
				String insert = "insert into insert_dish(Name,Info,Price,Time,Images) values ('"+lls.get(0).get(position)+"','"+lls.get(1).get(position)+"','"+lls.get(2).get(position)+"','"+lls.get(3).get(position)+"','"+position+"')";
				db.execSQL(insert);
				db.close();
			}
		});
		holder.img.setImageResource(mImageIds[position]);
//        LinearLayout info = (LinearLayout)convertView.findViewById(R.id.hidlayout);
//        if(position == selectItem){
//        	//被选中的元素
//        	    if(sign == selectItem){
//        	    	//再次选中的时候会隐藏，并初始化标记位置
//        	               info.setVisibility(View.GONE);
//		    //没有被选中设置透明色
//            	    convertView.setBackgroundColor(Color.parseColor("#00000000")); 	    
//            	    sign = -1;
//        	    }else{
//        	    	//选中的时候会展示，并标记此位置
//        	         info.setVisibility(View.VISIBLE);
//		            //被选中设置背景颜色
//            	    convertView.setBackgroundColor(Color.parseColor("#B0E2FF"));            
//            	    sign = selectItem;
//        	}
//        	
//        }else {//未被选中的元素
//            info.setVisibility(View.GONE);
//            convertView.setBackgroundColor(Color.parseColor("#00000000"));        
//        }
		return convertView;
	}


			

	
}