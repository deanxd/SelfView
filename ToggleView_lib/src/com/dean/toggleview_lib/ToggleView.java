package com.dean.toggleview_lib;

import com.dean.toggleview_lib.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ToggleView extends View {

	private Bitmap bm_background;
	private Bitmap bm_sliding;
	private Paint mPaint;
	private float downPointX;
	private int bm_sliding_width;
	private int bm_background_width;

	private int action_state;
	private boolean isOpen;// false

	public ToggleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initBitmap();
		mPaint = new Paint();
	}

	private void initBitmap() {
		// 开关的背景
		bm_background = BitmapFactory.decodeResource(getResources(),
				R.drawable.switch_background);
		// 开关的滑块
		bm_sliding = BitmapFactory.decodeResource(getResources(),
				R.drawable.slide_button_background);
		bm_sliding_width = bm_sliding.getWidth();
		bm_background_width = bm_background.getWidth();
	}

	public ToggleView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	// 大小
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 组件的大小 背景图片的大小
		setMeasuredDimension(bm_background.getWidth(),
				bm_background.getHeight());
	}
	
	public interface OnToggleChangeListener{
	
		public void onToggleChanged(ToggleView tv,boolean isOpen);
	}
	
	private OnToggleChangeListener mOnToggleChangeListener ;
	
	public void setOnToggleChangeListener(OnToggleChangeListener listener) {
		mOnToggleChangeListener = listener;
	}

	// 样子
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		// 画背景
		canvas.drawBitmap(bm_background, 0, 0, mPaint);

		// 判断事件类型
		if (action_state == MotionEvent.ACTION_MOVE
				|| action_state == MotionEvent.ACTION_DOWN) {
			// 画滑块
			moveOrdown(canvas);
		} else if (action_state == MotionEvent.ACTION_UP) {
			//松开
			if (isOpen) {
				// 滑块显示在最右边
				canvas.drawBitmap(bm_sliding, bm_background_width
						- bm_sliding_width, 0, mPaint);
			} else {
				canvas.drawBitmap(bm_sliding,0, 0, mPaint);
			}
		}

	}

	private void moveOrdown(Canvas canvas) {
		if (downPointX < bm_sliding_width / 2) {
			canvas.drawBitmap(bm_sliding, 0, 0, mPaint);
		} else {
			if (downPointX > (bm_background_width - bm_sliding_width / 2)) {
				// 滑块显示在最右边
				canvas.drawBitmap(bm_sliding, bm_background_width
						- bm_sliding_width, 0, mPaint);
			} else {
				// 显示在点击的位置
				canvas.drawBitmap(bm_sliding,
						downPointX - bm_sliding_width / 2, 0, mPaint);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 按下

			downPointX = event.getX();
			action_state = MotionEvent.ACTION_DOWN;
			break;
		case MotionEvent.ACTION_MOVE:
			// 滑动
			downPointX = event.getX();
			action_state = MotionEvent.ACTION_MOVE;
			break;
		case MotionEvent.ACTION_UP:
			// 松开
			downPointX = event.getX();
			action_state = MotionEvent.ACTION_UP;
			// 判断downPointX 位置是否超过了背景的一半
			// 是 打开
			// 否 关闭
			if (downPointX > bm_background_width / 2 && !isOpen) {
				isOpen = true;
				//调用
				if (mOnToggleChangeListener != null) {
					mOnToggleChangeListener.onToggleChanged(ToggleView.this, isOpen);
				}
			} else if ((downPointX < bm_background_width / 2 && isOpen)) {
				isOpen = false;
				//调用
				if (mOnToggleChangeListener != null) {
					mOnToggleChangeListener.onToggleChanged(ToggleView.this, isOpen);
				}
			}
			
			break;

		default:
			break;
		}
		invalidate();// 触发onDraw的调用
		return true;// 自己消费
	}

}
