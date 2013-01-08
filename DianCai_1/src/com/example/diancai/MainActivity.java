package com.example.diancai;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TabHost;
public class MainActivity extends TabActivity implements
        OnCheckedChangeListener {

    private TabHost mHost;
    private Intent Recommend;
    private Intent Ranking;
    private Intent Category;
    private Intent Search;
    private Intent Management;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.maintabs);
        this.Recommend = new Intent(this,StartActivity .class);
        this.Search = new Intent(this,ActionActivity .class);
        this.Category = new Intent(this, MessageGroup.class);
        this.Management = new Intent(this, YidianActivity.class);
        this.Ranking = new Intent(this, YidianActivity.class);
        
        initRadios();
        
        setupIntent();
    }
    
    private void initRadios() {
         ((RadioButton) findViewById(R.id.radio_button0)).setOnCheckedChangeListener(this);
         ((RadioButton) findViewById(R.id.radio_button1)).setOnCheckedChangeListener(this);
         ((RadioButton) findViewById(R.id.radio_button2)).setOnCheckedChangeListener(this);
         ((RadioButton) findViewById(R.id.radio_button3)).setOnCheckedChangeListener(this);
         ((RadioButton) findViewById(R.id.radio_button4)).setOnCheckedChangeListener(this);
    }
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
            case R.id.radio_button0:
                this.mHost.setCurrentTabByTag("mblog_tab");
                break;
            case R.id.radio_button1:
                this.mHost.setCurrentTabByTag("message_tab");
                break;
            case R.id.radio_button2:
                this.mHost.setCurrentTabByTag("userinfo_tab");
                break;
            case R.id.radio_button3:
                this.mHost.setCurrentTabByTag("search_tab");
                break;
            case R.id.radio_button4:
                this.mHost.setCurrentTabByTag("more_tab");
                break;
            }
        }
    }

    
  
   
    private void setupIntent() {
        this.mHost = getTabHost();
        TabHost localTabHost = this.mHost;

        localTabHost.addTab(buildTabSpec("mblog_tab", R.string.app_name,
               R.drawable.ic_tab_recommend, this.Recommend));

        localTabHost.addTab(buildTabSpec("message_tab", R.string.app_name,
        		R.drawable.ic_tab_rating, this.Category));

        localTabHost.addTab(buildTabSpec("userinfo_tab", R.string.app_name,
        		R.drawable.ic_tab_category, this.Ranking));

        localTabHost.addTab(buildTabSpec("search_tab", R.string.app_name,
        		R.drawable.ic_tab_search, this.Search));

        localTabHost.addTab(buildTabSpec("more_tab", R.string.app_name,
        		R.drawable.ic_tab_manage, this.Management));

    }

    private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
            final Intent content) {
        return this.mHost
                .newTabSpec(tag)
                .setIndicator(getString(resLabel),
                        getResources().getDrawable(resIcon))
                .setContent(content);
        }
    
}