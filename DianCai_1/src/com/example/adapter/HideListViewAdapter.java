package com.example.adapter;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.diancai.R;


public class HideListViewAdapter extends BaseAdapter{
	
	public final class ViewHolder{
		public ImageView img,hideImageView;
		public Button btn_diancai;
		TextView tv1, tv2, tv3, tv4,hideText;
		
	}
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
	private Integer[] mImageIds = {
			R.drawable.imagem_1,		   
			R.drawable.imagem_2,	
			R.drawable.imagem_3,		    
			R.drawable.imagem_4,		     
			R.drawable.imagem_5,		   			
			R.drawable.imagem_6,		    
			R.drawable.imagem_7,
			R.drawable.imagem_8,		   
			R.drawable.imagem_9,
			R.drawable.imagem_10,		 
			R.drawable.imagem_11,		  
			R.drawable.imagem_12,		    
			R.drawable.imagem_13,
			R.drawable.imagem_14,
			R.drawable.imagem_15,
			R.drawable.imagem_16,
			R.drawable.imagem_17,  
			R.drawable.imagem_18,  
			R.drawable.imagem_19,
			R.drawable.imagem_20,
			R.drawable.imagem_21,
			R.drawable.imagem_22,
			R.drawable.imagem_23,
			R.drawable.imagem_24
	};
	Cursor cs;
	private List<List<String>> lls;
	private LayoutInflater mInflater;
	public final static String URL = "/data/data/com.example.diancai/files";
	//数据库文件
	public final static String DB_FILE_NAME = "tabkaway.db";
	// 归属地
	public final static String TABLE_NAME = "mincai_info";
	SQLiteDatabase db = null;
	private int selectItem=-1;
	private int sign=-1;
	public HideListViewAdapter(Context context ,List<List<String>> lls){
		this.mInflater = LayoutInflater.from(context);
		this.lls = lls;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return lls.get(0).size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	public void setSelectItem(int arg2) {
		// TODO Auto-generated method stub
		selectItem = arg2;
	}
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView( final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder=new ViewHolder();  
			convertView = mInflater.inflate(R.layout.vlist, null);
			holder.img = (ImageView)convertView.findViewById(R.id.img);

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
			holder.hideText = (TextView)convertView.findViewById(R.id.hidetext);
			holder.hideImageView = (ImageView)convertView.findViewById(R.id.hideimageview);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder)convertView.getTag();
		}
		holder.tv1.setText(lls.get(0).get(position));   
		holder.tv2.setText(lls.get(1).get(position));   
		holder.tv3.setText(lls.get(2).get(position)); 
		holder.tv4.setText(lls.get(3).get(position)); 
	
		holder.hideImageView.setImageResource(PHOTOS_RESOURCES[position]);
		holder.hideText.setText(lls.get(4).get(position));
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
        LinearLayout info = (LinearLayout)convertView.findViewById(R.id.hidlayout);
        if(position == selectItem){
        	//被选中的元素
        	    if(sign == selectItem){
        	    	//再次选中的时候会隐藏，并初始化标记位置
        	               info.setVisibility(View.GONE);
		    //没有被选中设置透明色
            	    convertView.setBackgroundColor(Color.parseColor("#00000000")); 	    
            	    sign = -1;
        	    }else{
        	    	//选中的时候会展示，并标记此位置
        	         info.setVisibility(View.VISIBLE);
		            //被选中设置背景颜色
            	    convertView.setBackgroundColor(Color.parseColor("#B0E2FF"));            
            	    sign = selectItem;
        	}
        	
        }else {//未被选中的元素
            info.setVisibility(View.GONE);
            convertView.setBackgroundColor(Color.parseColor("#00000000"));        
        }
		return convertView;
	}


			

	
}