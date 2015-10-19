package com.dean.slidingmenu;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class SlidingMenuView extends ViewGroup {

	private View leftMenu;
	private View mainContent;
	private int leftMenu_width;
	private float mdownX;
	private Scroller mScroller;

	private boolean isOpenLeftMenu = false;
	private float downX;
	private float downY;
	private long downTime;

	public SlidingMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		mScroller = new Scroller(context);
	}

	public SlidingMenuView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}
	
	public void toggle(){
		isOpenLeftMenu = !isOpenLeftMenu;
		animationTo();
		
	}

	// 获取子组件

	@Override
	protected void onFinishInflate() {
		// 布局解析完成的回调

		leftMenu = getChildAt(0);
		mainContent = getChildAt(1);

		// 获取左侧菜单的宽度
		LayoutParams layoutParams = leftMenu.getLayoutParams();
		leftMenu_width = layoutParams.width;

		super.onFinishInflate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// 主内容的测量
		mainContent.measure(widthMeasureSpec, heightMeasureSpec);

		// 左侧菜单的测量
		// 宽度leftMenu_width 不带模式的值
		int leftMenuWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
				leftMenu_width, MeasureSpec.EXACTLY);
		leftMenu.measure(leftMenuWidthMeasureSpec, heightMeasureSpec);

		// 两个参数是不带模式
		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
				MeasureSpec.getSize(heightMeasureSpec));
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// 摆放子控件的位置

		int dx = 0;
		// 摆放主界面的位置
		int mainContentLeft = 0 + dx;
		int mainContentTop = 0;
		// 获取测量后的宽度
		int mainContentRight = mainContent.getMeasuredWidth() + dx;
		// 获取测量的高度
		int mainContentBottom = mainContent.getMeasuredHeight();
		mainContent.layout(mainContentLeft, mainContentTop, mainContentRight,
				mainContentBottom);

		// 摆放左侧菜单的位置
		int leftMenuLeft = -leftMenu.getMeasuredWidth() + dx;
		int leftMenuTop = 0;
		// 获取测量后的宽度
		int leftMenuRight = 0 + dx;
		// 获取测量的高度
		int leftMenuBottom = leftMenu.getMeasuredHeight();
		leftMenu.layout(leftMenuLeft, leftMenuTop, leftMenuRight,
				leftMenuBottom);
	}

	private void animationTo() {
		//
		int startX = getScrollX();// 当前屏幕的位置
		int endX = 0;
		if (isOpenLeftMenu) {
			// 打开
			endX = -leftMenu.getMeasuredWidth();
		} else {
			// 关闭
			endX = 0;
		}

		int dx = endX - startX;

		int startY = 0;
		int dy = 0;
		int duration = Math.abs(dx) * 5;
		if (duration > 500) {
			duration = 500;
		}

		mScroller.startScroll(startX, startY, dx, dy, duration);

		invalidate();// 刷新界面 draw -->dispatchDraw(canvas);-->drawChild(canvas,
						// child, drawingTime)-->draw(Canvas canvas, ViewGroup
						// parent, long drawingTime)
		// computeScroll();

	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			int currX = mScroller.getCurrX();
			//让屏幕不停的移动到currX
			scrollTo(currX, 0);
			invalidate();
		}
	}
	
	//分发事件
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		//点击事件
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//按下
			downX = ev.getX();
			downY = ev.getY();
			downTime = System.currentTimeMillis();
			break;
		case MotionEvent.ACTION_UP:
			//松开
			float upX = ev.getX();
			float upY = ev.getY();
			long upTime = System.currentTimeMillis();
			//判断是否是点击 
			// 1. 点的位置不变
			// 2. 时间差小于500
			if ( (Math.abs(downX - upX) < 5) && Math.abs(downY - upY) < 5) {
				// 点的位置不变
				if (upTime - downTime < 500) {
					
					//关闭左侧菜单
					//
					if (upY > 50 && isOpenLeftMenu) {
						
						toggle();
						return true;
					}
					
				}
			}
			break;
		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}
	
	//拦截事件
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub 不拦截
		
		//判断 横向滑动 拦截 纵向滑动 事件放行
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			//按下
			
			downX = ev.getX();
			downY = ev.getY();
			mdownX = downX;
			break;
		case MotionEvent.ACTION_MOVE:
			//移动
			float moveX = ev.getX();
			float moveY = ev.getY();
			
			float dx = moveX - downX;
			float dy = moveY - downY;
			
			if (Math.abs(dx) > Math.abs(dy)) {
				//横向
				return true;
			}
			break;

		default:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	//处理事件  
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		// 移动的是屏幕

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 按下

			mdownX = event.getX();

			break;
		case MotionEvent.ACTION_MOVE:
			// 移动
			actionMoveEvent(event);

			break;
		case MotionEvent.ACTION_UP:
			// 松开
			int screenX = getScrollX();// 屏幕的x坐标
			// 判断 screenX在左侧菜单的哪一半
			// 左半 显示菜单
			// 右半（包含） 不显示菜单
			if (screenX < -leftMenu.getMeasuredWidth() / 2) {
				// 显示菜单
				isOpenLeftMenu = true;
				// scrollTo(-leftMenu.getMeasuredWidth(), 0);
			} else {
				// 不显示菜单
				// scrollTo(0, 0);
				isOpenLeftMenu = false;
			}
			// 动画打开或关闭的效果
			animationTo();

			break;

		default:
			break;
		}
		return true;// 消费掉
	}

	private void actionMoveEvent(MotionEvent event) {
		// 移动
		float mMoveX = event.getX();

		// 屏幕即将改变的值
		int dx = Math.round(mdownX - mMoveX);// 方向

		// 屏幕的位置
		// 获取屏幕的当前值
		int screenX = getScrollX();// 屏幕的当前位置

		// 计算是否越界

		// 左边越界
		if (screenX + dx < -leftMenu.getMeasuredWidth()) {
			// 停留在左侧菜单完全显示的位置
			scrollTo(-leftMenu.getMeasuredWidth(), 0);
		} else if (screenX + dx > 0) { // 右边越界
			scrollTo(0, 0);
		} else {
			scrollBy(dx, 0);
		}

		// 控制屏幕的位置
		// scrollBy(x, y) 相对值
		// scrollTo(x, y) 绝对值

		// 改变起始位置
		mdownX = mMoveX;
	}
}
