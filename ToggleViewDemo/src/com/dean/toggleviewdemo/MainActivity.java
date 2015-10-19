package com.dean.toggleviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.dean.toggleview_lib.ToggleView;
import com.dean.toggleview_lib.ToggleView.OnToggleChangeListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ToggleView tv_test = (ToggleView) findViewById(R.id.tv_test);
		tv_test.setOnToggleChangeListener(new OnToggleChangeListener() {
			
			@Override
			public void onToggleChanged(ToggleView tv, boolean isOpen) {
				if (isOpen) {
					Toast.makeText(getApplicationContext(), "打开",Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "关闭",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
