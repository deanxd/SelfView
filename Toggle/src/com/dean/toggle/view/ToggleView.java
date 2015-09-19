package com.dean.toggle.view;

import com.dean.toggle.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Xudong
 * @comp Formssion
 * @date 2015-9-20
 * @desc ****

 * @version $Rev$
 * @anthor $Author$
 * @Date $Date$
 * @Id $Id$
 * @URL $URL$
 */
public class ToggleView extends View {

	private Bitmap mBgBitmap;
	private Bitmap mSlidingBitmap;
	private Paint mPaint;

	public ToggleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		setBitmap(R.drawable.switch_background,
				R.drawable.slide_button_background);
	}

	public ToggleView(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawBitmap(mBgBitmap, 0, 0, mPaint);
		canvas.drawBitmap(mSlidingBitmap, 0, 0, mPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:

			break;
		default:
			break;
		}
		System.out.println("点击了");
		return true;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(mBgBitmap.getWidth(), mBgBitmap.getHeight());
	}

	/**
	 * 设置控件的图片
	 * 
	 * @param rBgBitmap
	 *            背景图片
	 * @param rSlidingBitmap
	 *            滑动图片
	 * 
	 */
	private void setBitmap(int rBgBitmap, int rSlidingBitmap) {
		mBgBitmap = BitmapFactory.decodeResource(getResources(), rBgBitmap);
		mSlidingBitmap = BitmapFactory.decodeResource(getResources(),
				rSlidingBitmap);
	}

}
