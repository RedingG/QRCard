package com.reding.qrcard;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 *	功能描述：主程序入口类
 */
public class WelcomeActivity extends Activity implements OnClickListener,OnPageChangeListener {
	//定义ViewPager对象
	private ViewPager viewPager;
	
	//定义ViewPager适配器
	private ViewPagerAdapter vpAdapter;
	
	//定义一个ArrayList来存放View
	private ArrayList<View> views;

	//引导图片资源
    private static final int[] pics = {R.drawable.page1,R.drawable.page2,R.drawable.page3,R.drawable.page4};
    
    //底部小点的图片
    private ImageView[] points;
    
    //记录当前选中位置
    private int currentIndex;
    private int needjump = 0;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("welcome no pointer", "43");
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
		
		Log.d("welcome no pointer", "48");
		
		setContentView(R.layout.activity_welcome);
		
		Log.d("welcome no pointer", "52");
		
		initView();
		Log.d("welcome no pointer", "55");
		initData();	
		Log.d("welcome no pointer", "57");
	}

	/**
	 * 初始化组件
	 */
	private void initView(){
		//实例化ArrayList对象
		views = new ArrayList<View>();
		Log.d("welcome no pointer", "66");
		
		//实例化ViewPager
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		Log.d("welcome no pointer", "70");
		
		//实例化ViewPager适配器
		vpAdapter = new ViewPagerAdapter(views);
		Log.d("welcome no pointer", "74");
	}
	
	/**
	 * 初始化数据
	 */
	private void initData(){
		//定义一个布局并设置参数
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                														  LinearLayout.LayoutParams.MATCH_PARENT);
		Log.d("welcome no pointer", "84");
        //初始化引导图片列表
        for(int i=0; i<pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[i]);
            views.add(iv);
        } 
        Log.d("welcome no pointer", "92");
        
        //设置数据
        viewPager.setAdapter(vpAdapter);
        Log.d("welcome no pointer", "96");
        //设置监听
        viewPager.setOnPageChangeListener(this);
        Log.d("welcome no pointer", "99");
        //初始化底部小点
        initPoint();
	}
	
	/**
	 * 初始化底部小点
	 */
	private void initPoint(){
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);       
		Log.d("welcome no pointer", "109");
        points = new ImageView[pics.length];
        Log.d("welcome no pointer", "111");
        //循环取得小点图片
        for (int i = 0; i <pics.length; i++) {

        	//得到一个LinearLayout下面的每一个子元素
        	points[i] = (ImageView) linearLayout.getChildAt(i);
        	//默认都设为灰色
        	points[i].setEnabled(true);
        	//给每个小点设置监听
        	points[i].setOnClickListener(this);
        	//设置位置tag，方便取出与当前位置对应
        	points[i].setTag(i);
        }
        Log.d("welcome no pointer", "123");
        //设置当面默认的位置
        currentIndex = 0;
        Log.d("welcome no pointer", "126");
        //设置为白色，即选中状态
        points[currentIndex].setEnabled(false);
        Log.d("welcome no pointer", "129");
	}
	
	/**
	 * 当滑动状态改变时调用
	 */
	public void onPageScrollStateChanged(int arg0) {
		if(currentIndex==3){
			
			needjump = 1;
			if(needjump==1)
			{
				Intent mainIntent = new Intent(WelcomeActivity.this,MainActivity.class);
				WelcomeActivity.this.startActivity(mainIntent);
				this.finish();
				return;
			}
		}
	}
	
	/**
	 * 当当前页面被滑动时调用
	 */

	public void onPageScrolled(int arg0, float arg1, int arg2) {
		

		Log.d("number of main !!!", "number ++");
	}
	
	/**
	 * 当新的页面被选中时调用
	 */

	public void onPageSelected(int position) {
		//设置底部小点选中状态
        setCurDot(position);
        Log.d("welcome no pointer", "154");
        //String s = String.valueOf(position);
        //Toast.makeText(this, s, Toast.LENGTH_LONG).show();
	}

	/**
	 * 通过点击事件来切换当前的页面
	 */
	public void onClick(View v) {
		 int position = (Integer)v.getTag();
		 Log.d("welcome no pointer", "162");
         setCurView(position);
         Log.d("welcome no pointer", "164");
         setCurDot(position);		
         Log.d("welcome no pointer", "166");
	}

	/**
	 * 设置当前页面的位置
	 */
	private void setCurView(int position){
         if (position < 0 || position >= pics.length) {
             return;
         }
         Log.d("welcome no pointer", "176");
         viewPager.setCurrentItem(position);
         Log.d("welcome no pointer", "178");
     }

     /**
     * 设置当前的小点的位置
     */
    private void setCurDot(int positon){
         if (positon < 0 || positon >= pics.length || currentIndex == positon) {
             return;
         }
         Log.d("welcome no pointer", "188");
         points[positon].setEnabled(false);
         Log.d("welcome no pointer", "190");
         points[currentIndex].setEnabled(true);
         Log.d("welcome no pointer", "192");
         currentIndex = positon;
     }
	
}
