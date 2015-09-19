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
 * 
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
	private boolean mIsOpen = false;
	private boolean ACTION_UP = false;

	private float mDownX;
	private int mSlidWidth;
	private int mBgWidth;

	public ToggleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		setBitmap(R.drawable.switch_background,
				R.drawable.slide_button_background);
		mBgWidth = mBgBitmap.getWidth();
		mSlidWidth = mSlidingBitmap.getWidth();
		mDownX = mSlidWidth / 2;
	}

	public ToggleView(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(mBgBitmap, 0, 0, mPaint);
		if (ACTION_UP) {
			// 鼠标up
			if(mIsOpen){
				canvas.drawBitmap(mSlidingBitmap, mBgWidth - mSlidWidth, 0, mPaint);
			}else{
				canvas.drawBitmap(mSlidingBitmap, 0, 0, mPaint);
			}
		} else {
			// 鼠标down or move
			drawMoveOrDown(canvas);
		}
		ACTION_UP = false;
	}

	private void drawMoveOrDown(Canvas canvas) {
		if (mDownX < mSlidWidth / 2) {
			mDownX = mSlidWidth / 2;
		}
		if (mDownX > (mBgWidth - mSlidWidth / 2)) {
			mDownX = mBgWidth - mSlidWidth / 2;
		}
		canvas.drawBitmap(mSlidingBitmap, mDownX - mSlidWidth / 2, 0, mPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownX = event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			mDownX = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			mDownX = event.getX();
			ACTION_UP = true;
			if (mDownX < mBgWidth / 2 && mIsOpen) {
					mIsOpen = false;
			} else if (mDownX >= mBgWidth / 2 && !mIsOpen) {
					mIsOpen = true;
			}
			break;
		default:
			break;
		}

		// 重新绘制控件， 即重新执行onDraw()方法
		invalidate();
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
