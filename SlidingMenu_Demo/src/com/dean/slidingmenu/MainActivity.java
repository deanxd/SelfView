package com.dean.slidingmenu;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private SlidingMenuView smv_test;
	private TextView tv_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		smv_test = (SlidingMenuView) findViewById(R.id.smv_test);
		tv_content = (TextView) findViewById(R.id.tv_main_content_content);
	}
	
	public void onToggle(View v){
		smv_test.toggle();
	}
	
	
	
	
	public void tagClick(View v){
		//关闭左侧菜单
		smv_test.toggle();
		
		TextView tv = (TextView)v;
		//显示内容
		tv_content.setText(tv.getText());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
