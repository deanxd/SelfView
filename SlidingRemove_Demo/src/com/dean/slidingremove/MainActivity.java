package com.dean.slidingremove;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	private MyAdapter mAdapter;
	private List<String> datas = new ArrayList<String>();
	{
		for (int i = 0; i < 100; i++) {
			datas.add("andy" + i);
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		ListView lv_data = getListView();
		
		mAdapter = new MyAdapter();
		
		lv_data.setAdapter(mAdapter);
	}

	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder mHolder = null;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(), R.layout.item, null);
				mHolder = new ViewHolder();
				
				mHolder.srv_test = (SlidingRemoveView) convertView.findViewById(R.id.srv_test);
				mHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
				mHolder.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
				convertView.setTag(mHolder);
			} else {
				mHolder = (ViewHolder) convertView.getTag();
			}
			
			final ViewHolder finalHolder = mHolder;
			//数据
			final String mess = datas.get(position);
			mHolder.tv_content.setText(mess);
			
			mHolder.tv_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					datas.remove(mess);
					
					finalHolder.srv_test.dismissDelete();
					
					mAdapter.notifyDataSetChanged();
				}
			});
			
			return convertView;
		}
		
	}
	
	private class ViewHolder{
		SlidingRemoveView srv_test;
		TextView tv_content;
		TextView tv_delete;
	}
}
