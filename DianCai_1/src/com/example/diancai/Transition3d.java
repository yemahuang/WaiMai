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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.DBHelper.SQLiteDbHelper;
import com.example.adapter.MyListViewAdapter;
/**
 * This sample application shows how to use layout animation and various
 * transformations on views. The result is a 3D transition between a
 * ListView and an ImageView. When the user clicks the list, it flips to
 * show the picture. When the user clicks the picture, it flips to show the
 * list. The animation is made of two smaller animations: the first half
 * rotates the list by 90 degrees on the Y axis and the second half rotates
 * the picture by 90 degrees on the Y axis. When the first half finishes, the
 * list is made invisible and the picture is set visible.
 */
public class Transition3d extends Activity implements
        AdapterView.OnItemClickListener, View.OnClickListener {
	public final static String URL = "/data/data/com.example.diancai/files";
	//数据库文件
	public final static String DB_FILE_NAME = "tabkaway.db";
	// 归属地
	public final static String TABLE_NAME = "dish_info";
	static SQLiteDatabase db = null;
    private ListView mPhotosList;
    private ViewGroup mContainer;
    private ImageView mImageView;
    private TextView mText;
    private LinearLayout linearlayout;
    private Button btn_1;
	Cursor cs;
    private ScrollView mScrollView;
    private List<String> list1,list2,list3,list4,list5;
    // Resource identifiers for the photos we want to display
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        setContentView(R.layout.animations_main_screen);
        getlls1();
        mPhotosList = (ListView) findViewById(R.id.listview_1);
        mImageView = (ImageView) findViewById(R.id.picture);
        mContainer = (ViewGroup) findViewById(R.id.container);
        btn_1 = (Button)findViewById(R.id.button1);
        mText =(TextView)findViewById(R.id.detailInfo);
        mScrollView =(ScrollView)findViewById(R.id.scrollView);
        linearlayout = (LinearLayout)findViewById(R.id.LinearLayout);
        
        btn_1.setOnClickListener(new btn_1ClickListenr());
        // Prepare the ListView
        MyListViewAdapter adapter = new MyListViewAdapter(this,getlls1(),list1,list2,list3,list4);
        mPhotosList.setAdapter(adapter);
        mPhotosList.setOnItemClickListener(this);
        // Prepare the ImageView
        mImageView.setClickable(true);
        mImageView.setFocusable(true);
        mImageView.setOnClickListener(this);        
        mScrollView.setClickable(true);
        mScrollView.setFocusable(true);
        mScrollView.setOnClickListener(this);
        mText.setClickable(true);
        mText.setFocusable(true);
        mText.setOnClickListener(this);
        linearlayout.setClickable(true);
        linearlayout.setFocusable(true);
        linearlayout.setOnClickListener(this);
        // Since we are caching large views, we want to keep their cache
        // between each animation
        mContainer.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
    }

    /**
     * Setup a new 3D rotation on the container view.
     *
     * @param position the item that was clicked to show a picture, or -1 to show the list
     * @param start the start angle at which the rotation must begin
     * @param end the end angle of the rotation
     */
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
    /*
     * 
     * btn_1 事件监听
     */
    class btn_1ClickListenr implements OnClickListener{
        @Override
    	public void onClick(View v) {
    		// TODO Auto-generated method stub	    
//            NumberPicker np= (NumberPicker) findViewById(R.id.numberPicker);
//            np.setMaxValue(20);
//            np.setMinValue(0);
//            np.setFocusable(true);		   
//            np.setFocusableInTouchMode(true);
//            int value =np.getValue();
//            System.out.println("hksg"+value);
    	}
    }

    /**
     * This class listens for the end of the first half of the animation.
     * It then posts a new action that effectively swaps the views when the container
     * is rotated 90 degrees and thus invisible.
     */
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
	private List<List<String>> getlls1() {  
		List<List<String>> lls = new ArrayList<List<String>>();
		list1 = new ArrayList<String>();  
		list2 = new ArrayList<String>(); 
		list3 = new ArrayList<String>(); 
		list4 = new ArrayList<String>(); 
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
			}    
			while(cs.moveToNext());
		}      
		cs.close();   
		db.close();  
		return lls; 
	}	
	
    /**
     * This class is responsible for swapping the views and start the second
     * half of the animation.
     */
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
                mPhotosList.setVisibility(View.GONE);
               mImageView.setVisibility(View.VISIBLE);
                mImageView.requestFocus();
                mScrollView.setVisibility(View.VISIBLE);
                mScrollView.requestFocus();
                rotation = new Rotate3dAnimation(90, 360, centerX, centerY, 310.0f, false);
            } else {
            	mScrollView.setVisibility(View.GONE);
                mPhotosList.setVisibility(View.VISIBLE);
                mPhotosList.requestFocus();
                rotation = new Rotate3dAnimation(90, 0, centerX, centerY, 310.0f, false);
            }

            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());

            mContainer.startAnimation(rotation);
        }
    }

}

